package gui.views;

import models.Exercise;

public class ExerciseMenuItem extends RemovableMenuItem {
	
	Exercise exercise;
	
	/**
	 * Constructs a menu item from an {@link Exercise}
	 * @param exercise
	 */
	public ExerciseMenuItem(Exercise exercise) {
		super();
		
		this.exercise = exercise;
		setIcon(iconPencilPath);
		setLabel(exercise.getName());
	}

	/**
	 * Returns the {@link Exercise}
	 * @return 
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * Sets the {@link Exercise} and renames the label
	 * @param exercise
	 */
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
		setLabel(exercise.getName());
	}
	
}
