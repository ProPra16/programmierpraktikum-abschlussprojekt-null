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
			Parent exerciseView = FXMLLoader.load(getClass().getResource("/gui/views/ExercisesView.fxml"));
			mainSection.getChildren().add(exerciseView);
			setAllAnchorsNull(exerciseView);
			
			Parent menuView = FXMLLoader.load(getClass().getResource("/gui/views/MenuView.fxml"));
			sideSection.getChildren().add(menuView);
			setAllAnchorsNull(menuView);
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
