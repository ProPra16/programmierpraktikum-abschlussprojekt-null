package gui.controllers.cycle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.views.cycle.JavaCodeArea;

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
import models.TrackingData;
import models.TrackingSession;
import services.BabystepsService;
import services.CompileService;

public class GreenViewController implements Initializable {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set css class for styling
		if (rootPane.getStyleClass().contains("red")) {
			rootPane.getStyleClass().remove("red");
		}
		rootPane.getStyleClass().add("green");
	}

	/**
	 * FXML-Action for back button
	 */
	@FXML
	public void backAction() {
		// Ask for going back
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("TDDT");
		alert.setHeaderText("Going back");
		alert.setContentText("Are you sure you want to go back to edit the tests?");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			// Go back to RED
			switchToRed();
		}
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
			switchToBlue();
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
		compileService.setCodeArea(codeArea);
		codeArea.replaceText(compileService.getExercise().getClasses().get(0).getContent());
		
		// starting Babysteps
		if(compileService.getExercise().getConfig().isBabySteps()) {
			babystepsService = new BabystepsService(compileService.getExercise(), timeLabel, codeArea.getText(), codeArea);
			babystepsService.start();
		}
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
	 * Switches to blue
	 */
	private void switchToBlue() {
		if(babystepsService != null)
			babystepsService.stop();
		
		endTracking();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
			BlueViewController refactorViewController = new BlueViewController();
			loader.setController(refactorViewController);
			Parent testView = loader.load();
			AnchorPane.setTopAnchor(testView, 0.0);
			AnchorPane.setLeftAnchor(testView, 0.0);
			AnchorPane.setRightAnchor(testView, 0.0);
			AnchorPane.setBottomAnchor(testView, 0.0);
			refactorViewController.setCompileService(compileService);
			refactorViewController.setTrackingSession(trackingSession);
			refactorViewController.setMainSection(mainSection);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(testView);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Switches back to red
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
			AnchorPane.setTopAnchor(testView, 0.0);
			AnchorPane.setLeftAnchor(testView, 0.0);
			AnchorPane.setRightAnchor(testView, 0.0);
			AnchorPane.setBottomAnchor(testView, 0.0);
			testController.setCompileService(compileService);
			testController.setTrackingSession(trackingSession);
			testController.setMainSection(mainSection);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(testView);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates tracking point
	 */
	private void createTrackingPoint() {
		trackingData = new TrackingData(TrackingData.Mode.GREEN, new Date());
	}
	
	/**
	 * Lets tracking end and adds tracking point to tracking session
	 */
	private void endTracking() {
		trackingData.setEnd(new Date());
		trackingSession.getData().add(trackingData);
	}
}
