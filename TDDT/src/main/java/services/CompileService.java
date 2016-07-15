package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import gui.views.cycle.CompileErrorItem;
import gui.views.cycle.JavaCodeArea;
import gui.views.cycle.TestFailureItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.Class;
import models.Exercise;
import models.Test;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

public class CompileService {

	public enum Mode {
		RED, GREEN, BLUE
	}

	Exercise exercise;
	JavaCodeArea codeArea;
	VBox cycleInformationBox;
	JavaStringCompiler compiler;
	CompilerResult compilerResult;
	Mode mode;
	
	Thread compileThread;
	ObservableList<CompileError> compileErrors;

	/**
	 * Initializes a compile service with an exercise
	 * 
	 * @param exercise
	 * @param codeArea
	 * @param cycleInformationBox
	 */
	public CompileService(Exercise exercise, JavaCodeArea codeArea, VBox cycleInformationBox) {
		this.exercise = exercise;
		this.codeArea = codeArea;
		this.cycleInformationBox = cycleInformationBox;
		
		addAutocompile();
	}

	/**
	 * Gets the related exercise
	 * 
	 * @return
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * Sets the compile mode
	 * 
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	/**
	 * Sets the code area 
	 * 
	 * @param codeArea
	 */
	public void setCodeArea(JavaCodeArea codeArea) {
		this.codeArea = codeArea;
		addAutocompile();
	}
	
	/**
	 * Sets the cycle information box
	 * 
	 * @param cycleInformationBox
	 */
	public void setInformationBox(VBox cycleInformationBox) {
		this.cycleInformationBox = cycleInformationBox;
	}

	/**
	 * Gets the last compiler errors for current mode
	 * 
	 * @return collection of {@link CompileError}
	 */
	public Collection<CompileError> getCompileErrors() {
		Collection<CompileError> compileErrors = new ArrayList<CompileError>();
		switch (mode) {
		case RED:
			for (Test exerciseTest : exercise.getTests()) {
				CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseTest.getName());
				compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(testUnit));
			}
			break;
		case GREEN:
		case BLUE:
			for (Class exerciseClass : exercise.getClasses()) {
				CompilationUnit classUnit = compiler.getCompilationUnitByName(exerciseClass.getName());
				compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(classUnit));
			}
		}

		return compileErrors;
	}
	
	/**
	 * Gets all of the last compile errors
	 * 
	 * @return collection of {@link CompileError}
	 */
	public Collection<CompileError> getAllCompileErrors() {
		Collection<CompileError> compileErrors = new ArrayList<CompileError>();
		
		for (Test exerciseTest : exercise.getTests()) {
			CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseTest.getName());
			compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(testUnit));
		}

		for (Class exerciseClass : exercise.getClasses()) {
			CompilationUnit classUnit = compiler.getCompilationUnitByName(exerciseClass.getName());
			compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(classUnit));
		}

		return compileErrors;
	}

	/**
	 * Gets the last test result
	 * 
	 * @return {@link TestResult}
	 */
	public TestResult getTestResult() {
		return compiler.getTestResult();
	}

	/**
	 * Gets the last compiler result
	 * 
	 * @return {@link CompilerResult}
	 */
	public CompilerResult getCompilerResult() {
		return compilerResult;
	}
	
	/**
	 * Compiles and runs tests
	 */
	public void compileAndRunTests() {
		List<CompilationUnit> compilationUnits = new ArrayList<CompilationUnit>();
		for (Test exerciseTest : exercise.getTests()) {
			compilationUnits.add(new CompilationUnit(exerciseTest.getName(), exerciseTest.getContent(), true));
		}

		for (Class exerciseClass : exercise.getClasses()) {
			compilationUnits.add(new CompilationUnit(exerciseClass.getName(), exerciseClass.getContent(), false));
		}
		compiler = CompilerFactory.getCompiler(compilationUnits.toArray(new CompilationUnit[0]));
		compiler.compileAndRunTests();
		compilerResult = compiler.getCompilerResult();
	}

	/**
	 * Checks if code is valid for given mode
	 * 
	 * @return true, if code is valid, false otherwise
	 */
	public boolean isValid() {
		switch (mode) {
		case RED:
			return isValidRed();
		case GREEN:
			return isValidGreen();
		case BLUE:
			return isValidBlue();
		default:
			return false;
		}
	}

	/**
	 * Checks if code is valid for red mode
	 * 
	 * @return
	 */
	private boolean isValidRed() {
		if (compilerResult.hasCompileErrors() || compiler.getTestResult().getNumberOfFailedTests() != 1)
			return false;

		return true;
	}

	/**
	 * Checks if code is valid for green mode
	 * 
	 * @return
	 */
	private boolean isValidGreen() {
		if (compilerResult.hasCompileErrors() || compiler.getTestResult().getNumberOfFailedTests() != 0)
			return false;

		return true;
	}

	/**
	 * Checks if code is valid for blue mode
	 * 
	 * @return
	 */
	private boolean isValidBlue() {
		return isValidGreen(); // no difference in validation
	}
	
	/**
	 * Auto compile on change 
	 */
	private void addAutocompile() {
		// Initialize observable list
		compileErrors = FXCollections.observableArrayList();
		// Mark compile errors, if they changed
		compileErrors.addListener(new ListChangeListener<CompileError>() {
			@Override
			public void onChanged(Change<? extends CompileError> change) {
				codeArea.markErrors(compileErrors);
			}
		});
		
		// Auto compile on change 
		codeArea.textProperty().addListener((observable, oldValue, newValue) -> {
			// Check if possible previous compile is finished (thread terminated) 
			if(compileThread == null || compileThread.getState() == Thread.State.TERMINATED) {
				// Then compile and change errors  
				compileThread = new Thread(() -> {
					Platform.runLater(() -> {
						
						switch(mode) {
						case RED:
							exercise.getTests().get(0).setContent(codeArea.getText());
							break;
						case GREEN:
						case BLUE:
							exercise.getClasses().get(0).setContent(codeArea.getText());
							break;
						}
						
						compileAndRunTests();
						compileErrors.clear();
						compileErrors.addAll(getCompileErrors());
						showInformations();
					});
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("Error while measuring time");
					}
				});
				
				compileThread.start();
			}
		});
		
	}
	
	/**
	 * Shows informations in cycleInformationPane
	 */
	private void showInformations() {
		
		cycleInformationBox.getChildren().clear();
		cycleInformationBox.setAlignment(Pos.TOP_LEFT);
		
		/*Label compileDurationLabel = new Label("Compile time: " + compilerResult.getCompileDuration().toMillis() + "ms");
		VBox.setMargin(compileDurationLabel, new Insets(0, 0, 20, 0));
		compileDurationLabel.getStyleClass().add("compile-time");
		cycleInformationBox.getChildren().add(compileDurationLabel);*/
		
		if(compiler.getCompilerResult().hasCompileErrors()) {
			for (Test exerciseTest : exercise.getTests()) {
				CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseTest.getName());
				
				for(CompileError compileError : compilerResult.getCompilerErrorsForCompilationUnit(testUnit))
					cycleInformationBox.getChildren().add(new CompileErrorItem(compileError, exerciseTest.getName()));
			}

			for (Class exerciseClass : exercise.getClasses()) {
				CompilationUnit classUnit = compiler.getCompilationUnitByName(exerciseClass.getName());
				
				for(CompileError compileError : compilerResult.getCompilerErrorsForCompilationUnit(classUnit))
					cycleInformationBox.getChildren().add(new CompileErrorItem(compileError, exerciseClass.getName()));
			}
		} else {
			
			
			// Show check icon if mode is solved
			if(isValid()) {
				ImageView checkImageView = new ImageView(new Image("/gui/images/icons/check.png"));
				checkImageView.setFitWidth(cycleInformationBox.getWidth() - 40);
				checkImageView.setPreserveRatio(true);
				checkImageView.getStyleClass().add("check-icon");
				cycleInformationBox.setAlignment(Pos.CENTER);
				cycleInformationBox.getChildren().add(checkImageView);
			} else {
				for(TestFailure testFailure : compiler.getTestResult().getTestFailures()) {
					cycleInformationBox.getChildren().add(new TestFailureItem(testFailure));
				}
			}
			
		}
		
	}

}
