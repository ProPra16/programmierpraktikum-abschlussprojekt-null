package gui.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import gui.views.AlertBox;
import gui.views.JavaCodeArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.Exercise;
import services.CompileService;
import vk.core.api.TestResult;


public class TestController implements Initializable{
	Pane mainSection;
	CompileService compileService;
	
	@FXML
	AnchorPane rootPane;
	@FXML
	JavaCodeArea sourceTextField;
	@FXML
	Button cancelButton;
	@FXML
	Button confirmButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancelButton.setVisible(false);
		rootPane.getStyleClass().add("red");
		/*sourceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			
		});*/
	}
	
	@FXML
	public void cancelAction(){
	
	}
	
	@FXML
	public void confirmAction() {	
	
		compileService.getExercise().getTests().get(0).setContent(sourceTextField.getText());
				
		compileService.compileAndRunTests();
		
		if(!compileService.isValid()) {
			sourceTextField.markErrors(compileService.getCompileErrors()); // filtered list!
			
			if(!compileService.getCompilerResult().hasCompileErrors()) { // unfiltered!
				TestResult testResult = compileService.getTestResult();
				
				if(testResult.getNumberOfFailedTests() == 0) {
					AlertBox alertBox = new AlertBox("Error", "Kein Test ist fehlgeschlagen! Bitte einen Test schreiben der fehlschlägt!", 1);
					alertBox.buttonList[0].setText("Confirm");
					alertBox.buttonList[0].setOnAction(e-> alertBox.end());
					alertBox.show();
					
				} else if(testResult.getNumberOfFailedTests() != 1) {
					AlertBox alertBox = new AlertBox("Error", "Zu viele Tests sind fehlgeschlagen! Bitte nur genau EINEN Test schreiben der fehlschlagen soll!", 1);
					alertBox.buttonList[0].setText("Confirm");
					alertBox.buttonList[0].setOnAction(e-> alertBox.end());
					alertBox.show();
				} 
			} else if(compileService.missingAssertEquals()) {
				AlertBox alertBox = new AlertBox("Error", "Es fehlt assertEquals!", 1);
				alertBox.buttonList[0].setText("Confirm");
				alertBox.buttonList[0].setOnAction(e-> alertBox.end());
				alertBox.show();
			}
		}
		else {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
				ExerciseController exerciseController = new ExerciseController();
				loader.setController(exerciseController);
				Parent exerciseView = loader.load();
				exerciseController.setCompileService(compileService);
				AnchorPane.setTopAnchor(exerciseView, 0.0);
				AnchorPane.setLeftAnchor(exerciseView, 0.0);
				AnchorPane.setRightAnchor(exerciseView, 0.0);
				AnchorPane.setBottomAnchor(exerciseView, 0.0);
				exerciseController.setMainSection(mainSection);
				mainSection.getChildren().clear();
				mainSection.getChildren().add(exerciseView);
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Sets the exercise initially
	 * @param exercise
	 */
	public void setExercise(Exercise exercise){
		compileService = new CompileService(exercise);
		compileService.setMode(CompileService.Mode.RED);
		sourceTextField.replaceText(exercise.getTests().get(0).getContent());
	}
	
	/**
	 * Sets the compile service
	 * @param compileService
	 */
	public void setCompileService(CompileService compileService) {
		this.compileService = compileService;
		compileService.setMode(CompileService.Mode.RED);
		sourceTextField.replaceText(compileService.getExercise().getTests().get(0).getContent());
	}
	
	/**
	 * Sets the main section
	 * @param mainSection
	 */
	public void setMainSection(Pane mainSection){
		this.mainSection = mainSection;
	}
}