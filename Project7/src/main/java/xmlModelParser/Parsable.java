package xmlModelParser;

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
	 *            Takes a {@link org.w3c.dom.NodeList} of exercises (represented
	 *            as {@link org.w3c.dom.Element})
	 * @return A {@link Parsable}-Object representation of the DOM.
	 */
	public Parsable loadfromXML(Element element);
}
