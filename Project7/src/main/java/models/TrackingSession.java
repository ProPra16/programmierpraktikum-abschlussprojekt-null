package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlParser.Parsable;
import xmlParser.ParserException;
import xmlParser.XmlAtribute;
import xmlParser.XmlList;
import xmlParser.XmlNode;

public class TrackingSession extends Observable implements Parsable, Observer {
	/**
	 * Start date of tracking session
	 */
	private Date startDate;

	/**
	 * Exercise name of tracking session
	 */
	private String exerciseName;

	/**
	 * All tracking points
	 */
	private List<TrackingData> data;

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

	public TrackingSession() {
		data = new ArrayList<TrackingData>();
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

		for (TrackingData trackingData : data) {
			switch (trackingData.getMode()) {
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
	public Parsable loadfromXML(Element element) throws Exception {
		// ExerciseName
		this.exerciseName = element.getAttribute("exerciseName");
		// StartDate
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try {
			this.startDate=format.parse(element.getAttribute("startDate"));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParserException(
					"The contents of the Attribute \"startDate\" could not be parsed.\n Check if the Attribute is missing or not of the following format\n:"
							+ format.toPattern());
		}
		// Data
		NodeList trackingDataListList = element.getElementsByTagName("TrackingDataList");

		Element trackingDataContent = (Element) trackingDataListList.item(0);
		NodeList trackingDataList = trackingDataContent.getElementsByTagName("TrackingData");
		data.clear();

		for (int i = 0; i < trackingDataList.getLength(); i++) {

			Node node = trackingDataList.item(i);
			Element nodeElement = (Element) node;
			TrackingData mTrackingData = (TrackingData) new TrackingData().loadfromXML(nodeElement);
			this.addData(mTrackingData);
		}
		return this;
	}

	private void addData(TrackingData mTrackingData) {
		mTrackingData.addObserver(this);
		this.data.add(mTrackingData);
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public XmlNode objectToXMLObject() {

		XmlList mList = new XmlList();
		XmlList tDataList = new XmlList();

		for (TrackingData tdata : data) {
			if (tdata.getEnd()!=null&&tdata!=null)
			{
			tDataList.add(tdata);
			}
		}

		mList.add(new XmlNode("TrackingDataList", tDataList));
		XmlNode tSessionNode = new XmlNode("TrackingSession", mList);

		tSessionNode.addAtribute(new XmlAtribute("exerciseName", this.exerciseName));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		tSessionNode.addAtribute(new XmlAtribute("startDate", formatter.format(startDate)));
		
		return tSessionNode;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.setChanged();
		this.notifyObservers();
		
	}
}
