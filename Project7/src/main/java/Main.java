
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
		
		primaryStage.setTitle("TDDT");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);

		try {
			URL url = getClass().getResource("/gui/views/MainView.fxml");
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Handle exception
			e.printStackTrace();
		}

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
