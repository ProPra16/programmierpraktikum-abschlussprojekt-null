package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.Element;

import xmlParser.Parsable;
import xmlParser.ParserException;
import xmlParser.XmlNode;

public class TrackingSessionCatalog implements Parsable {
	
	/**
	 * All tracking results
	 */
	private final Collection<TrackingSession> trackingResults;
	
	/**
	 * Constructs a tracking service for private purposes
	 */
	public TrackingSessionCatalog() {
		trackingResults = new ArrayList<TrackingSession>();
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
	 * Adds a new tracking session 
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


	@Override
	public Parsable loadfromXML(Element element) throws ParserException {
		// TODO Auto-generated method stub
		return this;
	}


	@Override
	public XmlNode objectToXMLObject() {
		// TODO Auto-generated method stub
		return null;
	}	

}
