package xmlModelParser;

import java.util.Observer;

import org.w3c.dom.Element;

/**
 * An interface to enable the {@link Parser} to parse the implementing class
 * form an XML-File structure.
 *
 *
 */
public interface Parsable {

	/**
	 * @param element
	 *            Takes a {@link org.w3c.dom.Element} and loads the provided
	 *            XML-Parameters into the class.
	 * @return A {@link Parsable}-Object representation of the DOM.
	 * @throws ParserException
	 */
	public Parsable loadfromXML(Element element) throws ParserException;

	/**
	 * Returns an XMLObject of the class
	 * 
	 * @return {@link XmlNode}
	 */
	public XmlNode objectToXMLObject();

}
