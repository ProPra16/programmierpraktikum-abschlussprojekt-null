package gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Exercise;

import vk.core.api.*;


public class TestController implements Initializable{
	Exercise exercise;
	String value;
	
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
	}
	
	public void setExercise(Exercise exercise){
		this.exercise = exercise;
		value = exercise.getTests().get(0).getContent();
		TestTextField.setText(value);
	}
	

}