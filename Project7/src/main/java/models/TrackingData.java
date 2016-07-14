package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

import org.w3c.dom.Element;

import xmlParser.Parsable;
import xmlParser.ParserException;
import xmlParser.XmlAtribute;
import xmlParser.XmlNode;
import xmlParser.XmlString;

public class TrackingData extends Observable implements Parsable{
	
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
	private Mode mode; 
	
	/**
	 * Start date
	 */
	private Date start;
	
	/**
	 * End date
	 */
	private Date end;
	
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
	
	protected TrackingData() {}

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
		this.setChanged();
		this.notifyObservers();
		
	}

	/**
	 * @return The duration needed for a step
	 */
	public long getDuration() {
		if(start != null && end != null) {
			return end.getTime() - start.getTime();
		}
		return 0;
	}

	@Override
	public Parsable loadfromXML(Element element) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try{
		this.mode = Mode.valueOf(element.getAttribute("mode").toUpperCase());
		this.setEnd(formatter.parse(element.getAttribute("start")));
		this.start = formatter.parse(element.getAttribute("end"));
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			throw new ParserException("Unable to parse the Atribute \"mode\".\nPlease check your xml-File and the log.");
		} catch (ParseException e) {
			
			e.printStackTrace();
			throw new ParserException("Unable to parse the Tag \"start\" or \"end\" .\nPlease check your xml-File and the log.");
		}
		
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		XmlNode node = new XmlNode("TrackingData",new XmlString(""));
		node.addAtribute(new XmlAtribute("mode", this.getModeString()));
		node.addAtribute(new XmlAtribute("start", formatter.format(getStart())));
		node.addAtribute(new XmlAtribute("end", formatter.format(getEnd())));
		return node;
	}
	
}
