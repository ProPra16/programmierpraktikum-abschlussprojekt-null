package gui.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.views.AlertBox;
import gui.views.JavaCodeArea;
import services.CompileService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


public class ExerciseController implements Initializable{
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
		rootPane.getStyleClass().add("green");
	}
	
	@FXML
	public void cancelAction(){
		compileService.getExercise().getClasses().get(0).setContent(sourceTextField.getText());
		compileService.compileAndRunTests();
		
		//Checks for Syntax-Errors
		if(!compileService.isValid()){
			
			sourceTextField.markErrors(compileService.getCompileErrors());
			/*if(compileService.getTestResult().getNumberOfFailedTests() != 0) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("TDDT");
				alert.setContentText("Tests failed!");
				alert.showAndWait();
			}*/
		}
		
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
				testController.setCompileService(compileService);
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
	
	@FXML
	public void confirmAction(){	
		compileService.getExercise().getClasses().get(0).setContent(sourceTextField.getText());
		compileService.compileAndRunTests();
		
		if(!compileService.isValid()){
			sourceTextField.markErrors(compileService.getCompileErrors());
			
			if(compileService.getTestResult().getNumberOfFailedTests() != 0) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("TDDT");
				alert.setContentText("Tests failed!");
				alert.showAndWait();
			}	
		}
		else{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("TDDT");
			alert.setContentText("No failed Tests!");
			alert.showAndWait();
			//TODO save changed code
		}
	}
	
	/**
	 * Sets the compile service
	 * @param compileService
	 */
	public void setCompileService(CompileService compileService) {
		this.compileService = compileService;
		compileService.setMode(CompileService.Mode.GREEN);
		sourceTextField.replaceText(compileService.getExercise().getClasses().get(0).getContent());
	}
	
	/**
	 * Sets the main section
	 * @param mainSection
	 */
	public void setMainSection(Pane mainSection){
		this.mainSection = mainSection;
	}
}
