package services;

import java.util.Date;

import org.fxmisc.richtext.CodeArea;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
			long maxTime = exercise.getConfig().getTimeLimit();
			
			Date dateStart = new Date();
			
			while(running) {
				if(finishedTask == true) {
					running = false;
					break;
				}
				Date dateNext = new Date();
				long currentTime = (maxTime - (dateNext.getTime() - dateStart.getTime())/1000);
				
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
					timeLabel.setText(Long.toString(currentTime));
			    });
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Error while measuring time");
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
