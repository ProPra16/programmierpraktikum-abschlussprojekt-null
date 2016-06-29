package gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TestController implements Initializable{
	static Stage testStage;
	
	@FXML
	TextArea TestTextField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * Initializes controller with stage 
	 * @param stage
	 */
	public static void initWithStage(Stage stage) {
		testStage = stage;
	}
	
	@FXML
	public void cancelAction(){
		System.out.println("Hello");
	}
	
	@FXML
	public void confirmTest(){
		System.out.println("World");
	}
	

}