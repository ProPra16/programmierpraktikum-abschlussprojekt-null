package gui.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	private String title;
	private String message;
	public VBox layout;
	public Button[] buttonList;
	
	public AlertBox(String title, String message, int buttons){
		this.title = title;
		this.message = message;
		buttonList = new Button[buttons];
		for(int i = 0; i < buttons; i++){
			buttonList[i] = new Button();
		}
	}
	public void show(){
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle(title);
		alert.setMinWidth(250);
		alert.setMinHeight(150);
		
		Label label = new Label(message);
		
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
}
