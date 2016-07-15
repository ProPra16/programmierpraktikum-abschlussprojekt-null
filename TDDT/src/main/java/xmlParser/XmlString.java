package xmlParser;

/**
 * Represents a value of a {@link XmlNode} as {@link String}
 *
 */
public class XmlString extends XmlObject {
	/**
	 * Represents the Value of this {@link XmlString} as {@link String}
	 */
	String valueString;
	
	public XmlString(String string) {
		valueString = string;
	}
	
	/**
	 * Sets the Value of the {@link XmlString} as {@link String}
	 * @param valueString
	 */
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	@Override
	public String toXmlString() {
		return valueString;
	}

	@Override
	public String objectToString() {
		return valueString;
	}

	public boolean printName() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return valueString.isEmpty();
	}

}
