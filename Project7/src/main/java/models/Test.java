package models;

import java.util.Observable;

import org.w3c.dom.Element;

import xmlParser.Parsable;
import xmlParser.XmlAtribute;
import xmlParser.XmlNode;
import xmlParser.XmlString;

/**
 * Represents the XML tag "test", implements {@link Parsable}.
 *
 */
public class Test extends Observable implements Parsable {

	private String name;

	private String content;

	public String getContent() {
		return content;
	}

	/**
	 * Sets the content of the {@code Test}
	 * 
	 * @param A
	 *            {@link String} representation of the new content
	 */
	public void setContent(String content) {
		this.content = content;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Returns the name of the {@code Test}
	 * 
	 * @return A {@link String} representation of the Name
	 */
	public String getName() {
		return name;
	}

	@Override
	public Parsable loadfromXML(Element element) throws Exception {
		name = element.getAttribute("name");
		content = element.getTextContent();
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		XmlNode xmlObj = new XmlNode("test", new XmlString(this.content));
		xmlObj.addAtribute(new XmlAtribute("name", this.name));
		return xmlObj;
	}

}
