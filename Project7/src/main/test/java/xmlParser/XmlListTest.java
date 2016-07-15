package xmlParser;

import static org.junit.Assert.*;

import org.junit.Test;

public class XmlListTest {

	@Test
	public void toXmlStringTest() {
		XmlList list = new XmlList();
		XmlNode node = new XmlNode("TestNode", new XmlString("TestString"));
		node.addAtribute(new XmlAttribute("test1","test1"));
		node.addAtribute(new XmlAttribute("test","test"));
		list.add(node);
		list.add(node);
		list.add(node);
		list.add(node);
		assertEquals("\n<TestNode test1=\"test1\" test=\"test\">TestString</TestNode>\n"
				+"<TestNode test1=\"test1\" test=\"test\">TestString</TestNode>\n"
				+"<TestNode test1=\"test1\" test=\"test\">TestString</TestNode>\n"
				+"<TestNode test1=\"test1\" test=\"test\">TestString</TestNode>\n",list.toXmlString());
		
	}

}
