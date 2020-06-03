package Controller;

import javafx.scene.control.Label;
import java.io.IOException;
import java.net.BindException;
import java.net.URL;
import java.util.ResourceBundle;

import API.Queries;
import Main.Log;
import Main.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ServerController implements Initializable{

    @FXML
    private Button buttonMenu;
    
    @FXML
    private ToggleButton buttonOnOff;

    @FXML
    private Pane colorStatut;

    @FXML
    private Text labelStatut;

    @FXML
    private ListView<?> listLog;

    @FXML
    private Text nbConnexion;

    @FXML
    private ListView<?> listConnexions;
    
    @FXML
    private Label userNameToDelete;

    @FXML
    private Button deleteButton;
    
    @FXML
    void deleteUser(ActionEvent event) {

    }
    
    private boolean isOn;
    private Server server;
    
    @FXML
    public void OnOffClick(ActionEvent event) throws IOException {
    	if(isOn) {    
    		if(shutServeur()) {    				
    			buttonOnOff.setText("Allumer le serveur");    	    	
    	    	labelStatut.setText("off");
    	    	colorStatut.setBackground(new Background( new BackgroundFill(new Color(0.9,0.4,0.45,1), new CornerRadii(12), Insets.EMPTY)));
    		}
       	}
    	else {
    		if (lancerServeur()) {
    			buttonOnOff.setText("Eteindre le serveur");    	    	
    	    	labelStatut.setText("on");
    	    	colorStatut.setBackground(new Background( new BackgroundFill(new Color(0.6,0.9,0.5,1), new CornerRadii(12), Insets.EMPTY)));
    		}
    		
    	}
    	
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		
    	server = null;    	
    	isOn = false;
    	labelStatut.setText("off");
    	colorStatut.setBackground(new Background( new BackgroundFill(new Color(0.9,0.4,0.45,1), new CornerRadii(12), Insets.EMPTY)));
    	nbConnexion.setText("0");
    	    	   	
		ObservableList<String> friends =FXCollections.observableArrayList ();
		friends.add("Maelle");
		friends.add("RaphouY la couY");
	}
    
    private Thread threadServer;
    
    private boolean lancerServeur() {
    	try {
    		//test pour savoir si la bd est accessible
    		if(!Queries.test()) {
    			javax.swing.JOptionPane.showMessageDialog(null,"La base mySQL n'est pas accessible");
    			return false;
    		}
    			
    		
    		
    		
    		if(threadServer != null) {
    			if(threadServer.isInterrupted())
    				threadServer.start();
    		}
    		else {
    			threadServer = new Thread(new Server(3002));
        		threadServer.start();
    		}    		
    		isOn = true;
    		//update();
		}
    	catch(BindException be) {
	    	javax.swing.JOptionPane.showMessageDialog(null,"Le port du serveur est déjé occupé ou il n'existe pas");
	    	return false;
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null,"Erreur : " + e);
	    	return false;
		}
    	return true;
    }
    
    private boolean shutServeur() {
    	if(threadServer != null) {
    		threadServer.interrupt();
    		isOn = false;
    		return true;
    	}
    	return true;
    }
    
    private void update() {
    	while(isOn) {
    	}
    }
    
    
}
