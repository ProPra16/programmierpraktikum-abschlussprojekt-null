package xmlParser;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Element;

import models.Class;
import models.Config;
import models.Exercise;
import models.ExerciseCatalog;
import xmlParser.Parser;
import xmlParser.XmlList;
import xmlParser.XmlNode;



public class ParserTest {

	@Test
	public void SerializationAndDeserialization() throws Exception{
		File file = new File ("serializationTest.xml");
		file.delete();
		
		Exercise exercise = new Exercise();
		exercise.setName("TestExercise");
		
		Class mClass = new Class();
		mClass.setContent("TestContent");
		mClass.setName("TestClass");
		exercise.addClass(mClass);
		
		models.Test test = new models.Test();
		test.setContent("TestContent");
		test.setName("TestName");
		exercise.addTest(test);
		
		Config config = new Config();
				config.setBabySteps(true);
				config.setTimeTracking(true);
				config.setTimeLimit(778149000);
				ExerciseCatalog catalog= new ExerciseCatalog();
				exercise.setConfig(config);
		catalog.addExercise(exercise);
		Parser parser = new Parser();
		XmlList nodesList =new XmlList();
		nodesList.add(catalog);
		XmlNode node = new XmlNode("TDDT",nodesList);
		parser.serialize("serializationTest.xml",node);
		Element element =parser.deserailize("serializationTest.xml", "exercises");
		
		assertEquals(catalog.objectToXMLObject().toXmlString(), new ExerciseCatalog().loadfromXML(element).objectToXMLObject().toXmlString());
		file.delete();
	}
	
}
