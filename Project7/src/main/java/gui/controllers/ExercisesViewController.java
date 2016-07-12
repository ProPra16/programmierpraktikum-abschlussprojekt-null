package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import gui.views.exercises.ExercisesGrid;
import javafx.fxml.*;
import javafx.scene.control.ScrollPane;
import xmlModelParser.ModelStorageController;
import xmlModelParser.ParserException;

public class ExercisesViewController implements Initializable {

	@FXML
	ScrollPane mainPane;

	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		// Try to load current workspace
		try {
			ModelStorageController.getInstance().loadModel();
			
			// If exercises are imported, show exercises grid
			if(ModelStorageController.getInstance().getCatalog().getExercises().size() != 0) {
				// Create exercises grid
				ExercisesGrid exercisesGrid = new ExercisesGrid(ModelStorageController.getInstance().getCatalog().getExercises());
				exercisesGrid.addSelectExerciseHandler((exercise) -> {
					menuController.selectExercise(exercise);
				});
				mainPane.setContent(exercisesGrid);
			}
		} catch (SAXException | IOException | ParserConfigurationException | ParserException e) {
			// Do nothing - import view is already there
		}

	}

	/**
	 * Sets the menu controller
	 */
	public void setMenuController(MenuViewController menuController) {
		this.menuController = menuController;
	}

}
