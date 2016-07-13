package models;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import xmlParser.Parsable;
import xmlParser.ParserException;
import xmlParser.XmlNode;

import java.util.ArrayList;

public class TrackingSession implements Parsable {
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
	private final List<TrackingData> data;
	
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
	public List<TrackingData> getData() {
		return data;
	}
	
	/**
	 * Adds all durations  
	 * 
	 * @return sums of duration (red, green, blue)
	 */
	public long[] getDuration() {
		long redDuration = 0;
		long greenDuration = 0;
		long blueDuration = 0;
		
		for(TrackingData trackingData : data) {
			switch(trackingData.getMode()) {
			case RED:
				redDuration += trackingData.getDuration() / 100.0;
				break;
			case GREEN:
				greenDuration += trackingData.getDuration() / 100.0;
				break;
			case BLUE:
				blueDuration += trackingData.getDuration() / 100.0;
				break;
			}
		}
		
		return new long[] { redDuration, greenDuration, blueDuration };
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
