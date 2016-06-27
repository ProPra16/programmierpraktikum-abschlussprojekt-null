
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			URL url = getClass().getResource("/gui/views/ExcercisesView.fxml");
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
	        primaryStage.setTitle("TDDT");
	        primaryStage.show();
		} catch (IOException e) {
			// TODO Handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
