package gui.controllers.cycle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import gui.views.cycle.JavaCodeArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.TrackingData;
import models.TrackingResult;
import services.CompileService;

public class BlueViewController implements Initializable {
	Pane mainSection;
	CompileService compileService;
	TrackingResult trackingSession;
	TrackingData trackingData;

	@FXML
	AnchorPane rootPane;
	@FXML
	JavaCodeArea sourceTextField;
	@FXML
	Node backButton;
	@FXML
	Node confirmButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Disable back button - don't edit red 
		backButton.setVisible(false);
		// Set css class for styling
		if (rootPane.getStyleClass().contains("green")) {
			rootPane.getStyleClass().remove("green");
		}
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
	 * FXML-Action for confirm button
	 */
	@FXML
	public void confirmAction() {
		compileService.getExercise().getClasses().get(0).setContent(sourceTextField.getText());
		compileService.compileAndRunTests();

		// Check if there are compile errors
		if (!compileService.isValid() && compileService.getCompilerResult().hasCompileErrors()) {
			// Mark compile errors
			sourceTextField.markErrors(compileService.getCompileErrors());

			// Show alert
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("TDDT");
			alert.setHeaderText("Compile errors");
			alert.setContentText("There are compile errors in the tests or in the current code.");
			alert.showAndWait();
		}
		// Check if all tests are passed
		else if (!compileService.getCompilerResult().hasCompileErrors()
				&& compileService.getTestResult().getNumberOfFailedTests() != 0) {
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
		sourceTextField.replaceText(compileService.getExercise().getClasses().get(0).getContent());
	}
	
	/**
	 * Sets the tracking session
	 * 
	 * @param trackingSession the actual tracking result
	 */
	public void setTrackingSession(TrackingResult trackingSession) {
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
		endTracking();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
			RedViewController testController = new RedViewController();
			loader.setController(testController);
			Parent testView = loader.load();
			AnchorPane.setTopAnchor(testView, 0.0);
			AnchorPane.setLeftAnchor(testView, 0.0);
			AnchorPane.setRightAnchor(testView, 0.0);
			AnchorPane.setBottomAnchor(testView, 0.0);
			testController.setCompileService(compileService);
			testController.setTrackingSession(trackingSession);
			testController.setMainSection(mainSection);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(testView);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Creates tracking point
	 */
	private void createTrackingPoint() {
		trackingData = new TrackingData(TrackingData.Mode.BLUE, new Date());
	}
	
	/**
	 * Lets tracking end and adds tracking point to tracking session
	 */
	private void endTracking() {
		trackingData.setEnd(new Date());
		trackingSession.getData().add(trackingData);
	}
}
