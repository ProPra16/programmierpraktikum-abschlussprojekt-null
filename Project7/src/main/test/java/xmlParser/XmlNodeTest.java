package xmlParser;

import static org.junit.Assert.*;

import org.junit.Test;

public class XmlNodeTest {
	
	
	@Test
	public void isEmptyTest() {
		XmlNode node = new XmlNode("TestNode", new XmlString("TestString"));
		XmlNode emptyNode = new XmlNode("TestNode",new XmlString(""));
		
		assertTrue(emptyNode.isEmpty());
		assertFalse(node.isEmpty());
	}
	
	@Test
	public void toXmlString() {
		XmlNode node = new XmlNode("TestNode", new XmlString("TestString"));
		node.addAtribute(new XmlAttribute("test1","test1"));
		node.addAtribute(new XmlAttribute("test","test"));
		XmlNode emptyNode = new XmlNode("TestNode",new XmlString(""));
		emptyNode.addAtribute(new XmlAttribute("test","test"));
		emptyNode.addAtribute(new XmlAttribute("test1","test1"));
		assertEquals("<TestNode test=\"test\" test1=\"test1\"/>\n",emptyNode.toXmlString());
		assertEquals("<TestNode test1=\"test1\" test=\"test\">TestString</TestNode>\n",node.toXmlString());
	}
	

}
