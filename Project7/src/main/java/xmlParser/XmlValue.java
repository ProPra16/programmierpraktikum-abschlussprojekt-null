package xmlParser;

import java.util.ArrayList;

public abstract class XmlValue {

	protected ArrayList<XmlAtribute> atributes = new ArrayList<XmlAtribute>();

	public abstract String toXmlString();

	public abstract String objectToString();

	protected String AtributestoStringLine() {
		String atributeLine = "";
		for (XmlAtribute atribute : atributes) {
			atributeLine += " " + atribute.name + "=\"" + atribute.atribute + "\"";
		}
		return atributeLine;
	}

	public boolean printName() {
		return false;
	}
}
