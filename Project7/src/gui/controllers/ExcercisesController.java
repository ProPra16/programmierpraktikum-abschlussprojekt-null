package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.*;

public class ExcercisesController implements Initializable {
	
	@FXML
	FlowPane excercisesFlowPane;
	Catalog catalog;
	
	public ExcercisesController() {
		Excercise excercise1 = new Excercise(
				"Roman Numbers", 
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et",
				new String[] { "class A { public static void main(String[] args) {} }" },
				new String[] { "Test1", "Test2" },
				new Config(true, false)
			);
		
		Excercise excercise2 = new Excercise(
				"Roman Numbers Extended", 
				"Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
				new String[] { "class A { public static void main(String[] args) {} }" },
				new String[] { "Test" },
				new Config(true, false)
			);
		
		Excercise excercise3 = new Excercise(
				"Roman Numbers Heavy Hard", 
				"Never will be solved...",
				new String[] { "class A { public static void main(String[] args) {} }" },
				new String[] { "Test" },
				new Config(true, false)
			);
		
		catalog = new Catalog("Test Catalog", excercise1, excercise2, excercise3);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(Excercise excercise : catalog.excercises) {
			String excerciseName = (excercise.name.length() > 50) ? excercise.name.substring(0, 49).trim() + "..." : excercise.name.trim();
			Label header = new Label(excerciseName);
			header.getStyleClass().add("excercise-header");
			
			String excerciseDescription = (excercise.description.length() > 100) ? excercise.description.substring(0, 99).trim() + "..." : excercise.description.trim();
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
