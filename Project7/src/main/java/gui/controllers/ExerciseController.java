package gui.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.views.AlertBox;
import gui.views.JavaCodeArea;
import models.Exercise;
import models.Test;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import vk.core.api.*;


public class ExerciseController implements Initializable{
	Exercise exercise;
	String value;
	Test test;
	JavaStringCompiler compiler;
	Pane mainSection;
	
	
	@FXML
	JavaCodeArea sourceTextField;
	@FXML
	Button cancelButton;
	@FXML
	Button confirmButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void cancelAction(){
		
		exercise.getClasses().get(0).setContent(sourceTextField.getText());
		CompilationUnit compilatedTest = new CompilationUnit(test.getName(), exercise.getTests().get(0).getContent(), true);
		CompilationUnit compilatedData = new CompilationUnit(exercise.getClasses().get(0).getName(), exercise.getClasses().get(0).getContent(), false);
		
		compiler = CompilerFactory.getCompiler(compilatedTest,compilatedData);
				
		compiler.compileAndRunTests();
		CompilerResult comResult = compiler.getCompilerResult();
		
		//Checks for Syntax-Errors
		if(!comResult.hasCompileErrors()){
			
			AlertBox alertBox = new AlertBox("Exit", "Are you sure you want to go back to edit the tests?", 2);
			alertBox.buttonList[0].setText("Cancel");
			alertBox.buttonList[0].setOnAction(e-> alertBox.end());
			alertBox.buttonList[1].setText("Confirm");
			alertBox.buttonList[1].setOnAction(e-> {
				// TODO switch scene back to TestController
				
				try{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
					TestController testController = new TestController();
					loader.setController(testController);
					Parent testView = loader.load();
					AnchorPane.setTopAnchor(testView, 0.0);
					AnchorPane.setLeftAnchor(testView, 0.0);
					AnchorPane.setRightAnchor(testView, 0.0);
					AnchorPane.setBottomAnchor(testView, 0.0);
					testController.setExercise(exercise);
					testController.setMainSection(mainSection);
					mainSection.getChildren().clear();
					mainSection.getChildren().add(testView);
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				
				
				
				alertBox.end();
			});
			alertBox.show();
		}
		else{
			AlertBox alertBox = new AlertBox("Error", "Die folgenden Syntax-Fehler sind aufgetreten:", 1);
			alertBox.setComResult(comResult);
			alertBox.setCompilatedData(compilatedData);
			alertBox.buttonList[0].setText("Confirm");
			alertBox.buttonList[0].setOnAction(e-> alertBox.end());
			alertBox.show();
		}
	}
	
	@FXML
	public void confirmAction(){	
	
		exercise.getClasses().get(0).setContent(sourceTextField.getText());
		CompilationUnit compilatedTest = new CompilationUnit(test.getName(), exercise.getTests().get(0).getContent(), true);
		CompilationUnit compilatedData = new CompilationUnit(exercise.getClasses().get(0).getName(), exercise.getClasses().get(0).getContent(), false);
		
		compiler = CompilerFactory.getCompiler(compilatedTest,compilatedData);
				
		compiler.compileAndRunTests();
		CompilerResult comResult = compiler.getCompilerResult();
		TestResult testResult = compiler.getTestResult();
		
		//Checks for Syntax-Errors
		if(comResult.hasCompileErrors()){
			AlertBox alertBox = new AlertBox("Error", "Die folgenden Syntax-Fehler sind aufgetreten:", 1);
			alertBox.setComResult(comResult);
			alertBox.setCompilatedData(compilatedData);
			alertBox.buttonList[0].setText("Confirm");
			alertBox.buttonList[0].setOnAction(e-> alertBox.end());
			alertBox.show();

		}
		else{
			//failed tests 
			if(testResult.getNumberOfFailedTests() != 0){
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("TDDT");
				alert.setContentText("Tests failed!");
				alert.showAndWait();
				
			}
			// none failed tests
			else{
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("TDDT");
				alert.setContentText("No failed Tests!");
				alert.showAndWait();
				//TODO save changed code
			}
		}
	}
	
	public void setExercise(Exercise exercise){
		this.exercise = exercise;
		test = exercise.getTests().get(0);
		value = exercise.getClasses().get(0).getContent();
		sourceTextField.replaceText(value);
	}
	
	public void setMainSection(Pane mainSection){
		this.mainSection = mainSection;
	}
}
