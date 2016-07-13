package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import models.Class;
import gui.views.cycle.JavaCodeArea;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import models.Exercise;
import models.Test;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

public class CompileService {

	public enum Mode {
		RED, GREEN, BLUE
	}

	Exercise exercise;
	JavaCodeArea codeArea;
	JavaStringCompiler compiler;
	CompilerResult compilerResult;
	Mode mode;
	
	Thread compileThread;
	ObservableList<CompileError> compileErrors;

	/**
	 * Initializes a compile service with an exercise
	 * 
	 * @param exercise
	 */
	public CompileService(Exercise exercise, JavaCodeArea codeArea) {
		this.exercise = exercise;
		this.codeArea = codeArea;
		
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
	 * Gets the last compiler errors for mode
	 * 
	 * @return
	 */
	public Collection<CompileError> getCompileErrors() {
		Collection<CompileError> compileErrors = new ArrayList<CompileError>();
		switch (mode) {
		case RED:
			for (int i = 0; i < exercise.getTests().size(); i++) {
				Test exerciseTest = exercise.getTests().get(i);
				CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseTest.getName());
				compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(testUnit));
			}
			break;
		case GREEN:
		case BLUE:
			for (Class exerciseClass : exercise.getClasses()) {
				CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseClass.getName());
				compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(testUnit));
			}
		}

		return compileErrors;
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
	 * Gets the last test result
	 * 
	 * @return
	 */
	public TestResult getTestResult() {
		return compiler.getTestResult();
	}

	/**
	 * Gets the last compiler result
	 */
	public CompilerResult getCompilerResult() {
		return compilerResult;
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
					});
				});
				compileThread.start();
			}
		});
		
	}

}
