package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import models.TrackingSession;

public class TrackingService {
	
	/**
	 * Shared TrackingService instance
	 */
	private static TrackingService instance;
	
	/**
	 * All tracking results
	 */
	private final Collection<TrackingSession> trackingResults;
	
	/**
	 * Constructs a tracking service for private purposes
	 */
	private TrackingService() {
		trackingResults = new ArrayList<TrackingSession>();
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
	public synchronized Collection<TrackingSession> getTrackingResults() {
		return this.trackingResults;
	}
	
	/**
	 * Starts a new tracking session 
	 * 
	 * @param exerciseName name of exercise
	 * @param startDate start date
	 * @return the created tracking result
	 */
	public synchronized TrackingSession startSession(String exerciseName, Date startDate) {
		TrackingSession trackingResult = new TrackingSession(exerciseName, startDate); 
		trackingResults.add(trackingResult);
		return trackingResult;
	}	

}
