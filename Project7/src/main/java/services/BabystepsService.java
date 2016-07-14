package services;


import java.util.Date;

import org.fxmisc.richtext.CodeArea;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
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
	public BabystepsService(Exercise exercise, Label timeLabel, CodeArea codeArea) {
		this.exercise = exercise;
		this.timeLabel = timeLabel;
		finishedTask = false;
		this.cachedContent = new String(codeArea.getText());
		this.sourceCodeArea = codeArea;
	}
	
	/**
	 * @param timeLabel the timeLabel to set
	 */
	public void setTimeLabel(Label timeLabel) {
		this.timeLabel = timeLabel;
	}

	/**
	 * @param sourceCodeArea the sourceCodeArea to set
	 */
	public void setCodeArea(CodeArea sourceCodeArea) {
		this.sourceCodeArea = sourceCodeArea;
		this.cachedContent = new String(sourceCodeArea.getText());
	}

	/**
	 * Starts the timer
	 */
	public void start() {
		finishedTask = false;
		
		Thread t = new Thread(() -> {
			boolean running = true;
			long maxTime = exercise.getConfig().getTimeLimit();
			
			Date dateStart = new Date();
			
			while(running) {
				if(finishedTask == true) {
					running = false;
					return;
				}
				Date dateNext = new Date();
				long currentTime = (maxTime - (dateNext.getTime() - dateStart.getTime()));
				
				long formatTime = currentTime / 1000;
				String dateFormatted = String.format("%02d:%02d:%02d", formatTime/3600, (formatTime % 3600) / 60, (formatTime % 60));
				
				
				if(currentTime <= 0) {
					Platform.runLater(() -> {
						sourceCodeArea.replaceText(cachedContent);
						
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Babysteps");
						alert.setHeaderText("Out of time");
						alert.setContentText("Unfortunately, you did not finish in time :(\nThe code you've written in this coding stage will now be reset!");
						alert.initModality(Modality.WINDOW_MODAL);
						alert.showAndWait();
					
						start();
				    });
					running = false;
				} else {
					Platform.runLater(() -> {
						timeLabel.setText(dateFormatted);
				    });
				}
			
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Error while measuring time");
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Stops the timer
	 */
	public void stop() {
		finishedTask = true;
	}
	
}
