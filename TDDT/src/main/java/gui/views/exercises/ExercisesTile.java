package gui.views.exercises;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Exercise;;

public class ExercisesTile extends VBox {

	Exercise exercise;

	Label header;
	Text description;

	/**
	 * Constructs the tile as an overview tile
	 * 
	 * @param exercise
	 */
	public ExercisesTile(Exercise exercise) {
		this.exercise = exercise;
		header = new Label();
		header.getStyleClass().add("exercise-header");
		description = new Text();
		description.getStyleClass().add("exercise-description");
		TextFlow descriptionFlow = new TextFlow(description);
		getChildren().addAll(header, descriptionFlow);

		showOverview();

		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				showDetail();
			}
		});

		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				showOverview();
			}
		});

		getStyleClass().add("exercise-tile");
	}

	/**
	 * Gets the related exercise
	 * 
	 * @return
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * Transforms the tile to an overview tile
	 */
	public void showOverview() {
		/*
		 * No cutting of, of description, should be not soooooo long String
		 * exerciseName = (exercise.getName().length() > 50) ?
		 * exercise.getName().substring(0, 49).trim() + "..." :
		 * exercise.getName().trim(); header.setText(exerciseName); String
		 * exerciseDescription = (exercise.getDescription().length() > 100) ?
		 * exercise.getDescription().substring(0, 99).trim() + "..." :
		 * exercise.getDescription().trim();
		 * description.setText(exerciseDescription);
		 */

		header.setText(exercise.getName());
		description.setText(exercise.getDescription());

		if (getStyleClass().contains("exercise-tile-detail"))
			getStyleClass().remove("exercise-tile-detail");
	}

	/**
	 * Transforms the tile to a detail tile
	 */
	public void showDetail() {
		getStyleClass().add("exercise-tile-detail");
	}

}
