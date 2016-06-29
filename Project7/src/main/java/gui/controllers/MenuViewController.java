package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.Exercise;

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
	
	/**
	 * Selects the exercise overview
	 */
	public void selectExerciseOverview() {
		mainSection.getChildren().clear();
		mainSection.getChildren().add(exerciseView);
		menuItemExerciseOverview.getStyleClass().add("active");
	}
	
	/**
	 * Selects an exercise
	 */
	public void selectExercise(Exercise exercise) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/TestView.fxml"));	
			Parent testView = loader.load();
			mainSection.getChildren().clear();
			mainSection.getChildren().add(testView);
			AnchorPane.setTopAnchor(testView, 0.0);
			AnchorPane.setLeftAnchor(testView, 0.0);
			AnchorPane.setRightAnchor(testView, 0.0);
			AnchorPane.setBottomAnchor(testView, 0.0);
			TestController testController = loader.getController();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
	}

}
