package gui.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;

public class AlertBox {
	private String title;
	private String message;
	public VBox layout;
	public Button[] buttonList;
	Stage alert;
	private CompilerResult comResult;
	private CompilationUnit compilatedData;
	
	public AlertBox(String title, String message, int buttons){
		this.title = title;
		this.message = message;
		buttonList = new Button[buttons];
		for(int i = 0; i < buttons; i++){
			buttonList[i] = new Button();
		}
	}
	
	/**
	 * Start displaying the AlertBox
	 */
	public void show(){
		alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle(title);
		alert.setMinWidth(400);
		alert.setMinHeight(250);
		
		//Filters if there is an error or not
		Label label;
		if(comResult == null)
			label = new Label(message);
		else
			label = new Label(message + '\n' + createErrorText());
		
		layout = new VBox(10);
		layout.getChildren().addAll(label);
		layout.setAlignment(Pos.CENTER);
		
		for(int j = 0; j < buttonList.length; j++){
			layout.getChildren().add(buttonList[j]);
		}
		
		Scene scene = new Scene(layout);
		alert.setScene(scene);
		alert.showAndWait();
	
		
	}
	/**
	 * Closes the stage
	 */
	public void end(){
		alert.close();
	}
	
	
	/**
	 * Creates a errorMessage for Syntax-errors
	 * @return String
	 */
	public String createErrorText(){
		String errorMessage = "";
		for(CompileError error : comResult.getCompilerErrorsForCompilationUnit(compilatedData)){
			errorMessage += (error.getMessage() + '\n' + "Line: " + error.getLineNumber() + '\n');
		}
		return errorMessage;
	}
	
	
	
	public void setComResult(CompilerResult result){
		this.comResult = result;
	}
	
	public void setCompilatedData(CompilationUnit compilatedData){
		this.compilatedData = compilatedData;
	}

}
