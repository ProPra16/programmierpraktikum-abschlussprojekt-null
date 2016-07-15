package gui.controllers.cycle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.controllers.MainViewController;
import gui.views.cycle.JavaCodeArea;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Exercise;
import models.TrackingData;
import models.TrackingSession;
import services.BabystepsService;
import services.CompileService;
import services.StorageService;
import vk.core.api.TestResult;

public class RedViewController implements Initializable {
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
	Node confirmButton;
	@FXML
	Label timeLabel;
	@FXML
	VBox cycleInformationBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Disable back button - nothing to go back here
		backButton.setVisible(false);

		// Set css class for styling
		rootPane.getStyleClass().add("red");
	}

	/**
	 * FXML-Action for back button
	 */
	@FXML
	public void backAction() {
		// Disabled in RED, so do nothing
	}
	
	/**
	 * FXML-Action for share button
	 */
	@FXML
	public void shareAction() {
		// No share here
	}

	/**
	 * FXML-Action for confirm button
	 */
	@FXML
	public void confirmAction() {

		compileService.getExercise().getTests().get(0).setContent(codeArea.getText());
		compileService.compileAndRunTests();

		// Check if tests are valid
		if (!compileService.isValid() && !compileService.getCompilerResult().hasCompileErrors()) {
			TestResult testResult = compileService.getTestResult();

			if (testResult.getNumberOfFailedTests() == 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("TDDT");
				alert.setHeaderText("No failed tests");
				alert.setContentText("Please write a failing test to proceed.");
				alert.showAndWait();
			} else if (testResult.getNumberOfFailedTests() != 1) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("TDDT");
				alert.setHeaderText("Too many failing tests");
				alert.setContentText("Please write exactly one failing test.");
				alert.showAndWait();
			}
		} else if (compileService.getCompilerResult().hasCompileErrors()) {
			// There are compile errors

			// Mark errors in text field
			codeArea.markErrors(compileService.getCompileErrors());

			// Ask for them
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("TDDT");
			alert.setHeaderText("Compile errors");
			alert.setContentText("There are compile errors in your code, are you sure to preceed?");
			Optional<ButtonType> result = alert.showAndWait();

			// Switch to green, if user is sure
			if (result.get() == ButtonType.OK) {
				switchToGreen();
			}
		} else {
			// Really valid, so simply switch to green
			switchToGreen();
		}
	}

	/**
	 * Sets the exercise initially
	 * 
	 * @param exercise
	 */
	public void setExercise(Exercise exercise) {
		// Create compile service
		compileService = new CompileService(exercise, codeArea, cycleInformationBox);
		compileService.setMode(CompileService.Mode.RED);
		// Update source code
		codeArea.replaceText(exercise.getTests().get(0).getContent());
		
		// Start tracking-session
		trackingSession = StorageService.getInstance().gettSessionCatalog().startSession(exercise.getName(), new Date());
		createTrackingPoint();
		
		// Start babysteps
		babystepsService = new BabystepsService(compileService.getExercise(), timeLabel, codeArea);
		babystepsService.start();
	}

	/**
	 * Sets the compile service
	 * 
	 * @param compileService
	 */
	public void setCompileService(CompileService compileService) {
		this.compileService = compileService;
		compileService.setMode(CompileService.Mode.RED);
		compileService.setInformationBox(cycleInformationBox);
		compileService.setCodeArea(codeArea);
		codeArea.replaceText(compileService.getExercise().getTests().get(0).getContent());
	}
	
	/**
	 * Gets the babysteps service
	 * 
	 * @return
	 */
	public BabystepsService getBabystepsService() {
		return babystepsService;
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
	 * Switches to green
	 */
	private void switchToGreen() {
		
		
		// End tracking
		endTracking();
		
		// load red
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
			GreenViewController greenController = new GreenViewController();
			loader.setController(greenController);
			Parent cycleView = loader.load();
			MainViewController.setAllAnchorsNull(cycleView);
			greenController.setCompileService(compileService);
			greenController.setBabystepsService(babystepsService);
			greenController.setTrackingSession(trackingSession);
			greenController.setMainSection(mainSection);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(cycleView);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates tracking point if tracking is activated
	 */
	private void createTrackingPoint() {
		if(compileService.getExercise().getConfig().isTimeTracking())
			trackingData = new TrackingData(TrackingData.Mode.RED, new Date());
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