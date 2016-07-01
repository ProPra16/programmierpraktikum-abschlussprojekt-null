package gui.views;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Exercise;

public class ExercisesGrid extends GridPane {

	final double gap = 20.0;
	List<ExercisesTile> exercisesTiles;
	
	public interface ExerciseHandler {
		public void handle(Exercise exercise);
	}
	
	public ExercisesGrid(List<Exercise> exercises) {
		exercisesTiles = new ArrayList<ExercisesTile>();
		for(Exercise exercise : exercises) {
			ExercisesTile exercisesTile = new ExercisesTile(exercise);
			exercisesTiles.add(exercisesTile);
		}
		// Arrange exercises on width change
		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				arrangeExercises();
			}
		});
		getStyleClass().add("exercises");
		setVgap(gap);
		setHgap(gap);
	}
	
	/**
	 * Adds a select exercise handler 
	 * @param exerciseHandler
	 */
	public void addSelectExerciseHandler(ExerciseHandler exerciseHandler) {
		for(ExercisesTile exercisesTile : exercisesTiles) {
			exercisesTile.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					exerciseHandler.handle(exercisesTile.getExercise());
				}
			});
		}
	}
	
	/**
	 * Arranges {@link ExercisesTile}s on grid, respective to the current width
	 */
	private void arrangeExercises() {		
		// Get number of columns from width
		int columnsNumber = 1;
		double width = getWidth();
		if (width > 600)
			columnsNumber = 2;
		if (width > 900)
			columnsNumber = 3;

		// Only arrange, if number of columns did change
		if(getColumnConstraints().size() == columnsNumber)
			return;
		
		// Clear grid
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();
		
		// Calculate percentage width and set it for each column 
		for(int i = 0; i < columnsNumber; i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(100.0 / columnsNumber);
			getColumnConstraints().add(column);
		}
		
		for(int i = 0; i < exercisesTiles.size(); i++) {
			ExercisesTile exercisesTile = exercisesTiles.get(i);
			add(exercisesTile, i % columnsNumber, i / columnsNumber);
		}
	}
	
	
	/**
	 * Shows the detail view of selected {@link ExerciseTile}
	 * @param exercise
	 */
	private void selectExercise(ExercisesTile exerciseTile) {
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();
		
		exerciseTile.showDetail();
		
		add(exerciseTile, 0, 0);
		GridPane.setHgrow(exerciseTile, Priority.ALWAYS);
		GridPane.setVgrow(exerciseTile, Priority.ALWAYS);
	}
	
	
}
