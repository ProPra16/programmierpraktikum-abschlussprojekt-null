package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Catalog;
import models.Exercise;
import xmlModelParser.Parser;


public class ExcercisesController implements Initializable {
	
	@FXML
	FlowPane excercisesFlowPane;
	Catalog catalog;
	
	public ExcercisesController() {
		
		
		//TODO Discuss how to load and unload XML with the UI; e.g. Storage class
		//TODO TAKE THIS OUT BEFORE RELEASE THE FOLLOWING LINES ARE JUST FOR TETING PURPOSES
		Parser parser = new Parser();
		try {
			
			catalog =parser.parse("default.xml");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Handle Error appropriately
			catalog= new Catalog();
			System.out.println("Shit!!!");
			
			e.printStackTrace();
		}
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for(Exercise excercise : catalog.getExercises()) {
			String excerciseName = (excercise.getName().length() > 50) ? excercise.getName().substring(0, 49).trim() + "..." : excercise.getName().trim();
			Label header = new Label(excerciseName);
			header.getStyleClass().add("excercise-header");
			
			String excerciseDescription = (excercise.getDescription().length() > 100) ? excercise.getDescription().substring(0, 99).trim() + "..." : excercise.getDescription().trim();
			Text description = new Text(excerciseDescription);
			description.getStyleClass().add("excercise-description");
			
			GridPane tile = new GridPane();
			tile.getStyleClass().add("excercise-tile");
			tile.add(header, 0, 0);
			tile.add(description, 0, 1);
			excercisesFlowPane.getChildren().add(tile);
		}
	}
	
}
