package models;

import java.util.Collection;
import java.util.ArrayList;

public class TrackingResult {
	private String exerciseName;
	private final Collection<TrackingData> red;
	private final Collection<TrackingData> green;
	private final Collection<TrackingData> blue;
	
	public TrackingResult() {
		red = new ArrayList<TrackingData>();
		green = new ArrayList<TrackingData>();
		blue = new ArrayList<TrackingData>();
	}
	
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	
	public String getExerciseName() {
		return exerciseName;
	}
	
	public Collection<TrackingData> getRed() {
		return red;
	}
	
	public Collection<TrackingData> getGreen() {
		return green;
	}
	
	public Collection<TrackingData> getBlue() {
		return blue;
	}
}
