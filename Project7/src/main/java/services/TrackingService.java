package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import models.TrackingResult;

public class TrackingService {
	
	public enum Mode {
		RED,
		GREEN,
		BLUE
	}
	private static TrackingService instance;
	private final Collection<TrackingResult> trackingResults;
	
	private TrackingService() {
		trackingResults = new ArrayList<TrackingResult>();
	}
	
	/**
	 * Gets the shared TrackingService instance
	 * 
	 * @return The shared TrackingService instance
	 */
	public synchronized static TrackingService shared() {
		if(instance == null) {
			instance = new TrackingService();
		}
		return instance;
	}
	
	/**
	 * Gets the list of tracking results
	 * 
	 * @return list of tracking results
	 */
	public synchronized Collection<TrackingResult> getTrackingResults() {
		return this.trackingResults;
	}
	
	/**
	 * Adds a tracking result to the list 
	 * 
	 * @param trackingResult tracking result to be added
	 */
	public synchronized void addTrackingResult(TrackingResult trackingResult) {
		trackingResults.add(trackingResult);
	}
	
	/**
	 * Starts a new tracking session 
	 * 
	 * @param exerciseName name of exercise
	 * @param startDate start date
	 * @return the created tracking result
	 */
	public synchronized TrackingResult startSession(String exerciseName, Date startDate) {
		TrackingResult trackingResult = new TrackingResult(exerciseName, startDate); 
		trackingResults.add(trackingResult);
		return trackingResult;
	}	

}
