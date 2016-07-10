package services;

import java.util.Collection;

import models.TrackingResult;

public class TrackingService {
	
	private static TrackingService instance;
	private Collection<TrackingResult> trackingResults;
	
	private TrackingService() {
		
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
	
	

}
