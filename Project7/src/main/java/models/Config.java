package models;

import java.util.Observable;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xmlParser.Parsable;
import xmlParser.ParserException;
import xmlParser.XmlAtribute;
import xmlParser.XmlList;
import xmlParser.XmlNode;
import xmlParser.XmlString;

/**
 * Represents the XML tag "config", implements {@link Parsable}.
 *
 */
public class Config extends Observable implements Parsable {
	private boolean babySteps;

	private boolean timeTracking;

	// time limit in milliseconds
	private long bStepsTimeLimit;

	public Config(boolean babysteps, boolean timetracking, long bStepsTimeLimit) {
		super();
		this.babySteps = babysteps;
		this.timeTracking = timetracking;
		this.bStepsTimeLimit = bStepsTimeLimit;
	}

	public Config() {

	}

	public boolean isBabySteps() {
		return babySteps;
	}

	public void setBabySteps(boolean babySteps) {
		this.babySteps = babySteps;
		this.setChanged();
		this.notifyObservers();
	}

	public boolean isTimeTracking() {
		return timeTracking;
	}

	public void setTimeTracking(boolean timeTracking) {
		this.timeTracking = timeTracking;
		this.setChanged();
		this.notifyObservers();
	}

	public long getTimeLimit() {
		return bStepsTimeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.bStepsTimeLimit = timeLimit;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public Parsable loadfromXML(Element element) throws ParserException {
		// TODO: Discuss, how to implement this class! -->Class Variables

		// Gets the content of every "timetraking" tag from the XML file and
		// sets the variable timeTracking to the boolean representation of its
		// Atribute value.
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList timeTrackingList = element.getElementsByTagName("timetracking");
		Element TimeTrackingContent = (Element) timeTrackingList.item(0);
		this.timeTracking = Boolean.parseBoolean(TimeTrackingContent.getAttribute("value"));

		// Gets the content of every "babysteps" tag from the XML file and
		// sets the variable babysteps to the boolean representation of its
		// Atribute value.
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList babyStepsList = element.getElementsByTagName("babysteps");
		Element babyStepsContent = (Element) babyStepsList.item(0);
		this.babySteps = Boolean.parseBoolean(babyStepsContent.getAttribute("value"));

		if (this.babySteps) {
			try {
				this.bStepsTimeLimit = Long.parseLong(babyStepsContent.getTextContent());
			} catch (NumberFormatException e) {

				e.printStackTrace();
				// TODO pass a better error message
				throw new ParserException("The Content of <babysteps /> cannot be parsed as long:\n"
						+ babyStepsContent.getTextContent());
			}
		}
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		// BabySteps
		XmlNode xmlBabySteps = new XmlNode("babysteps", new XmlString(""));
		xmlBabySteps.addAtribute(new XmlAtribute("value", String.valueOf(this.babySteps)));
		if (this.babySteps) {
			xmlBabySteps.setValue(new XmlString(String.valueOf(bStepsTimeLimit)));
		}

		// TimeTracking
		XmlNode xmlTimeTracking = new XmlNode("timetracking", new XmlString(""));
		xmlTimeTracking.addAtribute(new XmlAtribute("value", String.valueOf(this.timeTracking)));
		
		XmlList xmlConfigParameters = new XmlList();
		xmlConfigParameters.add(xmlBabySteps);
		xmlConfigParameters.add(xmlTimeTracking);
		XmlNode xmlConfig = new XmlNode("config", xmlConfigParameters);
		return xmlConfig;
	}

}