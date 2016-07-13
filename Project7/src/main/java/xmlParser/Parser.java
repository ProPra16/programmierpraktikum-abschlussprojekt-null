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
	 * Parses the XML file to a {@link ExerciseCatalog}-Object
	 * 
	 * @param url
	 *            Take the Path or URL to the XML file as {@link String}.
	 * @return A {@link ExerciseCatalog} object, representing the data in the XML-File.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws ParserException
	 */
	public ExerciseCatalog deserailize(String url)
			throws SAXException, IOException, ParserConfigurationException, ParserException {

		File file = new File(url);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		NodeList excercisesList = document.getElementsByTagName("exercises");
		Element element = (Element) excercisesList.item(0);

		return (ExerciseCatalog) new ExerciseCatalog().loadfromXML(element);

	}

	public void serialize(String path, ExerciseCatalog catalog) throws IOException {
		XmlNode obj = catalog.objectToXMLObject();
		String json = obj.toXmlString();
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
		writer.write(json);
		writer.close();
	}
}
