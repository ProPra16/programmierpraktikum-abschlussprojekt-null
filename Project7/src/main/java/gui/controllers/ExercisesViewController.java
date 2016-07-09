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
import xmlModelParser.Parser;
import xmlModelParser.ParserException;

public class ExercisesViewController implements Initializable {

	@FXML
	ScrollPane mainPane;
	Catalog catalog;
	boolean inDetailView;

	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Discuss how to load and unload XML with the UI; e.g. Storage
		// class
		// TODO TAKE THIS OUT BEFORE RELEASE THE FOLLOWING LINES ARE JUST FOR
		// TETING PURPOSES
		Parser parser = new Parser();
		try {
			catalog = parser.deserailize("default.xml");
		} catch (SAXException | IOException | ParserConfigurationException | ParserException e) {
			// TODO Handle Error appropriately
			catalog = new Catalog();
			e.printStackTrace();
		}
         try {
			parser.serialize("test.xml", catalog);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		ExercisesGrid exercisesGrid = new ExercisesGrid(catalog.getExercises());
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
