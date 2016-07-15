package models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlParser.Parsable;
import xmlParser.XmlList;
import xmlParser.XmlNode;

/**
 * Represents the XML tag "exercises", implements {@link Parsable}.
 *
 */
public class ExerciseCatalog extends Observable implements Parsable, Observer {

	private ArrayList<Exercise> exercises;

	public ExerciseCatalog() {
		exercises = new ArrayList<Exercise>();
	}

	/**
	 * Adds an Exercise to the {@code Catalog}
	 * 
	 * @param exercise
	 *            An {@link Exercise}-Object.
	 */
	public void addExercise(Exercise exercise) {
		exercise.addObserver(this);
		this.exercises.add(exercise);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Returns the the list of Exercieses currently stored in the
	 * {@code Catalog}
	 * 
	 * @return An {@code ArrayList<Exercise>} of exercieses
	 */
	public ArrayList<Exercise> getExercises() {
		return exercises;

	}

	public Parsable loadfromXML(Element element) throws Exception {

		// Gets all exercise items form the variable exerciselist
		// and passes each to a Exercise object as well as adding
		// them to the exercises variable.
		NodeList exerciselist = element.getElementsByTagName("exercise");
		exercises.clear();

		for (int i = 0; i < exerciselist.getLength(); i++) {
			Node node = exerciselist.item(i);
			Element nodeElement = (Element) node;
			Exercise excercise = (Exercise) new Exercise().loadfromXML(nodeElement);
			this.addExercise(excercise);
		}
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		XmlList XmlExercises = new XmlList();
		for (Exercise exercise : this.exercises) {
			XmlExercises.add(exercise);
		}

		XmlNode XmlObj = new XmlNode("exercises", XmlExercises);
		XmlObj.setRoot(true);
		return XmlObj;
	}

	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}

}
