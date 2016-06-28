
import java.io.IOException;
import java.net.URL;

import gui.controllers.ExcercisesController;
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/ExcercisesView.fxml"));	
			Parent root = loader.load();
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
			ExcercisesController controller = loader.getController();
			controller.initWithStage(primaryStage);
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
