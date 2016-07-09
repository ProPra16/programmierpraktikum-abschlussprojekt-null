package xmlModelParser;


import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import models.Catalog;

public class ModelStorageController {
	private static ModelStorageController instance;

	private ModelStorageController() {
	}

	public static synchronized ModelStorageController getInstance() {
		if (instance == null) {
			instance = new ModelStorageController();
		}
		return instance;
	}

	public Catalog loadModel(String path) throws SAXException, IOException, ParserConfigurationException, ParserException
	{
		Parser parser = new Parser();
		return parser.deserailize(path);
	}
	public void saveModel(String path, Catalog catalog) throws IOException {
		Parser parser = new Parser();
		parser.serialize(path, catalog);
		
	}
	
	
}
