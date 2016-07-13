package services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fxmisc.richtext.CodeArea;

import gui.controllers.cycle.RedViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.Exercise;

public class BabystepsService {

	Label timeLabel;
	Exercise exercise;
	boolean finishedTask;
	String cachedContent;
	CodeArea sourceCodeArea;
	
	/**
	 * Counstructs babysteps service with time label
	 * 
	 * @param counterLabel
	 */
	public BabystepsService(Exercise exercise, Label timeLabel, String cachedContent, CodeArea codeArea) {
		this.exercise = exercise;
		this.timeLabel = timeLabel;
		finishedTask = false;
		this.cachedContent = cachedContent;
		this.sourceCodeArea = codeArea;
	}	
	
	/**
	 * Starts the timer
	 */
	public void start() {
		Thread t = new Thread(() -> {
			boolean running = true;
			long maxTime = exercise.getConfig().getTimeLimit()*1000;
			
			Date dateStart = new Date();
			
			while(running) {
				if(finishedTask == true) {
					running = false;
					break;
				}
				Date dateNext = new Date();
				long currentTime = (maxTime - (dateNext.getTime() - dateStart.getTime()));
				Date finalTime = new Date(currentTime);
				
				DateFormat formatter = new SimpleDateFormat("mm:ss:SS");
				String dateFormatted = formatter.format(finalTime);				
				
				if(currentTime <= 0) {
					Platform.runLater(() -> {
						sourceCodeArea.replaceText(cachedContent);
						
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Babysteps");
						alert.setHeaderText("Out of time");
						alert.setContentText("You did not finish in time!");
						alert.showAndWait();
						
						start();
				    });
					running = false;
				}
				
				Platform.runLater(() -> {
					timeLabel.setText(dateFormatted);
			    });
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t.start();
	}
	
	/**
	 * Stops the timer
	 */
	public void stop() {
		finishedTask = true;
	}
	
}
