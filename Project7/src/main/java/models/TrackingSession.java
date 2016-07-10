package models;

import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;

public class TrackingSession {
	/**
	 * Start date of tracking session
	 */
	private final Date startDate;
	
	/**
	 * Exercise name of tracking session
	 */
	private final String exerciseName;
	
	/**
	 * All tracking points
	 */
	private final Collection<TrackingData> data;
	
	/**
	 * Constructs a tracking session with name start date 
	 * 
	 * @param exerciseName
	 * @param startDate
	 */
	public TrackingSession(String exerciseName, Date startDate) {
		data = new ArrayList<TrackingData>();
		
		this.exerciseName = exerciseName;
		this.startDate = startDate;
	}
	
	/**
	 * @return start date of session
	 */
	public Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * @return exercise name of session
	 */
	public String getExerciseName() {
		return exerciseName;
	}
	
	/**
	 * @return tracking points
	 */
	public Collection<TrackingData> getData() {
		return data;
	}
}
