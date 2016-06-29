package models;

import org.w3c.dom.Element;

import xmlModelParser.Parsable;

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
	public Parsable loadfromXML(Element element) {
		name = element.getAttribute("name");
		content = element.getTextContent();
		return this;
	}

}
