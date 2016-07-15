package xmlParser;

import java.util.ArrayList;

/**
 * Represents a List of XML-Nodes a.k.a. {@link XmlNodes}
 *
 */
public class XmlList extends XmlObject {
	
	/**
	 * List of {@link XmlNode}-Objects
	 */
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

	/**
	 * Adds an {@link XmlNode} to the {@link XmlList}
	 * @param node Some {@link XmlObject}
	 */
	public void add(XmlNode node) {
		list.add(node);
	}

	/**
	 * Adds an Object implementing {@link Parsable} to the {@link XmlList}
	 * @param parsable Some {@link Parsable}
	 */
	public void add(Parsable parsable) {
		list.add(parsable.objectToXMLObject());
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return list.isEmpty();
	}

}
