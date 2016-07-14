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

/**
 * Manages all aspects of Storage as a single instance (Singleton)
 */
public class StorageService extends Observable implements Observer {
	/**
	 * Global instance (Singleton)
	 */
	private static StorageService instance;
	/**
	 * The default Path to temporarily store files
	 */
	public static final String defaultPath = ".tddt/";
	/**
	 * The default Filename to temporarily store files
	 */
	public static final String defaultFileName = "Catalog.xml";
	/**
	 * The globally stored {@link ExerciseCatalog}
	 */
	private ExerciseCatalog exerciseCatalog = new ExerciseCatalog();
	
	/**
	 * The globally stored {@link TrackingSessionCatalog}
	 */
	private TrackingSessionCatalog tSessionCatalog = new TrackingSessionCatalog();

	private StorageService() {
	}

	/**
	 * Returns or creates the singleton instance
	 * @return Globally valid StorageService instance
	 */
	public synchronized static StorageService getInstance() {

		if (instance == null) {
			instance = new StorageService();
			instance.exerciseCatalog.addObserver(instance);

			createStorage();
		}

		return instance;
	}

	/**
	 * Returns the default relative file path
	 * @return
	 */
	public static String getDefaultRelativeFilePath() {
		return defaultPath + defaultFileName;
	}

	/**
	 * Imports a new Model globally
	 * @param path Path to the XML-file 
	 * @param acceptEmptyExerciseCatalog Set weather an empty catalog is acceptable
	 * @throws Exception
	 */
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

	/**
	 * Loads the default XML-File globally
	 * @throws Exception
	 */
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

	/**
	 * Returns the global {@link ExerciseCatalog}
	 * @return {@link ExerciseCatalog}
	 */
	public ExerciseCatalog getExerciseCatalog() {
		return this.exerciseCatalog;
	}

	/**
	 * Returns the global {@link TrackinSessionCatalog}
	 * @return {@link TrackingSessionCatalog}
	 */
	public TrackingSessionCatalog gettSessionCatalog() {
		return tSessionCatalog;
	}

	/**
	 * Creates a Path for the Application's default model
	 * 
	 */
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
