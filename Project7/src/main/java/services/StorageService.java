package services;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import models.ExerciseCatalog;
import models.TrackingSessionCatalog;
import xmlParser.Parser;
import xmlParser.XmlList;
import xmlParser.XmlNode;

public class StorageService extends Observable implements Observer {
	private static StorageService instance;
	public static final String defaultPath = ".tddt/";
	public static final String defaultFileName = "Catalog.xml";
	private ExerciseCatalog exerciseCatalog = new ExerciseCatalog();
	private TrackingSessionCatalog tSessionCatalog = new TrackingSessionCatalog();

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

	public synchronized void importModel(String path,boolean acceptEmptyExerciseCatalog) throws Exception {

		Parser parser = new Parser();

		this.exerciseCatalog = (ExerciseCatalog) new ExerciseCatalog()
				.loadfromXML(parser.deserailize(path, "exercises"));
		try{
		this.tSessionCatalog = (TrackingSessionCatalog) new TrackingSessionCatalog()
				.loadfromXML(parser.deserailize(path, "TrackingSessions"));
		}
		catch (NullPointerException e) {
			this.tSessionCatalog = new TrackingSessionCatalog();
			e.printStackTrace();
		}
		if(this.exerciseCatalog.getExercises().isEmpty()&&!acceptEmptyExerciseCatalog)
		{
			throw new Exception(".xml file does not contain Exercises");
		}
		
		this.exerciseCatalog.addObserver(this);
		this.tSessionCatalog.addObserver(this);
		this.saveModel();
	}

	public synchronized void loadModel() throws Exception {
		this.importModel(getDefaultRelativeFilePath(),true);
	}

	public void saveModel() throws IOException {
		Parser parser = new Parser();
		XmlList xmlStorage = new XmlList();
		xmlStorage.add(this.exerciseCatalog.objectToXMLObject());
		xmlStorage.add(this.tSessionCatalog.objectToXMLObject());
		XmlNode storageNode =new XmlNode("TDDT",xmlStorage);
		storageNode.setRoot(true);
		parser.serialize(getDefaultRelativeFilePath(), storageNode);
		

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
