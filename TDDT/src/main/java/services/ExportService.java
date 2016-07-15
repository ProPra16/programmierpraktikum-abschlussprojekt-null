package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import models.Class;
import models.Exercise;
import models.Test;

public class ExportService {
	
	/**
	 * Shows dialog for exporting created files 
	 * 
	 * @param exercise
	 */
	public static void export(Exercise exercise, Window window) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose an export directory");
		File selectedDirectory = directoryChooser.showDialog(window);
		
		if(selectedDirectory != null) {
			try {	
				// Save test files
				for(Test test : exercise.getTests()) {
					Path testPath = Paths.get(selectedDirectory.getAbsolutePath() + "/" + test.getName() + ".java");
						Files.write(testPath, test.getContent().getBytes());
				}
				// Save class files
				for(Class exerciseClass : exercise.getClasses()) {
					Path classPath = Paths.get(selectedDirectory.getAbsolutePath() + "/" + exerciseClass.getName() + ".java");
						Files.write(classPath, exerciseClass.getContent().getBytes());
				}
			} catch (IOException e) {
				// Show alert
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("TDDT");
				alert.setHeaderText("Error while saving your files");
				alert.setContentText("No chance to save your files there, please try again and make sure to grant all necessary permissions!");
				alert.showAndWait();
				e.printStackTrace();
			}
		}
	}
}
