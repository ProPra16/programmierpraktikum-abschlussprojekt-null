package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import xmlModelParser.ModelStorageController;
import xmlModelParser.ParserException;

public class ImportViewController implements Initializable {
	
	@FXML 
	Node dropArea;
	
	MainViewController mainController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * Drag over action
	 * @param event
	 */
	@FXML
	public void dragOver(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		if(dragboard.hasFiles() && dragboard.getFiles().size() == 1 && 
				dragboard.getFiles().get(0).getName().endsWith(".xml")) {
			event.acceptTransferModes(TransferMode.COPY);
			dropArea.getStyleClass().add("active");
		}
		event.consume();
	}
	
	/**
	 * Drag exited action
	 * @param event
	 */
	@FXML
	public void dragExited(DragEvent event) {
		if(dropArea.getStyleClass().contains("active"))
			dropArea.getStyleClass().remove("active");
	}
	
	/**
	 * Drag dropped action
	 * @param event
	 */
	@FXML
	public void dragDropped(DragEvent event) {		
    	loadConfig(event.getDragboard().getFiles().get(0));
		mainController.showExercisesView();
    	event.setDropCompleted(true);
        event.consume();
	}
	
	public void setMainController(MainViewController mainController) {
		this.mainController = mainController;
		/* TODO not possible, because blank file is created automatically... 
		try {
			// Try to load last exercise
			ModelStorageController.getInstance().loadModel();
			// Last session is restored, load exercise view
			mainController.showExercisesView();
		} catch (SAXException | IOException | ParserConfigurationException | ParserException e) {
			// Do nothing - import view is already there
		}
		*/
	}
	
	private void loadConfig(File file) {
		try {
			ModelStorageController.getInstance().importModel(file.getAbsolutePath());
		} catch (SAXException | IOException | ParserConfigurationException | ParserException e) {
			// TODO Handle Error appropriately
			e.printStackTrace();
		}
	}
	
	

}
