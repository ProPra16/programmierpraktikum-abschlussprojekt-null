package xmlModelParser;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import models.Catalog;
import models.Exercise;

public class ModelStorageController extends Observable implements Observer {
	private static ModelStorageController instance;
	public static final String defaultPath = "Storage/";
	public static final String defaultFileName = "Catalog.xml";
	private Catalog catalog;

	private ModelStorageController() {
	}

	public synchronized static ModelStorageController getInstance() {

		if (instance == null) {
			instance = new ModelStorageController();
			instance.catalog = new Catalog();
			instance.catalog.addObserver(instance);
			createStorage();
		}

		return instance;
	}

	public static String getDefaultRelativeFilePath() {
		return defaultPath + defaultFileName;
	}

	
	public synchronized void importModel(String path)
			throws SAXException, IOException, ParserConfigurationException, ParserException {
		Parser parser = new Parser();
		Catalog tempCatalog=parser.deserailize(path);
		for (Exercise exercise:tempCatalog.getExercises())
		{
			this.catalog.addExercise(exercise);
		}
		
	}

	public synchronized Catalog loadModel()
			throws SAXException, IOException, ParserConfigurationException, ParserException {
		Parser parser = new Parser();
		this.catalog = parser.deserailize(getDefaultRelativeFilePath());
		this.catalog.addObserver(this);
		return this.catalog;
	}

	public void saveModel() throws IOException {
		Parser parser = new Parser();
		parser.serialize(getDefaultRelativeFilePath(), this.catalog);

	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	private static void createStorage() {
		File file = new File(getDefaultRelativeFilePath());
		if (!file.exists()) {
			File dir = new File(defaultPath);
			dir.mkdirs();
			try {
				instance.saveModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			saveModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
