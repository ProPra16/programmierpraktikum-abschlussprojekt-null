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
	TextArea TestTextField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void cancelAction(){
		System.out.println("Hello");
	}
	
	@FXML
	public void confirmTest(){	
		
		System.out.println("World");
		CompilationUnit compilatedData = new CompilationUnit(test.getName(), test.getContent(), true);
		compiler = CompilerFactory.getCompiler(compilatedData);
		
		compiler.compileAndRunTests();
		CompilerResult comResult = compiler.getCompilerResult();
		TestResult tesResult = compiler.getTestResult();
		
		System.out.println(comResult.hasCompileErrors());
		
	}
	
	public void setExercise(Exercise exercise){
		this.exercise = exercise;
		test = exercise.getTests().get(0);
		value = test.getContent();
		TestTextField.setText(value);
	}
	

}