package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlParser.Parsable;
import xmlParser.XmlList;
import xmlParser.XmlNode;

public class TrackingSessionCatalog extends Observable implements Parsable, Observer {
	
	/**
	 * All tracking results
	 */
	private final Collection<TrackingSession> trackingSessions;
	
	/**
	 * Constructs a tracking service
	 */
	public TrackingSessionCatalog() {
		trackingSessions = new ArrayList<TrackingSession>();
	}
	
	
	/**
	 * Gets the list of tracking results
	 * 
	 * @return list of tracking results
	 */
	public synchronized Collection<TrackingSession> getTrackingResults() {
		return this.trackingSessions;
	}
	
	/**
	 * Creates and adds a new tracking session 
	 * 
	 * @param exerciseName name of exercise
	 * @param startDate start date
	 * @return the created tracking result
	 */
	public synchronized TrackingSession startSession(String exerciseName, Date startDate) {
		TrackingSession trackingResult = new TrackingSession(exerciseName, startDate); 
		this.addSession(trackingResult);
		return trackingResult;
	}
	private void addSession(TrackingSession session) {
		session.addObserver(this);
		this.trackingSessions.add(session);
		this.setChanged();
		this.notifyObservers();
		
	}

	@Override
	public Parsable loadfromXML(Element element) throws Exception {
		// TODO Auto-generated method stub
	
		NodeList sessionList = element.getElementsByTagName("TrackingSession");
		
		trackingSessions.clear();

		for (int i = 0; i < sessionList.getLength(); i++) {
			Node node = sessionList.item(i);
			Element nodeElement = (Element) node;
			TrackingSession session = (TrackingSession) new TrackingSession().loadfromXML(nodeElement);
			this.addSession(session);
		}
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		// TODO Auto-generated method stub
		XmlList XmlTrackingSession = new XmlList();
		for (TrackingSession exercise : this.trackingSessions) {
			XmlTrackingSession.add(exercise);
		}
		
		XmlNode XmlObj = new XmlNode("TrackingSessions", XmlTrackingSession);
		XmlObj.setRoot(true);
		return XmlObj;
	}


	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
		
	}
}	

