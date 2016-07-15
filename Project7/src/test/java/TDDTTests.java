package java;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import services.ServicesTests;

@RunWith(Suite.class)
@SuiteClasses({xmlParser.XmlParserTests.class, ServicesTests.class})
public class TDDTTests {
	
}
