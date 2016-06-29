package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.Exercise;

public class MenuViewController implements Initializable {
	
	@FXML
	AnchorPane menu; 
	@FXML
	HBox menuItemExerciseOverview;
	
	Pane mainSection;
	Node exerciseView;
	List<Exercise> exercisesMenu;
	List<Node> exercisesViews;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exercisesMenu = new ArrayList<Exercise>();
		exercisesViews = new ArrayList<Node>();
		
		menuItemExerciseOverview.getStyleClass().add("active");
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
		unselectAllMenuItems();
		menuItemExerciseOverview.getStyleClass().add("active");
	}
	
	/**
	 * Selects an {@link Exercise}
	 */
	public void selectExercise(Exercise exercise) {
		if(!exercisesMenu.contains(exercise)) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/TestView.fxml"));	
				Parent testView = loader.load();
				AnchorPane.setTopAnchor(testView, 0.0);
				AnchorPane.setLeftAnchor(testView, 0.0);
				AnchorPane.setRightAnchor(testView, 0.0);
				AnchorPane.setBottomAnchor(testView, 0.0);
				mainSection.getChildren().clear();
				mainSection.getChildren().add(testView);
				exercisesViews.add(testView);
				TestController testController = loader.getController();
						
				exercisesMenu.add(exercise);
				Node exerciseMenuItem = createMenuItem(exercise);
				exerciseMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						selectExercise(exercise);
					}
				});
				unselectAllMenuItems();
				exerciseMenuItem.getStyleClass().add("active");
				menu.getChildren().add(exerciseMenuItem);
			} catch(IOException exception) {
				exception.printStackTrace();
			}
		} else {
			int exerciseIndex = exercisesMenu.indexOf(exercise);
			Node testView = exercisesViews.get(exerciseIndex);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(testView);
			
			unselectAllMenuItems();
			menu.getChildren().get(exerciseIndex + 1).getStyleClass().add("active"); // +1 for exercise overview menu item
		}
	}
	
	/**
	 * Creates a menu item from an {@link Exercise}
	 * @param exercise
	 * @return
	 */
	private Node createMenuItem(Exercise exercise) {
		// Create class for menu item
		ImageView iconView = new ImageView(new Image("/gui/images/icons/pencil.png"));
		iconView.setFitHeight(20.0);
		iconView.setFitWidth(20.0);
		iconView.getStyleClass().add("menu-item-icon");
		Label exerciseLabel = new Label(exercise.getName().trim());
		exerciseLabel.setPrefHeight(20.0);
		exerciseLabel.getStyleClass().add("menu-item-label");
			
		HBox hbox = new HBox(iconView, exerciseLabel);
		hbox.getStyleClass().add("menu-item");
		AnchorPane.setTopAnchor(hbox, 40.0*(exercisesMenu.size() + 1)); // +1 for exercise overview menu-item
		AnchorPane.setLeftAnchor(hbox, -8.0);
		AnchorPane.setRightAnchor(hbox, -8.0);
		
		return hbox;
	}
	
	/**
	 * Removes all active style classes from menu items
	 */
	private void unselectAllMenuItems() {
		if(menuItemExerciseOverview.getStyleClass().contains("active"))
			menuItemExerciseOverview.getStyleClass().remove("active");
		for(Node menuItem : menu.getChildren()) {
			if(menuItem.getStyleClass().contains("active"))
				menuItem.getStyleClass().remove("active");
		}
	}

}
