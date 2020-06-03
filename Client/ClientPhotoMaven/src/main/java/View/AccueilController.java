package View;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Model.Photo;
import Model.User;
import Util.Client;
import Util.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class AccueilController implements Initializable{

    @FXML
    private BorderPane border_pane;
    
	@FXML
    private ImageView profilImage;

    @FXML
    private TextField BoutonRechercher;
    
    @FXML
    private Text welcomeText;

    @FXML
    private Label Titre;
    
    @FXML
    private Button uploadButton;

    @FXML
    private Button profilButton;

    @FXML
    private ListView<String> imageList;

    @FXML
    private ListView<String> friendList;

    @FXML
    private Button seeUserPhoto;

    private User user;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		
		Log.InitialiserLog();
		
		user = InterfaceApplication.GetUser();
		
		welcomeText.setText("Bienvenue " + user.getSurname());
		
		Titre.setText("Parcourir les photos publiques");
		
        ArrayList<Photo> photos = new ArrayList<Photo>();
		try {
			Client client = InterfaceApplication.GetClient();
						
			photos = client.GetPhotoPublic();		 
			
		} catch (Exception e) {
			Log.Write(""+ e);
			photos = null;
		}
		
		afficherListImage(photos);  
		
		ObservableList<String> friends =FXCollections.observableArrayList ("Maelle", "Raph", "Pédro");
        friendList.setItems(friends);
	}

    @FXML
    void mettreEnLigne() throws IOException {
        
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("upload.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.getIcons().add(new Image("Resources/icon.png"));
            stage.setTitle("Mettre en ligne une photo");
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
   
    }
    

    @FXML
    void seeProfil() throws IOException {
        
		Parent root = FXMLLoader.load(getClass().getResource("profil.fxml"));
        Scene scene = new Scene(root);
        Stage fenetre = (Stage) border_pane.getScene().getWindow();
        fenetre.setTitle("Serveur Photo");
        fenetre.setScene(scene);
        fenetre.show();        
    }
    
    @FXML
    void showUserPhoto() throws IOException {
        
		Parent root = FXMLLoader.load(getClass().getResource("profil.fxml"));
        Scene scene = new Scene(root);
        Stage fenetre = (Stage) border_pane.getScene().getWindow();
        fenetre.setTitle("Serveur Photo");
        fenetre.setScene(scene);
        fenetre.show();
        
    }

    @FXML
    public void rechercher() {
    	if(!BoutonRechercher.getText().equals("") || !BoutonRechercher.getText().equals(null) || !BoutonRechercher.getText().equals(" ") )
    		this.Titre.setText("Rechercher : " + this.BoutonRechercher.getText());
    	
    	ArrayList<Photo> photos = new ArrayList<Photo>();
    	try {
			Client client = InterfaceApplication.GetClient();
					
			photos = client.GetPhotosMot(this.BoutonRechercher.getText());		 
			
		} catch (Exception e) {
			Log.Write(""+ e);
			photos = null;
		}
		
		afficherListImage(photos);
    }
    
    public void afficherListImage(ArrayList<Photo> photos) {
    	
    	if(photos == null)
    		return;
    	
    	try {
    		ObservableList<String> items = FXCollections.observableArrayList ();
        	Map<String, Image> imageCollection = new HashMap<>();
        	 
        	for(Photo photo : photos) {
        		items.add(photo.getDescription());
        		imageCollection.put(photo.getDescription(), photo.getImage());
    		}
        	imageList.setItems(items);
        	
        	
            imageList.setCellFactory(param -> new ListCell<String>() {
            		
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
               super.updateItem(name, empty);
               imageView.setImage(imageCollection.get(name));
               setText(name);
               setGraphic(imageView);
               }
            });
    	}
    	catch(Exception ex) {
    		Log.Write(ex);
    	}    	
    }

}
