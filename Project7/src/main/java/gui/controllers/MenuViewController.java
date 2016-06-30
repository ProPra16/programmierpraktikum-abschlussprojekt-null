package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.views.ExerciseMenuItem;
import gui.views.MenuItem;
import gui.views.OverviewMenuItem;
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
import javafx.scene.layout.VBox;
import models.Exercise;

public class MenuViewController implements Initializable {
	
	@FXML 
	AnchorPane menuSection;
	@FXML
	VBox menu; 
	
	Pane mainSection;
	Node exerciseView;
	List<MenuItem> menuItems;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuItems = new ArrayList<MenuItem>();
		
		MenuItem overviewMenuItem = new OverviewMenuItem();
		overviewMenuItem.select();
		
		overviewMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectExerciseOverview();
			}
		});
		
		menuItems.add(overviewMenuItem);
		menu.getChildren().addAll(menuItems);
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
	 * Selects the exercise overview menu item
	 */
	public void selectExerciseOverview() {
		mainSection.getChildren().clear();
		mainSection.getChildren().add(exerciseView);
		
		unselectAllMenuItems();
		for(MenuItem menuItem : menuItems) {
			if(menuItem instanceof OverviewMenuItem) {
				menuItem.select();
				break; // Usually only one overview menu item
			}
		}
	}
	
	/**
	 * Selects the menu item of the given {@link Exercise}
	 * @param exercise
	 */
	public void selectExercise(Exercise exercise) {
		int exerciseIndexInMenu = getMenuIndex(exercise); 
		
		if(exerciseIndexInMenu == -1) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/TestView.fxml"));	
				Parent testView = loader.load();
				AnchorPane.setTopAnchor(testView, 0.0);
				AnchorPane.setLeftAnchor(testView, 0.0);
				AnchorPane.setRightAnchor(testView, 0.0);
				AnchorPane.setBottomAnchor(testView, 0.0);
				mainSection.getChildren().clear();
				mainSection.getChildren().add(testView);
				TestController testController = loader.getController();
				
				ExerciseMenuItem exerciseMenuItem = new ExerciseMenuItem(exercise);
				exerciseMenuItem.setMainView(testView);
				exerciseMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						selectExercise(exercise);
					}
				});
				unselectAllMenuItems();
				exerciseMenuItem.select();
				menuItems.add(exerciseMenuItem);
				menu.getChildren().add(exerciseMenuItem);
			} catch(IOException exception) {
				exception.printStackTrace();
			}
		} else {
			MenuItem exerciseMenuItem = menuItems.get(exerciseIndexInMenu);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(exerciseMenuItem.getMainView());
			
			unselectAllMenuItems();
			exerciseMenuItem.select();
		}
	}
	
	
	/**
	 * Removes all active style classes from menu items
	 */
	private void unselectAllMenuItems() {
		for(MenuItem menuItem : menuItems)
			menuItem.unselect();
	}
	
	
	/**
	 * Checks if exercise is already in menu
	 * @param exercise
	 * @return -1 if not found, otherwise the index in menu items list
	 */
	private int getMenuIndex(Exercise exercise) {
		for(int i = 0; i < menuItems.size(); i++) {
			MenuItem menuItem = menuItems.get(i);
			if(menuItem instanceof ExerciseMenuItem && ((ExerciseMenuItem) menuItem).getExercise() == exercise)
				return i;
		}
		
		return -1;
	}

}
