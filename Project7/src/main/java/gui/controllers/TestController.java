package gui.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.views.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.*;
import models.Class;

import vk.core.api.*;


public class TestController implements Initializable{
	Exercise exercise;
	String value;
	Test test;
	Class classCode;
	JavaStringCompiler compiler;
	Pane mainSection;
	

	
	@FXML
	TextArea sourceTextField;
	@FXML
	Button cancelButton;
	@FXML
	Button confirmButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void cancelAction(){
		AlertBox alertBox = new AlertBox("Exit", "Are you sure you want to go back to the Menu?", 2);
		alertBox.buttonList[0].setText("Cancel");
		alertBox.buttonList[0].setOnAction(e-> alertBox.end());
		alertBox.buttonList[1].setText("Confirm");
		alertBox.buttonList[1].setOnAction(e-> {
			// TODO switch scene back to the menu!!!
		alertBox.end();
		});
		alertBox.show();
		
	}
	
	@FXML
	public void confirmAction(){	
	
		System.out.println(test.getName());
		test.setContent(sourceTextField.getText());
		CompilationUnit compilatedData = new CompilationUnit(test.getName(), test.getContent(), true);
		CompilationUnit compilatedClass = new CompilationUnit(classCode.getName(), classCode.getContent(), false);
		
		// TODO Save changes in the TextArea
		
		compiler = CompilerFactory.getCompiler(compilatedData, compilatedClass);
				
		compiler.compileAndRunTests();
		CompilerResult comResult = compiler.getCompilerResult();
		TestResult testResult = compiler.getTestResult();
		
		//Checks for Syntax-Errors
		if(comResult.hasCompileErrors() && containsError(comResult, compilatedData)){
			
			AlertBox alertBox = new AlertBox("Error", "Die folgenden Syntax-Fehler sind aufgetreten:", 1);
			alertBox.setComResult(comResult);
			alertBox.setCompilatedData(compilatedData);
			alertBox.buttonList[0].setText("Confirm");
			alertBox.buttonList[0].setOnAction(e-> alertBox.end());
			alertBox.show();

		}
		else{
			//One failed test which is Okay
			if(comResult.hasCompileErrors() && !containsError(comResult, compilatedData) || testResult.getNumberOfFailedTests() == 1){
				// TODO switch scene
				
				try{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
					ExerciseController exerciseController = new ExerciseController();
					loader.setController(exerciseController);
					Parent exerciseView = loader.load();
					exerciseController.setExercise(exercise);
					AnchorPane.setTopAnchor(exerciseView, 0.0);
					AnchorPane.setLeftAnchor(exerciseView, 0.0);
					AnchorPane.setRightAnchor(exerciseView, 0.0);
					AnchorPane.setBottomAnchor(exerciseView, 0.0);
					exerciseController.setMainSection(mainSection);
					mainSection.getChildren().clear();
					mainSection.getChildren().add(exerciseView);
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				
				System.out.println("Test1");
			}
			//Multiple or none failed tests
			else{
				if(testResult.getNumberOfFailedTests() == 0){
					AlertBox alertBox = new AlertBox("Error", "Kein Test ist fehlgeschlagen! Bitte einen Test schreiben der fehlschl�gt!", 1);
					alertBox.buttonList[0].setText("Confirm");
					alertBox.buttonList[0].setOnAction(e-> alertBox.end());
					alertBox.show();
					
				}else{
					AlertBox alertBox = new AlertBox("Error", "Zu viele Tests sind fehlgeschlagen! Bitte nur genau EINEN Test schreiben der fehlschlagen soll!", 1);
					alertBox.buttonList[0].setText("Confirm");
					alertBox.buttonList[0].setOnAction(e-> alertBox.end());
					alertBox.show();
				}
			}
		}
		
	}
	
	public void setExercise(Exercise exercise){
		this.exercise = exercise;
		test = exercise.getTests().get(0);
		classCode = exercise.getClasses().get(0);
		value = test.getContent();
		sourceTextField.setText(value);
	}
	
	public void setMainSection(Pane mainSection){
		this.mainSection = mainSection;
	}
	
	private boolean containsError(CompilerResult comResult, CompilationUnit compilatedData) {
		// TODO check if every error is a method error
		for(CompileError error : comResult.getCompilerErrorsForCompilationUnit(compilatedData)) {
			if(error.getMessage().contains("method") && error.getMessage().contains(classCode.getName())) {
				return false;
			}
		}
		return true;
	}
}