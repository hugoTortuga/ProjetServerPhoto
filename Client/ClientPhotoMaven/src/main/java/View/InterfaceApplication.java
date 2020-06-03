package View;

import java.io.IOException;
import java.net.URL;

import Model.User;
import Util.Client;
import Util.Log;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


@SuppressWarnings("restriction")
public class InterfaceApplication extends Application{

	Stage stage;
	
	private static Client client;
	private static User user;
	
	
	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
	
	public static Client GetClient() {
		return client;
	}
	
	public static void CreateUser(User _user) {
		user = _user;
	}
	
	public static User GetUser() {
		return user;
	}

	@Override
	public void start(Stage fenetre) throws IOException {
		
		try {
			client = new Client(3002);	
			BorderPane fxPanneau = (BorderPane) FXMLLoader.load(getClass().getResource("interface.fxml"));
			Scene scene = new Scene(fxPanneau);
		
			fenetre.setScene(scene);
			fenetre.setTitle("Serveur Photo");
			fenetre.getIcons().add(new Image("Resources/icon.png"));
			fenetre.setMaximized(false);
			fenetre.setFullScreen(false);
			fenetre.setResizable(true);
			fenetre.show();
			stage = fenetre;
		}
		catch (Exception e) {
			Log.Write(""+e);
		}
		finally {
			
		}
		
	}
	

	@Override
	public void stop() {
		Platform.exit();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
