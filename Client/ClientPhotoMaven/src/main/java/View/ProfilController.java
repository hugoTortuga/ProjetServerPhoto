package View;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Model.Photo;
import Model.User;
import Util.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.control.ListView;


@SuppressWarnings("restriction")
public class ProfilController implements Initializable {


    @FXML
    private BorderPane border_pane;

    @FXML
    private ImageView profilImage;

    @FXML
    private Button backButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Button updateInfoButton;

    @FXML
    private ListView<String> imageList;

    @FXML
    private ListView<String> friendList;
    
    private User user;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		user = InterfaceApplication.GetUser();

		
		ArrayList<Photo> photos = null;
		try {
			photos = InterfaceApplication.GetClient().GetMyPhotos(user);
		} catch (Exception e) {
			Log.Write(""+ e);
		}
		
		afficherListImage(photos);  
		
		ObservableList<String> friends =FXCollections.observableArrayList ("Maelle", "Raph");
        friendList.setItems(friends);
	}
	
    @FXML
    void mettreEnLigne() {
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
    void updateUserInfo() {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateInfos.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.getIcons().add(new Image("Resources/icon.png"));
            stage.setTitle("Modifier mes informations");
            stage.setScene(new Scene(root, 330, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void returnToWelcomePage() throws IOException {
    	System.out.println("test");
		Parent root = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene scene = new Scene(root);
        Stage fenetre = (Stage) border_pane.getScene().getWindow();
        fenetre.setTitle("Serveur Photo");
        fenetre.setScene(scene);
        fenetre.show();
    }
    
    public void afficherListImage(ArrayList<Photo> photos) {
    	
    	if(photos == null)
    		return;
    	
    	//Ici commence les carabistouilles
    	
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
    
    public void displayFriends(ArrayList<User> friends) {
    	ObservableList<User> items = FXCollections.observableArrayList ();
    	Map<String, Image> imageCollection = new HashMap<>();
    	 
    	for(User user : friends) {
    		//on doit get le name
    		items.add((User) user.getAmis());
    		//imageCollection.put(user.getDescription(), user.getImage());
		}
    	//imageList.setItems(items);
    	
    	
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

}
