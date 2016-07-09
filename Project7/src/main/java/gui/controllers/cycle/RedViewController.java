package gui.controllers.cycle;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.views.cycle.JavaCodeArea;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.Exercise;
import services.CompileService;
import vk.core.api.CompileError;
import vk.core.api.TestResult;

public class RedViewController implements Initializable {
	Pane mainSection;
	CompileService compileService;

	@FXML
	AnchorPane rootPane;
	@FXML
	JavaCodeArea sourceTextField;
	@FXML
	Node backButton;
	@FXML
	Node confirmButton;

	Thread compileThread;
	ObservableList<CompileError> compileErrors;;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Disable back button - nothing to go back here
		backButton.setVisible(false);

		// Set css class for styling
		if (rootPane.getStyleClass().contains("green")) {
			rootPane.getStyleClass().remove("green");
		}
		rootPane.getStyleClass().add("red");

		/*
		 * TODO autocompile - Not working until yet... // Initialize observable
		 * list compileErrors = FXCollections.observableArrayList(); // Mark
		 * compile errors, if they changed compileErrors.addListener(new
		 * ListChangeListener<CompileError>() {
		 * 
		 * @Override public void onChanged(Change<? extends CompileError>
		 * change) { sourceTextField.markErrors(compileErrors); } });
		 * 
		 * // Auto compile on change
		 * sourceTextField.textProperty().addListener((observable, oldValue,
		 * newValue) -> { // Check if possible previous compile is finished
		 * (thread terminated) if(compileThread == null ||
		 * compileThread.getState() == Thread.State.TERMINATED) { // Then
		 * compile and change errors compileThread = new Thread(() -> {
		 * compileService.compileAndRunTests(); compileErrors.clear();
		 * compileErrors.addAll(compileService.getCompileErrors()); });
		 * compileThread.start(); } });
		 */

	}

	/**
	 * FXML-Action for back button
	 */
	@FXML
	public void backAction() {
		// Disabled in RED, so do nothing
	}

	/**
	 * FXML-Action for confirm button
	 */
	@FXML
	public void confirmAction() {

		compileService.getExercise().getTests().get(0).setContent(sourceTextField.getText());

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
			sourceTextField.markErrors(compileService.getCompileErrors());

			// Ask for them
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("TDDT");
			alert.setHeaderText("Compile errors");
			alert.setContentText("There are compile errors in your code, are you sure to preceed?");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				// If assertEquals is missing, also ask for it
				if (compileService.missingAssertEquals()) {
					alert.setHeaderText("Missing assertEquals");
					alert.setContentText("We think there is missing an assertEquals, are you sure to proceed?");
					result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						// Switch to green, if user is really sure for both
						switchToGreen();
					}
				} else {
					// Switch to green, if user is sure
					switchToGreen();
				}
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
		compileService = new CompileService(exercise);
		compileService.setMode(CompileService.Mode.RED);
		sourceTextField.replaceText(exercise.getTests().get(0).getContent());
	}

	/**
	 * Sets the compile service
	 * 
	 * @param compileService
	 */
	public void setCompileService(CompileService compileService) {
		this.compileService = compileService;
		compileService.setMode(CompileService.Mode.RED);
		sourceTextField.replaceText(compileService.getExercise().getTests().get(0).getContent());
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
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
			GreenViewController exerciseController = new GreenViewController();
			loader.setController(exerciseController);
			Parent exerciseView = loader.load();
			exerciseController.setCompileService(compileService);
			AnchorPane.setTopAnchor(exerciseView, 0.0);
			AnchorPane.setLeftAnchor(exerciseView, 0.0);
			AnchorPane.setRightAnchor(exerciseView, 0.0);
			AnchorPane.setBottomAnchor(exerciseView, 0.0);
			exerciseController.setMainSection(mainSection);
			mainSection.getChildren().clear();
			mainSection.getChildren().add(exerciseView);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}