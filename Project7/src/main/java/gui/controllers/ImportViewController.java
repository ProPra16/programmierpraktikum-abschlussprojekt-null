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
import services.StorageService;
import xmlParser.ParserException;

public class ImportViewController implements Initializable {
	
	@FXML 
	Node dropArea;
	
	MenuViewController menuController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * Drag over action
	 * 
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
	 * 
	 * @param event
	 */
	@FXML
	public void dragExited(DragEvent event) {
		// Remove all duplicates  
		while(dropArea.getStyleClass().contains("active")) {
			dropArea.getStyleClass().remove("active");
		}
	}
	
	/**
	 * Drag dropped action
	 * 
	 * @param event
	 */
	@FXML
	public void dragDropped(DragEvent event) {		
    	try {
			loadConfig(event.getDragboard().getFiles().get(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
       
		menuController.selectExerciseOverview();
    	event.setDropCompleted(true);
        event.consume();
	}
	
	/**
	 * Sets the {@link MenuViewController} - needed for select exercise view
	 * 
	 * @param menuController
	 */
	public void setMenuController(MenuViewController menuController) {
		this.menuController = menuController;
	}
	
	/**
	 * Imports config file to workspace and (re)loads model 
	 * 
	 * @param file
	 */
	private void loadConfig(File file) throws Exception{
		
			StorageService.getInstance().importModel(file.getAbsolutePath(),false);
			// TODO override old config
		
	}
	
	

}
