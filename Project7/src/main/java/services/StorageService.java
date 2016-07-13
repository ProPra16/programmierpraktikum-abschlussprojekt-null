package services;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import models.Exercise;
import models.ExerciseCatalog;
import models.TrackingSessionCatalog;
import xmlParser.Parser;
import xmlParser.ParserException;

public class StorageService extends Observable implements Observer {
	private static StorageService instance;
	public static final String defaultPath = ".tddt/";
	public static final String defaultFileName = "Catalog.xml";
	private ExerciseCatalog exerciseCatalog = new ExerciseCatalog();
	private TrackingSessionCatalog tSessionCatalog= new TrackingSessionCatalog();

	

	private StorageService() {
	}

	public synchronized static StorageService getInstance() {

		if (instance == null) {
			instance = new StorageService();
			instance.exerciseCatalog.addObserver(instance);
			
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
		
		ExerciseCatalog tempCatalog=parser.deserailize(path);
		for (Exercise exercise:tempCatalog.getExercises())
		{
			this.exerciseCatalog.addExercise(exercise);
		}
		
	}

	public synchronized ExerciseCatalog loadModel()
			throws SAXException, IOException, ParserConfigurationException, ParserException {
		Parser parser = new Parser();
		this.exerciseCatalog = parser.deserailize(getDefaultRelativeFilePath());
		this.exerciseCatalog.addObserver(this);
		return this.exerciseCatalog;
	}

	public void saveModel() throws IOException {
		Parser parser = new Parser();
		parser.serialize(getDefaultRelativeFilePath(), this.exerciseCatalog);

	}

	public ExerciseCatalog getExerciseCatalog() {
		return this.exerciseCatalog;
	}
	
	public TrackingSessionCatalog gettSessionCatalog() {
		return tSessionCatalog;
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
