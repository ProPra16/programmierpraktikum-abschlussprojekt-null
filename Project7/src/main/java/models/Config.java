package models;

import java.util.Observable;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xmlModelParser.Parsable;
import xmlModelParser.ParserException;
import xmlModelParser.XmlAtribute;
import xmlModelParser.XmlList;
import xmlModelParser.XmlNode;
import xmlModelParser.XmlString;

/**
 * Represents the XML tag "config", implements {@link Parsable}.
 *
 */
public class Config extends Observable implements Parsable {
	private boolean babySteps;

	private boolean timeTracking;

	// TimeLimit in milliseconds
	private long timeLimit;

	public Config(boolean babysteps, boolean timetracking, long timeLimit) {
		super();
		this.babySteps = babysteps;
		this.timeTracking = timetracking;
		this.timeLimit = timeLimit;
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
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public Parsable loadfromXML(Element element) throws ParserException {
		// TODO: Discuss, how to implement this class! -->Class Variables

		// Gets the content of every "babysteps" tag from the XML file and
		// sets the variable babysteps to the boolean representation of its
		// Atribute value.
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList babyStepsList = element.getElementsByTagName("babysteps");
		Element babyStepsContent = (Element) babyStepsList.item(0);
		this.babySteps = Boolean.parseBoolean(babyStepsContent.getAttribute("value"));

		// Gets the content of every "timetraking" tag from the XML file and
		// sets the variable timeTracking to the boolean representation of its
		// Atribute value.
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList timeTrackingList = element.getElementsByTagName("timetracking");
		Element TimeTrackingContent = (Element) timeTrackingList.item(0);
		this.timeTracking = Boolean.parseBoolean(TimeTrackingContent.getAttribute("value"));

		if (this.timeTracking) {
			try {
				this.timeLimit = Long.parseLong(TimeTrackingContent.getTextContent());
			} catch (NumberFormatException e) {

				e.printStackTrace();
				// TODO pass a better error message
				throw new ParserException("The Content of <timetracking> is not parsable to long:\n"
						+ TimeTrackingContent.getTextContent());
			}
		}
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		// BabySteps
		XmlNode xmlBabySteps = new XmlNode("babysteps", new XmlString(""));
		xmlBabySteps.addAtribute(new XmlAtribute("value", String.valueOf(this.babySteps)));

		// TimeTracking
		XmlNode xmlTimeTracking = new XmlNode("timetracking", new XmlString(""));
		xmlTimeTracking.addAtribute(new XmlAtribute("value", String.valueOf(this.timeTracking)));
		if (this.timeTracking) {
			xmlTimeTracking.setValue(new XmlString(String.valueOf(timeLimit)));
		}
		XmlList xmlConfigParameters = new XmlList();
		xmlConfigParameters.add(xmlBabySteps);
		xmlConfigParameters.add(xmlTimeTracking);
		XmlNode xmlConfig = new XmlNode("config", xmlConfigParameters);
		return xmlConfig;
	}

}