package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import models.Catalog;
import models.Exercise;
import xmlModelParser.Parser;

public class ExercisesController implements Initializable {

	@FXML
	GridPane exercisesGrid;
	Catalog catalog;
	Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Discuss how to load and unload XML with the UI; e.g. Storage
		// class
		// TODO TAKE THIS OUT BEFORE RELEASE THE FOLLOWING LINES ARE JUST FOR
		// TETING PURPOSES
		Parser parser = new Parser();
		try {

			catalog = parser.parse("default.xml");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Handle Error appropriately
			catalog = new Catalog();
			System.out.println("Shit!!!");

			e.printStackTrace();
		}
	}

	/**
	 * Initializes controller with stage and adds  
	 * @param stage
	 */
	public void initWithStage(Stage stage) {
		this.stage = stage;
		// Arrange exercises on width change
		stage.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth,
					Number newWidth) {
				arrangeExercises();
			}
		});
	}

	/**
	 * Arranges exercises on grid, respective to the current width
	 */
	private void arrangeExercises() {
		
		// Get number of columns from width
		int columnsNumber = 1;
		double width = stage.getWidth();
		if (width > 600)
			columnsNumber = 2;
		if (width > 900)
			columnsNumber = 3;

		exercisesGrid.getChildren().clear();
		exercisesGrid.getColumnConstraints().clear();
		exercisesGrid.getRowConstraints().clear();
		
		for(int i = 0; i < columnsNumber; i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(100.0 / columnsNumber);
			exercisesGrid.getColumnConstraints().add(column);
		}
		
		for (int i = 0; i < catalog.getExercises().size(); i++) {
			Exercise exercise = catalog.getExercises().get(i);

			String exerciseName = (exercise.getName().length() > 50) ? exercise.getName().substring(0, 49).trim() + "..."
					: exercise.getName().trim();
			Label header = new Label(exerciseName);
			header.getStyleClass().add("exercise-header");

			String exerciseDescription = (exercise.getDescription().length() > 100)
					? exercise.getDescription().substring(0, 99).trim() + "..." : exercise.getDescription().trim();
			Text description = new Text(exerciseDescription);
			description.getStyleClass().add("exercise-description");
			TextFlow descriptionFlow = new TextFlow(description);

			GridPane tile = new GridPane();
			tile.getStyleClass().add("exercise-tile");
			tile.add(header, 0, 0);
			tile.add(descriptionFlow, 0, 1);
			tile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					selectExercise(exercise);
				}
			});
			exercisesGrid.add(tile, i % columnsNumber, i / columnsNumber);
			// TODO: tile fill full column excercisesPane.setFillWidth(tile,
			// true);
		}
	}

	/**
	 * Shows the detail view of selected {@link Exercise}
	 * @param exercise
	 */
	private void selectExercise(Exercise exercise) {
		exercisesGrid.getChildren().clear();
		exercisesGrid.getColumnConstraints().clear();
		exercisesGrid.getRowConstraints().clear();
		
		Label header = new Label(exercise.getName().trim());
		header.getStyleClass().add("exercise-header");

		Text description = new Text(exercise.getDescription().trim());
		description.getStyleClass().add("exercise-description");
		TextFlow descriptionFlow = new TextFlow(description);

		GridPane tile = new GridPane();
		tile.getStyleClass().add("exercise-tile");
		tile.getStyleClass().add("exercise-tile-detail");
		tile.add(header, 0, 0);
		tile.add(descriptionFlow, 0, 1);
		tile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				selectExercise(exercise);
			}
		});
		exercisesGrid.add(tile, 0, 0);
	}
}
