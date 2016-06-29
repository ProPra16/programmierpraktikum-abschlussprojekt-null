package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainViewController implements Initializable {
	
	@FXML
	AnchorPane menuSection;
	@FXML
	AnchorPane mainSection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/ExercisesView.fxml"));
			Parent exerciseView = loader.load();
			mainSection.getChildren().add(exerciseView);
			AnchorPane.setTopAnchor(exerciseView, 0.0);
			AnchorPane.setLeftAnchor(exerciseView, 0.0);
			AnchorPane.setRightAnchor(exerciseView, 0.0);
			AnchorPane.setBottomAnchor(exerciseView, 0.0);
		} catch (IOException e) {
			// TODO Handle exception
			e.printStackTrace();
		}
	}

}
