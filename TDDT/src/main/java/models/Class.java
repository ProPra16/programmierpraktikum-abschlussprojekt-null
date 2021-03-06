package models;

import java.util.Observable;

import org.w3c.dom.Element;

import xmlParser.Parsable;
import xmlParser.XmlAttribute;
import xmlParser.XmlNode;
import xmlParser.XmlString;

/**
 * Represents the XML tag "class", implements {@link Parsable}.
 *
 */
public class Class extends Observable implements Parsable {


	private String name;



	private String content;
	
	
	public Class() {
		super();
		this.content="";
		this.name="";
	}
	
	/**
	 * Returns the content of the {@code Class}
	 * 
	 * @return A {@link String} representation of this {@code Class}'s content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content of the {@code Class}
	 * 
	 * @param content
	 *            A {@link String} representation of the new content
	 */
	public void setContent(String content) {
		this.content = content;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Returns the name of the {@code Class}
	 * 
	 * @return A {@link String} representation of the Name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the {@code Class}
	 * @param name A {@link String} representation of the Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Parsable loadfromXML(Element element) throws Exception {
		name = element.getAttribute("name");
		content = element.getTextContent();
		return this;

	}

	@Override
	public XmlNode objectToXMLObject() {
		XmlNode xmlObj = new XmlNode("class", new XmlString(this.content));
		xmlObj.addAtribute(new XmlAttribute("name", this.name));
		return xmlObj;
	}

}
