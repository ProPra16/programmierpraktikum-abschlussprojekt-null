package xmlParser;

/**
 * Represents a Attribute of a {@link XmlNode}
 *
 */
public class XmlAttribute {

	/**
	 * Represents the Name of the Attribute
	 */
	String name;
	/**
	 * Represents the value of the Attribute
	 */
	String atribute;

	public XmlAttribute(String name, String atribute) {
		this.name = name;
		this.atribute = atribute;
	}

}
