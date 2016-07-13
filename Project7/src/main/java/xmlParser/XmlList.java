package xmlParser;

import java.util.ArrayList;

public class XmlList extends XmlValue {
	ArrayList<XmlNode> list = new ArrayList<XmlNode>();

	public XmlList() {
	}

	public XmlList(ArrayList<Parsable> parsableList) {
		for (Parsable parsable : parsableList) {
			add(parsable);
		}
	}

	public String toXmlString() {
		String xml = "\n";

		for (XmlNode obj : list) {
			xml += obj.toXmlString();
		}
		int length = xml.length();
		return xml.substring(0, length) + "";
	}

	public String objectToString() {
		String string = "";

		for (XmlNode obj : list) {
			string += obj.objectToString();
		}
		int length = string.length();
		return string.substring(0, length) + "\n";
	}

	public void add(XmlNode obj) {
		list.add(obj);
	}

	public void add(Parsable parsable) {
		list.add(parsable.objectToXMLObject());
	}

}
