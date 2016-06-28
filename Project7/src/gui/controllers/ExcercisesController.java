package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import models.*;

public class ExcercisesController implements Initializable {
	
	@FXML
	GridPane excercisesPane;
	Catalog catalog;
	Stage stage;
	
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
		
	}
	
	public void initWithStage(Stage stage) {
		this.stage = stage;
		stage.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	arrangeExcercises();
		    }
		});
	}
	
	private void arrangeExcercises() {
		int columnsNumber = 1;
		double width = stage.getWidth();
		if(width > 600)
			columnsNumber = 2;
		if(width > 900)
			columnsNumber = 3;
		
		excercisesPane.getChildren().clear();
		excercisesPane.getColumnConstraints().clear();
		excercisesPane.getRowConstraints().clear();
		
		for(int i = 0; i < catalog.excercises.size(); i++) {
			Excercise excercise = catalog.excercises.get(i);
			
			String excerciseName = (excercise.name.length() > 50) ? excercise.name.substring(0, 49).trim() + "..." : excercise.name.trim();
			Label header = new Label(excerciseName);
			header.getStyleClass().add("excercise-header");
			
			String excerciseDescription = (excercise.description.length() > 100) ? excercise.description.substring(0, 99).trim() + "..." : excercise.description.trim();
			Text description = new Text(excerciseDescription);
			description.getStyleClass().add("excercise-description");
			TextFlow descriptionFlow = new TextFlow(description);
			
			GridPane tile = new GridPane();
			tile.getStyleClass().add("excercise-tile");
			tile.add(header, 0, 0);
			tile.add(descriptionFlow, 0, 1);
			tile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent e) {
		            selectExcercise(excercise);
		          }
		        });
			excercisesPane.add(tile, i % columnsNumber, i / columnsNumber);
			// TODO: tile fill full column excercisesPane.setFillWidth(tile, true);
		}
		/* TODO: All columns have to have the same width
		for(ColumnConstraints column : excercisesPane.getColumnConstraints()) {
			column.setPercentWidth(100.0 / columnsNumber);
		}
		*/
	}
	
	private void selectExcercise(Excercise excercise) {
		// TODO: Implement select excercise 
	}
}
