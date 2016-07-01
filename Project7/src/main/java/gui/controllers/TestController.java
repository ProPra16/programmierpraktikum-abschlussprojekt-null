package gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.*;

import vk.core.api.*;


public class TestController implements Initializable{
	Exercise exercise;
	String value;
	Test test;
	JavaStringCompiler compiler;

	
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
		System.out.println("Hello");
	}
	
	@FXML
	public void confirmAction(){	
	
		System.out.println(test.getName());
		CompilationUnit compilatedData = new CompilationUnit(test.getName(), sourceTextField.getText(), true);
		// TODO Save changes in the TextArea
		
		compiler = CompilerFactory.getCompiler(compilatedData);
				
		compiler.compileAndRunTests();
		CompilerResult comResult = compiler.getCompilerResult();
		TestResult tesResult = compiler.getTestResult();
		
		if(comResult.hasCompileErrors()){
			// TODO Alertbox
			for(CompileError error : comResult.getCompilerErrorsForCompilationUnit(compilatedData))
				System.out.println(error.getMessage());
		}
		else{
			if(tesResult.getNumberOfFailedTests() == 1){
				// TODO switch scene
				System.out.println("Test1");
			}
			else{
				// TODO Alertbox too many new or wrong tests
				System.out.println("Test2");
			}
		}
		
		
	}
	
	public void setExercise(Exercise exercise){
		this.exercise = exercise;
		test = exercise.getTests().get(0);
		value = test.getContent();
		sourceTextField.setText(value);
	}
	

}