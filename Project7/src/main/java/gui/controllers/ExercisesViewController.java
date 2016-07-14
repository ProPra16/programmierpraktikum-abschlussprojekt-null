package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.views.exercises.ExercisesGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import services.StorageService;

public class ExercisesViewController implements Initializable {

	@FXML
	ScrollPane mainPane;

	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadView();
	}

	/**
	 * Sets the menu controller
	 */
	public void setMenuController(MenuViewController menuController) {
		this.menuController = menuController;
	}
	
	/**
	 * Refreshes the catalog and loads the view
	 */
	public void loadView() {
		// Try to load current workspace
		try {
			StorageService.getInstance().loadModel();
			
			// If exercises are imported, show exercises grid
			if(StorageService.getInstance().getExerciseCatalog().getExercises().size() != 0) {
				// Create exercises grid
				ExercisesGrid exercisesGrid = new ExercisesGrid(StorageService.getInstance().getExerciseCatalog().getExercises());
				exercisesGrid.addSelectExerciseHandler((exercise) -> {
					menuController.selectExercise(exercise);
				});
				mainPane.setContent(exercisesGrid);
			}
		} catch (Exception e) {
			// Do nothing - import view is already there
		}
	}

}
