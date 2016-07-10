package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import gui.views.exercises.ExercisesGrid;
import javafx.fxml.*;
import javafx.scene.control.ScrollPane;
import models.Catalog;
import xmlModelParser.ModelStorageController;
import xmlModelParser.Parser;
import xmlModelParser.ParserException;

public class ExercisesViewController implements Initializable {

	@FXML
	ScrollPane mainPane;

	boolean inDetailView;

	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Discuss how to load and unload XML with the UI; e.g. Storage
		// class
		// TODO TAKE THIS OUT BEFORE RELEASE THE FOLLOWING LINES ARE JUST FOR
		// TETING PURPOSES

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
