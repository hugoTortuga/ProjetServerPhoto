package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.MessageClient;
import Model.ObjectNetwork;
import Model.Photo;
import Model.Tag;
import Model.User;
import Util.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;

@SuppressWarnings("restriction")
public class UploadController implements Initializable{

    @FXML
    private ImageView image;

    @FXML
    private Button choosePhotoButton;

    @FXML
    private Label locationLabel;

    @FXML
    private TextField namePhotoField;
    
    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextArea tagTextArea;

    @FXML
    private Button uploadButton;

    @FXML
    private Label visibilityLabel;

    @FXML
    private ComboBox<String> visibilityCombo;

    Photo photoToUpload;
    String photoFormat;
    Client client;
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        //Create photo which gonna be upload when all the informations will be set
        photoToUpload = new Photo();
	
		//Visibility combobox 
        ObservableList<String> items = FXCollections.observableArrayList ("Public", "Privé", "Ami·e·s");
        visibilityCombo.setItems(items);
        visibilityCombo.setValue("Privé");
		this.photoToUpload.setVisibility(visibilityCombo.getValue());;
		
        ObservableList<String> tags = FXCollections.observableArrayList ("#nature", "#food", "#animaux");
        AutocompletionlTextField field = new AutocompletionlTextField();
        field.getEntries().addAll(tags);


        //Tags combobox
//      ArrayList listTags = client.send("getTags");
//      
//      for(Tag t : listTags) {
//          ObservableList<String> tags = FXCollections.observableArrayList (t.GetLibelle());
//      }

//        tagListView.setItems(tags);
//        tagListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}
	

    @FXML
    void choosePhoto(ActionEvent event) throws FileNotFoundException {

        JFileChooser chooser=new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));

      
        int result = chooser.showOpenDialog(null);
        switch (result) {
        case JFileChooser.APPROVE_OPTION:
          System.out.println("Approve (Open or Save) was clicked");
          if(chooser.getSelectedFile() != null) {
              String path=chooser.getSelectedFile().getAbsolutePath();
              String name = chooser.getSelectedFile().getName();
              photoFormat = name.substring(name.lastIndexOf("."),name.length());
              namePhotoField.setText(name.substring(0, name.lastIndexOf(".")));

              @SuppressWarnings("unused")
			String filename=chooser.getSelectedFile().getName();
              if(path != null) {
                  FileInputStream input = new FileInputStream(path);
                  Image img = new Image(input);
                  image.setImage(img);
                  
              }
          }
          break;
        case JFileChooser.CANCEL_OPTION:
          System.out.println("Cancel or the close-dialog icon was clicked");
          break;
        case JFileChooser.ERROR_OPTION:
          System.out.println("Error");
          break;
        }
    }

    @FXML
    void enteredLocation(ActionEvent event) {
		 this.photoToUpload.setLocation(locationField.getText());
         System.out.println("bonjour");

    }
    
    @FXML
    void chooseTag(ActionEvent event) {
		 ArrayList<Tag> tags = new ArrayList<Tag>();

//    	tagListView.getSelectionModel().selectedItemProperty()
//	    .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
//		     ObservableList<String> selectedItems = tagListView.getSelectionModel().getSelectedItems();
//	
//		     StringBuilder builder = new StringBuilder("Selected items :");
//		     
//		     for (String name : selectedItems) {
//		 		 String libelle = tagListView.getSelectionModel().getSelectedItem();
//
//		 		 libelle = name;
//				 Tag tag = new Tag(libelle);
//				 boolean contains = true;
//				 System.out.println("test");
//				 for(Tag t : tags) {
//					 if(!t.getLibelle().equals(tag.getLibelle())) contains = false;
//			 		 System.out.println("Tag " + t.getLibelle());
//
//				 }
//				 for(int i = 0; i < tags.size(); i++) {
//			 		 System.out.println("Liste tags" + tags.get(i).getLibelle());
//
//		 		 }
//				 /*if (!contains)*/ tags.add(tag);
//				 this.photoToUpload.setTags(tags);
//
//		     }
//
//	    });
    }

    @FXML
    void chooseVisibility(ActionEvent event) {
    	this.photoToUpload.setVisibility(visibilityCombo.getSelectionModel().getSelectedItem());
    }

    @FXML
    void enteredDescription(InputMethodEvent event) {
    	descriptionTextArea.setText("coucou");
    	String description = descriptionTextArea.getText();
    	
    	description += "\n Tags : ";

    	ArrayList<Tag> listTags = (ArrayList<Tag>) this.photoToUpload.getTags();
    	for(int i = 0; i < listTags.size(); i++) {
    		if(i == listTags.size()-1) {
    			description += listTags.get(i).getLibelle();
    		} else {
            	description += listTags.get(i).getLibelle() +", ";

    		}
    	}
    	this.descriptionTextArea.setText(description);

    	this.photoToUpload.setDescription(descriptionTextArea.getText());
    }
    
    public ArrayList<Tag> getTagsList() {
		 ArrayList<Tag> tags = new ArrayList<Tag>();

//	    	tagListView.getSelectionModel().selectedItemProperty()
//		    .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
//			     ObservableList<String> selectedItems = tagListView.getSelectionModel().getSelectedItems();
//		
//			     StringBuilder builder = new StringBuilder("Selected items :");
//			     
//			     for (String name : selectedItems) {
//			 		 String libelle = tagListView.getSelectionModel().getSelectedItem();
//
//			 		 libelle = name;
//					 Tag tag = new Tag(libelle);
//					 boolean contains = true;
//					 System.out.println("test");
//					 for(Tag t : tags) {
//						 tags.add(tag);
////						 if(!t.getLibelle().equals(tag.getLibelle())) contains = false;
////				 		 System.out.println("Tag " + t.getLibelle());
//
//					 }
////					 for(int i = 0; i < tags.size(); i++) {
////				 		 System.out.println("Liste tags" + tags.get(i).getLibelle());
////
////			 		 }
////					 if (!contains) tags.add(tag);
//
//			     }
//		    });
	    	return tags;
    }

    public ArrayList<Tag> getTagsEntered() {
    	ArrayList<Tag> tags = getTagsList();
    	String tag = this.tagTextArea.getText();
		if(tag != null) {
			if(!tag.isEmpty()) {
				tag = tag.replaceAll("\\s+","");
				tag = tag.replaceAll(",", "");
				tag = tag.trim();
			}
			if(tag.contains("#")) {
				String [] tmp = tag.split("#");
				for(String s : tmp) {
					if(!s.isEmpty()) {
							s = s.replaceAll(",", "");
							s = s.replaceAll("\\s+","");
							s = s.trim();
							tags.add(new Tag(s));
					}
				}
			} else {
				if(tag != null) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Tags");
                    alert.setHeaderText("Information :");
                    alert.setContentText("Vos tags doivent commencer par un '#'.");
                    alert.showAndWait();
                }
			}
		}
		return tags;
    }
    
    @FXML
    void upload(ActionEvent event) {
    	try {
    		
    		// on récupère les données de la vue
    		this.photoToUpload.setLocation(locationField.getText());
        	this.photoToUpload.setDescription(descriptionTextArea.getText());
    		this.photoToUpload.setTags(getTagsEntered());
    		String photoPath = namePhotoField.getText() + photoFormat;
    		this.photoToUpload.setChemin(photoPath);
    		
    		for(Tag tag : this.photoToUpload.getTags()) {
    			System.out.println("tag : " + tag.getLibelle());
    		}
    		
    		//On affiche les données de la photo en console (debug)
        	System.out.println("photo : \n" 
        			+ "location " + this.photoToUpload.getLocation() + "\n"
        			+ "chemin " + this.photoToUpload.getChemin() + "\n"
        			+ "desc " + this.photoToUpload.getDescription() + "\n" 
        			+ "tags " + this.photoToUpload.getTags()+ "\n" 
        			+ "visib " + this.photoToUpload.getVisibility() + "\n"
        			+ "date "+  this.photoToUpload.getDate() + "\n");

        	//On envoie la demande de création de la photo
        	User u = InterfaceApplication.GetUser();
        	        	
        	Object[] tab = new Object[2];
    		tab[0] = u;
    		tab[1] = photoToUpload;
    		ObjectNetwork net = new ObjectNetwork(MessageClient.UploadOnePhoto, tab);				
    		client.Send(net);
    		
    			//MessageClient msg = (MessageClient)Input.readObject();		
    			//if(msg == MessageClient.Success) 
    			//	return true;
    			//else
    			//	return false;
    			
        	
/*            if(client.UploadOnePhoto(u, photoToUpload))
            	javax.swing.JOptionPane.showMessageDialog(null, "Enregistrement a réussi");
            else
            	javax.swing.JOptionPane.showMessageDialog(null, "Impossible de mettre en ligne votre photo, veuillez réessayer.");
            */
    	} catch(Exception e) 
    	{
    		Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Mise en ligne");
	        alert.setHeaderText("Resultats:");
	        alert.setContentText("Impossible de mettre en ligne votre photo, veuillez réessayer.");
	        alert.showAndWait();
    	}
    }

}

