package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlModelParser.Parsable;

/**
 * Represents the XML tag "exercise", implements {@link Parsable}
 *
 */
public class Exercise implements Parsable {
	private String name;
	private String description;
	private ArrayList<Class> classes;
	private ArrayList<Test> tests;
	private Config config;

	public Exercise(String name, String description, ArrayList<Class> classes, ArrayList<Test> tests, Config config) {
		this.name = name;
		this.description = description;
		this.classes = classes;
		this.tests = tests;
		this.config = config;
	}

	public Exercise(String name, String description, Class[] classes, Test[] tests, Config config) {
		this.name = name;
		this.description = description;
		this.classes = (ArrayList<Class>) Arrays.asList(classes);
		this.tests = (ArrayList<Test>) Arrays.asList(tests);
		this.config = config;
	}

	public Exercise() {
		classes = new ArrayList<Class>();
		tests = new ArrayList<Test>();
	}

	/**
	 * Adds a {@link Test} to the {@code Exercise}
	 * 
	 * @param test
	 *            The {@link Test} that is to be added
	 */
	public void addTest(Test test) {
		this.tests.add(test);
	}

	/**
	 * Adds a {@link Class} to the {@code Exercise}
	 * 
	 * @param mClass
	 *            The {@link Class} that is to be added
	 */
	public void addClass(Class mClass) {
		this.classes.add(mClass);
	}

	/**
	 * Returns the Name of the {@code Exercise}
	 * 
	 * @return A {@link String} representation of the Name
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the Description of the {@code Exercise}
	 * 
	 * @return A {@link String} representation of the Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the List of Classes stored in the {@code Exercise}
	 * 
	 * @return A {@code ArrayList<T>} of {@link Class} objects
	 */
	public ArrayList<Class> getClasses() {
		return classes;
	}

	/**
	 * Returns the List of Tests stored in the {@code Exercise}
	 * 
	 * @return A {@code ArrayList<T>} of {@link Test} objects
	 */
	public List<Test> getTests() {
		return tests;
	}

	/**
	 * Returns the Configuration stored in the {@code Exercise}
	 * 
	 * @return The {@link Config} object stored in the {@code Exercise} object
	 */
	public Config getConfig() {
		return config;
	}

	public Parsable loadfromXML(Element element) {
		// TODO implement method

		// Gets the content of the "description" tag from the XML file and sets
		// it to the identically named global class variable
		NodeList contentList = element.getElementsByTagName("description");
		Element descriptionContent = (Element) contentList.item(0);
		this.description = descriptionContent.getTextContent();

		// Gets the content of the "name" value from the XML file and sets
		// it to the identically named global class variable
		// TODO Check if this is "ok" with the given data format!
		this.name = element.getAttribute("name");

		// Gets the content of every "classes" tag from the XML file and
		// adds it to the "classes"-list
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList classesList = element.getElementsByTagName("classes");
		Element classesContent = (Element) classesList.item(0);

		// Gets all class items form the variable classesList
		// and passes each to a Class object as well as adding
		// them to the List<Class> variable.

		NodeList classlist = classesContent.getElementsByTagName("class");
		classes.clear();

		for (int i = 0; i < classlist.getLength(); i++) {

			Node node = classlist.item(i);
			Element nodeElement = (Element) node;
			Class mClass = (Class) new Class().loadfromXML(nodeElement);
			this.addClass(mClass);
		}

		// Gets the content of every "tests" tag from the XML file and
		// adds it to the "tests"-list
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList testsList = element.getElementsByTagName("tests");
		Element testsContent = (Element) testsList.item(0);

		// Gets all test items form the variable classesList
		// and passes each to a Test object as well as adding
		// them to the List<Test> variable.

		NodeList testlist = testsContent.getElementsByTagName("test");
		tests.clear();

		for (int i = 0; i < testlist.getLength(); i++) {

			Node node = testlist.item(i);
			Element nodeElement = (Element) node;
			Test mTest = (Test) new Test().loadfromXML(nodeElement);
			this.addTest(mTest);
		}

		// Gets the content of every "config" tag from the XML file and
		// creates a new Config object, which is then saved to the global
		// class variable "config"
		// !!!Takes only the first Tag in the list, all others are ignored!!!
		NodeList configList = element.getElementsByTagName("config");
		Element configContent = (Element) configList.item(0);
		this.config = (Config) new Config().loadfromXML(configContent);

		return this;
	}

}
