
import java.io.IOException;
import java.net.URL;

import gui.controllers.ExercisesViewController;
import gui.controllers.MainViewController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("TDDT");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/views/MainView.fxml"));
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
