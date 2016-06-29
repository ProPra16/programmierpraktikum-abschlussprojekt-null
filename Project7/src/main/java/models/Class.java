package models;

import org.w3c.dom.Element;

import xmlModelParser.Parsable;

/**
 * Represents the XML tag "class", implements {@link Parsable}.
 *
 */
public class Class implements Parsable {

	private String name;
	

	private String content;

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
	}

	/**
	 * Returns the name of the {@code Class}
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
