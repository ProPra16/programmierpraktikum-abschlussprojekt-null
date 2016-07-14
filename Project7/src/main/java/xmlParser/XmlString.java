package xmlParser;

public class XmlString extends XmlValue {
	String valueString;

	public XmlString(String string) {
		valueString = string;
	}

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
