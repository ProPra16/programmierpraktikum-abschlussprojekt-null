package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class MainViewController implements Initializable {

	@FXML
	AnchorPane sideSection;
	@FXML
	AnchorPane mainSection;
	
	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Load menu in sidebar
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/MenuView.fxml"));
			Parent menuView = loader.load();
			sideSection.getChildren().add(menuView);
			setAllAnchorsNull(menuView);
			menuController = loader.getController();
			menuController.setMainSection(mainSection);
			
			// Load import view in main section
			loader = new FXMLLoader(getClass().getResource("/gui/views/ImportView.fxml"));
			Parent importView = loader.load();
			mainSection.getChildren().add(importView);
			ImportViewController importController = loader.getController();
			importController.setMainController(this);
			setAllAnchorsNull(importView);
			
		} catch (IOException exception) {
			// TODO Handle exception
			exception.printStackTrace();
		}
	}

	/**
	 * Sets all anchors null
	 * 
	 * @param node
	 */
	public static void setAllAnchorsNull(Node node) {
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);
	}
	
	/**
	 * Shows exerciseView
	 */
	public void showExercisesView() {
		try {
			// Load exercise overview in main section
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/ExercisesView.fxml"));
			Parent exerciseView = loader.load();
			mainSection.getChildren().clear();
			mainSection.getChildren().add(exerciseView);
			setAllAnchorsNull(exerciseView);
			ExercisesViewController exercisesController = loader.getController();
			exercisesController.setMenuController(menuController);
			menuController.setExerciseView(exerciseView);
		} catch(IOException exception) {
			// TODO Handle exception
			exception.printStackTrace();
		}
	}

}
