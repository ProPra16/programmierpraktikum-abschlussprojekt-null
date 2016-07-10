package xmlModelParser;

import java.util.ArrayList;

public class XmlNode extends XmlValue {
	String name;
	XmlValue value;

	public void setValue(XmlValue value) {
		this.value = value;
	}

	public XmlNode(String name, XmlValue jsonValue) {
		this.name = name;
		value = jsonValue;
	}

	public String toXmlString() {
		return "<" + name + this.AtributestoStringLine() + ">" + value.toXmlString() + "</" + name + ">\n";
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

}
