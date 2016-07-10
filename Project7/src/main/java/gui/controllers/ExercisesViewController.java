package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.views.exercises.ExercisesGrid;
import javafx.fxml.*;
import javafx.scene.control.ScrollPane;
import xmlModelParser.ModelStorageController;

public class ExercisesViewController implements Initializable {

	@FXML
	ScrollPane mainPane;

	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ExercisesGrid exercisesGrid = new ExercisesGrid(
				ModelStorageController.getInstance().getCatalog().getExercises());
		exercisesGrid.addSelectExerciseHandler((exercise) -> {
			menuController.selectExercise(exercise);
		});

		mainPane.setContent(exercisesGrid);
	}

	/**
	 * Sets the menu controller
	 */
	public void setMenuController(MenuViewController menuController) {
		this.menuController = menuController;
	}

}
