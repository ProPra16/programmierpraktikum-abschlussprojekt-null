package xmlParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import models.ExerciseCatalog;

/**
 * Contains methods to serialize or deserialize a {@link ExerciseCatalog}-Object from
 * and to a XML representation.
 *
 *
 */
public class Parser {

	/**
	 * Parses the XML file to a {@link ExerciseCatalog}-Object.
	 * 
	 * @param url
	 *            Takes the Path or URL to the XML file as {@link String}.
	 * @param tagName The name of the xmlTag (e.g. exercises or TrackingSessions) as {@link String}.           
	 * @return A {@link ExerciseCatalog} object, representing the data in the XML-File.
	 * 
	 * @throws Exception
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws ParserException
	 */
	public Element deserailize(String url,String tagName)
			throws SAXException, IOException, ParserConfigurationException, ParserException {

		File file = new File(url);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		NodeList excercisesList = document.getElementsByTagName("TDDT");
		Element element = (Element) excercisesList.item(0);
		

		return (Element) element.getElementsByTagName(tagName).item(0);

	}

	/**
	 * Serializes an {@link XmlObject} element to a specific Path.
	 * @param path Takes the Path or URL to the XML file as {@link String}.
	 * @param serializableValue The {@link XmlObject} which should be parsed 
	 * @throws IOException
	 */
	public void serialize(String path, XmlObject serializableValue) throws IOException {
		String xml = serializableValue.toXmlString();
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
		writer.write(xml);
		writer.close();
	}
}
