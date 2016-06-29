package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainViewController implements Initializable {
	
	@FXML
	AnchorPane sideSection;
	@FXML
	AnchorPane mainSection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Load menu in sidebar
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/MenuView.fxml"));
			Parent menuView = loader.load();
			sideSection.getChildren().add(menuView);
			setAllAnchorsNull(menuView);
			MenuViewController menuController = loader.getController();
			menuController.setMainSection(mainSection);
			
			// Load exercise overview in main section
			loader = new FXMLLoader(getClass().getResource("/gui/views/ExercisesView.fxml"));
			Parent exerciseView = loader.load();
			mainSection.getChildren().add(exerciseView);
			setAllAnchorsNull(exerciseView);
			ExercisesViewController exercisesController = loader.getController();
			exercisesController.setMenuController(menuController);
			menuController.setExerciseView(exerciseView);
		} catch (IOException e) {
			// TODO Handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets all anchors null
	 * @param node
	 */
	private void setAllAnchorsNull(Node node) {
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);
	}

}
