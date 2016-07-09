package models;

import java.util.Observable;

import org.w3c.dom.Element;

import xmlModelParser.Parsable;
import xmlModelParser.ParserException;
import xmlModelParser.XmlAtribute;
import xmlModelParser.XmlNode;
import xmlModelParser.XmlString;

/**
 * Represents the XML tag "test", implements {@link Parsable}.
 *
 */
public class Test implements Parsable {
	
	private String name;
	
	private String content;

	public String getContent() {
		return content;
	}

	/**
	 * Sets the content of the {@code Test}
	 * 
	 * @param A {@link String} representation of the new content
	 */
	public void setContent(String content) {
		this.content = content;
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
	public Parsable loadfromXML(Element element) throws ParserException {
		name = element.getAttribute("name");
		content = element.getTextContent();
		return this;
	}

	@Override
	public XmlNode objectToXMLObject() {
		XmlNode xmlObj = new XmlNode("test",new XmlString(this.content));
		xmlObj.addAtribute(new XmlAtribute("name", this.name));
		return xmlObj;
	}



}
