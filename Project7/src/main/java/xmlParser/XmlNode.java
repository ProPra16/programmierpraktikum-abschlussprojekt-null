package xmlParser;

public class XmlNode extends XmlValue {
	String name;
	XmlValue value;
	boolean isRoot;

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public void setValue(XmlValue value) {
		this.value = value;
	}

	public XmlNode(String name, XmlValue jsonValue) {
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

	public void addAtribute(XmlAtribute atribute) {
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
