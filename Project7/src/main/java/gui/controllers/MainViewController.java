package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import xmlModelParser.ModelStorageController;
import xmlModelParser.ParserException;

public class MainViewController implements Initializable {

	@FXML
	AnchorPane sideSection;
	@FXML
	AnchorPane mainSection;
	
	MenuViewController menuController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Load workspace
		try {
			ModelStorageController.getInstance().loadModel();
		} catch (SAXException | IOException | ParserConfigurationException | ParserException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
				
		try {
			// Load menu in sidebar
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/MenuView.fxml"));
			Parent menuView = loader.load();
			setAllAnchorsNull(menuView);
			sideSection.getChildren().add(menuView);
			menuController = loader.getController();
			menuController.setMainSection(mainSection);	
			
			if(ModelStorageController.getInstance().getCatalog().getExercises().size() == 0) {
				// Load import view in main section
				loader = new FXMLLoader(getClass().getResource("/gui/views/ImportView.fxml"));
				Parent importView = loader.load();
				setAllAnchorsNull(importView);
				ImportViewController importController = loader.getController();
				importController.setMenuController(menuController);
				mainSection.getChildren().add(importView);
			} else {
				// Load exercise overview in main section
				loader = new FXMLLoader(getClass().getResource("/gui/views/ExercisesView.fxml"));
				Parent exerciseView = loader.load();
				mainSection.getChildren().clear();
				mainSection.getChildren().add(exerciseView);
				setAllAnchorsNull(exerciseView);
				ExercisesViewController exercisesController = loader.getController();
				exercisesController.setMenuController(menuController);
				menuController.setExerciseView(exerciseView);
			}
			
			
		} catch (IOException exception) {
			// TODO Handle exception
			exception.printStackTrace();
		}
	}

	/**
	 * Sets all anchors null
	 * 
	 * @param node
	 */
	public static void setAllAnchorsNull(Node node) {
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);
	}
}
