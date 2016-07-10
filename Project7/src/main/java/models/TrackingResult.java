package models;

import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;

public class TrackingResult {
	private final Date startDate;
	private final String exerciseName;
	private final Collection<TrackingData> data;
	
	public TrackingResult(String exerciseName, Date startDate) {
		data = new ArrayList<TrackingData>();
		
		this.exerciseName = exerciseName;
		this.startDate = startDate;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public String getExerciseName() {
		return exerciseName;
	}
	
	public Collection<TrackingData> getData() {
		return data;
	}
}
