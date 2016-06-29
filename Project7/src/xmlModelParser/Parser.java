package xmlModelParser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import models.Catalog;

/**
 * Contains methods to serialize or deserialize a {@link Catalog}-Object from
 * and to a XML representation.
 *
 *
 */
public class Parser {

	/**
	 * Parses the XML file to a {@link Catalog}-Object
	 * 
	 * @param url
	 *            Take the Path or URL to the XML file as {@link String}.
	 * @return A {@link Catalog} object, representing the data in the XML-File.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Catalog parse(String url) throws SAXException, IOException, ParserConfigurationException {
		
		File file = new File(url);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		NodeList excercisesList = document.getElementsByTagName("exercises");
		Element element = (Element) excercisesList.item(0);

		return (Catalog) new Catalog().loadfromXML(element);

	}

}
