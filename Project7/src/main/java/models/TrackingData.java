package models;

import java.util.Date;

import org.w3c.dom.Element;

import xmlParser.Parsable;
import xmlParser.ParserException;
import xmlParser.XmlNode;

public class TrackingData implements Parsable{
	
	/**
	 * Cycle mode representation - red, green, blue
	 */
	public enum Mode {
		RED,
		GREEN,
		BLUE
	}
	
	/**
	 * Cycle mode
	 */
	private final Mode mode; 
	
	/**
	 * Start date
	 */
	private final Date start;
	
	/**
	 * End date
	 */
	private Date end;
	
	/**
	 * Duration needed for a step
	 */
	private long duration;
	
	/**
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}
	
	/**
	 * @return the mode string
	 */
	public String getModeString() {
		switch(mode) {
		case RED:
			return "RED";
		case GREEN:
			return "GREEN";
		case BLUE:
			return "BLUE";
		default:
			return "";
		}
	}

	/**
	 * Constructs a tracking data with a start date
	 * @param start
	 */
	public TrackingData(Mode mode, Date start) {
		this.mode = mode;
		this.start = start;
	}
	
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
		if(start != null && end != null) {
			duration = end.getTime() - start.getTime();
		}
	}

	/**
	 * @return The duration needed for a step
	 */
	public long getDuration() {
		return duration;
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
