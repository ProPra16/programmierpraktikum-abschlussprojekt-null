package gui.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import gui.views.statistics.TrackingStatistic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.TrackingSession;
import services.TrackingService;

public class StatisticsViewController implements Initializable {
	
	@FXML
	VBox statisticsContainer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * Updates the shown statistics
	 */
	public void updateStatistics() {
		Collection<TrackingSession> trackingResults = TrackingService.shared().getTrackingResults();
		
		statisticsContainer.getChildren().clear();
		 
		if(trackingResults.size() == 0 || !anyCompleteSession()) {
			Label errorLabel = new Label("Currently, there are no statistics available");
			errorLabel.getStyleClass().add("error");
			statisticsContainer.getChildren().add(errorLabel);
		} else {
			for(TrackingSession trackingSession : trackingResults) {
				// Fulfill at least one cycle
				if(trackingSession.getData().size() >= 3) {
					statisticsContainer.getChildren().add(new TrackingStatistic(trackingSession));
				}
			}
		}
	}
	
	/**
	 * @return true, if any session has fulfilled at least one cycle
	 */
	private boolean anyCompleteSession() {
		Collection<TrackingSession> trackingResults = TrackingService.shared().getTrackingResults();
		
		for(TrackingSession trackingSession : trackingResults) {
			if(trackingSession.getData().size() >= 3) {
				return true;
			}
		}
		return false;
	}

}
