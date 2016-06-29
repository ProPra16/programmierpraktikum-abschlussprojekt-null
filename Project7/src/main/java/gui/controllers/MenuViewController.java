package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MenuViewController implements Initializable {

	@FXML
	HBox menuItemExerciseOverview;
	
	Pane mainSection;
	Node exerciseView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuItemExerciseOverview.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectExerciseOverview();
			}
		});
	}

	/**
	 * Sets the main section
	 * @param mainSection
	 */
	public void setMainSection(Pane mainSection) {
		this.mainSection = mainSection;
	}
	
	/**
	 * Sets the exercise view
	 * @param exerciseView
	 */
	public void setExerciseView(Node exerciseView) {
		this.exerciseView = exerciseView;
	}
	
	public void selectExerciseOverview() {
		mainSection.getChildren().clear();
		mainSection.getChildren().add(exerciseView);
		menuItemExerciseOverview.getStyleClass().add("active");
	}

}
