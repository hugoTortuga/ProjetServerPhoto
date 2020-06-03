package View;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import Model.Photo;
import Model.User;
import Util.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@SuppressWarnings("restriction")
public class UpdateUserInformationController implements Initializable {

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    private User user;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		user = InterfaceApplication.GetUser();

		this.lastNameField.setText(user.getName());
		this.nameField.setText(user.getSurname());
		this.mailField.setText(user.getMail());
		this.passwordField.setText(user.getPassword());
//		Date d = new Date();
//		user.setDateOfBirth((java.sql.Date) d);
//    	Date a = user.getDateOfBirth();
//    	LocalDate localDate = Instant.ofEpochMilli(a.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
//		
//		this.dateField.setValue(localDate);
		
	}
	
    @FXML
    void saveAndUpdateInfos() {
    	try {
    		saveButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	user.setSurname(lastNameField.getText());
    	    	user.setName(nameField.getText());
    	    	user.setDateOfBirth(null);
    	    	user.setMail(mailField.getText());
    	    	user.setUserName(userNameField.getText());
    	    	user.setPassword(passwordField.getText());
    	    }
    	});
    		javax.swing.JOptionPane.showMessageDialog(null, "Modifications enregistrées");
    		
    		
    	}
    	catch(Exception ex) {
    		javax.swing.JOptionPane.showMessageDialog(null, "Erreur : " + ex);
    	}
    	

    }

}

