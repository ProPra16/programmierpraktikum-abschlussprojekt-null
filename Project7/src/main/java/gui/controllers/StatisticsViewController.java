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
import models.TrackingSessionCatalog;
import services.StorageService;

public class StatisticsViewController implements Initializable {
	
	@FXML
	VBox statisticsContainer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateStatistics();
	}
	
	/**
	 * Updates the shown statistics
	 */
	public void updateStatistics() {
		Collection<TrackingSession> trackingResults = StorageService.getInstance().gettSessionCatalog().getTrackingResults();
		
		statisticsContainer.getChildren().clear();
		
		// Fulfill at least one cycle 
		if(trackingResults.size() <= 3) {
			Label errorLabel = new Label("Currently, there are no statistics available");
			errorLabel.getStyleClass().add("error");
			statisticsContainer.getChildren().add(errorLabel);
		} else {
			for(TrackingSession trackingSession : trackingResults) {
				statisticsContainer.getChildren().add(new TrackingStatistic(trackingSession));
			}
		}
	}

}
