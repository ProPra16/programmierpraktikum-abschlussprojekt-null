package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlModelParser.Parsable;

/**
 * Represents the XML tag "exercises", implements {@link Parsable}.
 *
 */
public class Catalog implements Parsable {
	private String name; // FIXME A Tag declaring the name of a "Catalog" is
							// nonexistent in the sample data structure, should
							// this
							// be ignored?
	private ArrayList<Exercise> exercises;

	public Catalog() {
		exercises = new ArrayList<Exercise>();
	}

	public Catalog(String name, ArrayList<Exercise> excercises) {
		this.name = name;
		this.exercises = excercises;
	}

	public Catalog(String name, Exercise... excercises) {
		this.name = name;
		this.exercises = (ArrayList<Exercise>) Arrays.asList(excercises);
	}

	/**
	 * Adds an Exercise to the {@code Catalog}
	 * 
	 * @param exercise
	 *            An {@link Exercise}-Object.
	 */
	public void addExercise(Exercise exercise) {
		this.exercises.add(exercise);
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

	public Parsable loadfromXML(Element element) {

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
}
