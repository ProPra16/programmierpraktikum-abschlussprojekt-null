package gui.controllers.cycle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import gui.controllers.MainViewController;
import gui.views.cycle.JavaCodeArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.TrackingData;
import models.TrackingSession;
import services.BabystepsService;
import services.CompileService;
import services.ExportService;

public class BlueViewController implements Initializable {
	Pane mainSection;
	CompileService compileService;
	BabystepsService babystepsService;
	TrackingSession trackingSession;
	TrackingData trackingData;

	@FXML
	AnchorPane rootPane;
	@FXML
	JavaCodeArea codeArea;
	@FXML
	Node backButton;
	@FXML
	Node shareButton;
	@FXML
	Node confirmButton;
	@FXML
	Label timeLabel;
	@FXML
	VBox cycleInformationBox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Disable back button - don't edit red 
		backButton.setVisible(false);
		// Hide time label - no time limit here
		timeLabel.setVisible(false);
		// Show share button
		shareButton.setVisible(true);
		// Set css class for styling
		rootPane.getStyleClass().add("blue");
	}
	
	/**
	 * FXML-Action for back button
	 */
	@FXML
	public void backAction() {
		// No back button here
	}
	
	/**
	 * FXML-Action for share button
	 */
	@FXML
	public void shareAction() {
		ExportService.export(compileService.getExercise(), backButton.getScene().getWindow());
	}
	
	/**
	 * FXML-Action for confirm button
	 */
	@FXML
	public void confirmAction() {
		compileService.getExercise().getClasses().get(0).setContent(codeArea.getText());
		compileService.compileAndRunTests();

		// Check if there are compile errors
		if (!compileService.isValid() && compileService.getCompilerResult().hasCompileErrors()) {
			// Mark compile errors
			codeArea.markErrors(compileService.getCompileErrors());

			// Show alert
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("TDDT");
			alert.setHeaderText("Compile errors");
			alert.setContentText("There are compile errors in the tests or in the current code.");
			alert.showAndWait();
		}
		// Check if all tests are passed
		else if (!compileService.getCompilerResult().hasCompileErrors() && compileService.getTestResult().getNumberOfFailedTests() != 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("TDDT");
			alert.setHeaderText("Tests failed");
			alert.setContentText("Your code have not passed all tests.");
			alert.showAndWait();
		} else if (compileService.isValid()) {
			// Switch to RED
			switchToRed();
		}
	}
	
	/**
	 * Sets the compile service
	 * 
	 * @param compileService
	 */
	public void setCompileService(CompileService compileService) {
		this.compileService = compileService;
		compileService.setMode(CompileService.Mode.GREEN);
		compileService.setInformationBox(cycleInformationBox);
		compileService.setCodeArea(codeArea);
		codeArea.replaceText(compileService.getExercise().getClasses().get(0).getContent());
	}
	
	/**
	 * Sets the babysteps service
	 * 
	 * @param babystepsService
	 */
	public void setBabystepsService(BabystepsService babystepsService) {
		this.babystepsService = babystepsService;
		babystepsService.setTimeLabel(timeLabel);
		babystepsService.setCodeArea(codeArea);
		babystepsService.start();
	}
	
	/**
	 * Sets the tracking session
	 * 
	 * @param trackingSession the actual tracking result
	 */
	public void setTrackingSession(TrackingSession trackingSession) {
		this.trackingSession = trackingSession;
		createTrackingPoint();
	}

	/**
	 * Sets the main section
	 * 
	 * @param mainSection
	 */
	public void setMainSection(Pane mainSection) {
		this.mainSection = mainSection;
	}
	
	/**
	 * Switches to red
	 */
	private void switchToRed() {
		if(babystepsService != null)
			babystepsService.stop();
			
		endTracking();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
			RedViewController testController = new RedViewController();
			loader.setController(testController);
			Parent testView = loader.load();
			MainViewController.setAllAnchorsNull(testView);
			testController.setCompileService(compileService);
			testController.setBabystepsService(babystepsService);
			testController.setTrackingSession(trackingSession);
			testController.setMainSection(mainSection);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(testView);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Creates tracking point if tracking is activated
	 */
	private void createTrackingPoint() {
		if(compileService.getExercise().getConfig().isTimeTracking())
			trackingData = new TrackingData(TrackingData.Mode.BLUE, new Date());
	}
	
	/**
	 * Lets tracking end and adds tracking point to tracking session
	 */
	private void endTracking() {
		if(compileService.getExercise().getConfig().isTimeTracking()) {
			trackingData.setEnd(new Date());
			trackingSession.getData().add(trackingData);
		}
	}
}
