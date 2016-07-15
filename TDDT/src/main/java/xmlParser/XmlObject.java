package xmlParser;

import java.util.ArrayList;


/**
 * Abstract class to help the Parser to serialize different data scenarios
 */
public abstract class XmlObject {

	protected ArrayList<XmlAttribute> atributes = new ArrayList<XmlAttribute>();

	/**
	 * Returns the {@link XmlObject} as a {@link String} formated as XML
	 * @return {@link String}
	 */
	public abstract String toXmlString();

	/**
	 * Returns the {@link XmlObject} as a {@link XmlString}
	 * @return {@link String}
	 */
	public abstract String objectToString();

	/**
	 * Returns all the attributes stored in the specific {@link XmlObject} as {@link String}.
	 * 
	 * @return A {@link String} formated to be inserted into the XML tag when serialized.
	 */
	protected String AtributestoStringLine() {
		String atributeLine = "";
		for (XmlAttribute atribute : atributes) {
			atributeLine += " " + atribute.name + "=\"" + atribute.atribute + "\"";
		}
		return atributeLine;
	}

	/**
	 * Returns weather the specific {@link XmlObject} adds its name on the top of the returning {@link String}
	 * when the Method {@code objectToString} is invoked
	 * 
	 * @return true if the {@code objectToString} prints the Objects name at the beginning of its returning {@link String}.
	 */
	public boolean printName() {
		return false;
	}

	/**
	 * Returns weather the specific {@link XmlObject} has a value.
	 * @return true if {@link XmlObject} has no value, otherwise false.
	 */
	public abstract boolean isEmpty();
}
