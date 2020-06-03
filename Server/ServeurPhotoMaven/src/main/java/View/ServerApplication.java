package View;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ServerApplication extends Application {

	@Override
	public void start(Stage fenetre) throws IOException {
		FlowPane fxPanneau = (FlowPane) FXMLLoader.load(getClass().getResource("server.fxml"));
	    Scene scene = new Scene(fxPanneau);
	
	    fenetre.setScene(scene);
	    fenetre.setTitle("ServerPhoto");
	    fenetre.setMaximized(false);
	    fenetre.setFullScreen(false);
	    fenetre.setResizable(false);
	    fenetre.show();
	
	}

	@Override
	public void stop() {
	    Platform.exit();
	}
	
	public static void main(String[] args) {
	    launch(args);	
	}
}