package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.User;
import Util.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class InterfaceController implements Initializable {

	@FXML
	private TextField idField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button connexionButton;

	@FXML
	private TextField nameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField mailField;

	@FXML
	private DatePicker dOBField;

	@FXML
	private PasswordField passwordInscriptionField;

	@FXML
	private Button submitButton;

	@FXML
	private BorderPane border_pane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		@SuppressWarnings("unused")
		Runnable run = new Runnable() {

			@Override
			public void run() {
				System.out.println("OK");
			}
		};
	}

	@FXML
	public void inscrire(ActionEvent event) {

		try {
			if (CheckInfoInscription()) {
				User user = new User();
				Log.Write("Inscription demandée");
				boolean reussi = InterfaceApplication.GetClient().inscription(user);
				if (reussi)
					javax.swing.JOptionPane.showMessageDialog(null, "L'inscription a réussi, la chance !");
				else
					javax.swing.JOptionPane.showMessageDialog(null, "L'inscription n'a pas réussi, pas de chance !");
			}
		} catch (Exception ex) {
			Log.Write(ex);
		}
	}

	@FXML
	void seConnecter() throws IOException, ClassNotFoundException {

		String mail = idField.getText();
		String pwd = passwordField.getText();

		if (mail.equals("") || mail == null) {

			javax.swing.JOptionPane.showMessageDialog(null, "Le mail doit être renseigné");

			return;
		}
		if (pwd.equals("") || pwd == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "Le mot de passe doit être renseigné");
			return;
		}

		User user = null;

		try {
			user = InterfaceApplication.GetClient().connexion(mail, pwd);

			if (user == null) {
				javax.swing.JOptionPane.showMessageDialog(null, "Identifiants non reconnus");
				return;
			} else
				InterfaceApplication.CreateUser(user);

		} catch (Exception ex) {
			javax.swing.JOptionPane.showMessageDialog(null, "Erreur : " + ex);
			return;
		}

		// Permet de charger la page d'accueil de l'application
		if (user != null) {
			try {
				
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("accueil.fxml"));
				Scene scene = new Scene(root);
				Stage fenetre = (Stage) border_pane.getScene().getWindow();
				fenetre.setTitle("Serveur Photo");
				fenetre.getIcons().add(new Image("Resources/icon.png"));
				fenetre.setScene(scene);
				fenetre.show();
			}
			catch(Exception ex) {
				Log.Write(ex);
			}
			
		}

	}

	private boolean CheckInfoInscription() {
		if (passwordInscriptionField == null || passwordInscriptionField.getText() == "") {
			javax.swing.JOptionPane.showMessageDialog(null, "Le mot de passe doit être renseigné");
			return false;
		}
		if (passwordInscriptionField.getText().length() < 6) {
			javax.swing.JOptionPane.showMessageDialog(null, "Le mot de passe doit faire au moins 6 caractères");
			return false;
		}
		if (nameField.getText() == "" || nameField.getText() == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "Le prénom doit être renseigné");
			return false;
		}
		if (lastNameField.getText() == "" || lastNameField.getText() == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "Le nom doit être renseigné");
			return false;
		}

		if (mailField.getText() == "" || mailField.getText() == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "Le nom doit être renseigné");
			return false;
		}

		if (dOBField.getValue() == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "La date de naissance doit être renseigné");
			return false;
		}

		/*
		 * Date a = Calendar.getInstance().getTime(); SimpleDateFormat format = new
		 * SimpleDateFormat("yyyy MM dd   HH:mm:ss"); LocalDate da = new
		 * java.sql.Date(a.getTime()).toLocalDate(); int jour = da.getDayOfMonth(); int
		 * month = da.getMonthValue(); int year = da.getYear();
		 * 
		 * 
		 * 
		 * vous pouvez a nouveau regarder if(dateless15.compareTo(dOBField.getValue())
		 * == -1 ) { javax.swing.JOptionPane.showMessageDialog(
		 * null,"Il faut avoir au moins 15 ans pour s'inscrire"); return false; }
		 */

		return true;
	}

}
