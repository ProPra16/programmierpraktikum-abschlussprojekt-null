package xmlParser;

/**
 * Represents a Node in the XML-file
 *
 */
public class XmlNode extends XmlObject {
	String name;
	XmlObject value;
	

	/**
	 * Indicates weather {@link XmlNode} is a root Node
	 */
	boolean isRoot;
	
	/**
	 * Returns weather {@link XmlNode} is a root Node
	 * @return true if {@link XmlNode} is a root Node, otherwise false.
	 */
	public boolean isRoot() {
		return isRoot;
	}

	/**
	 * Sets weather the {@link XmlNode} is a root Node or not
	 * @param isRoot Set true if {@link XmlNode} is a root Node otherwise false
	 */
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	/**
	 * Sets the value saved between the two Node tags
	 * @param value Some {@link XmlObject}
	 */
	public void setValue(XmlObject value) {
		this.value = value;
	}
	/**
	 * Gets the value stored between the two Node tags
	 * @return XmlObject
	 */
	public XmlObject getValue() {
		return value;
	}
	public XmlNode(String name, XmlObject jsonValue) {
		this.name = name;
		value = jsonValue;
	}

	public String toXmlString() {
		if(this.isRoot||!value.isEmpty())
		{
		return "<" + name + this.AtributestoStringLine()+ ">" + value.toXmlString()+ "</" + name + ">\n";
		}
		return "<" + name + this.AtributestoStringLine()+ "/>\n";
		
	}

	/**
	 * Adds an {@link XmlAttribute} to the {@link XmlNode}
	 * @param atribute Some {@link XmlAtribute}
	 */
	public void addAtribute(XmlAttribute atribute) {
		this.atributes.add(atribute);
	}

	public String objectToString() {
		String string = value.objectToString();

		if (!value.printName()) {
			return string + "\n";
		}
		return "-----" + name + "-----:\n" + string + "\n";
	}

	@Override
	public boolean isEmpty() {
		
		return value.isEmpty();
	}

}
