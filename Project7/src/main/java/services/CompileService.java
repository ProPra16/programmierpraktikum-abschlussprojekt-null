package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import models.Class;
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
		RED, GREEN, REFACTOR
	}

	Exercise exercise;
	JavaStringCompiler compiler;
	CompilerResult compilerResult;
	Mode mode;
	boolean ignoreMethodErrors;

	/**
	 * Initializes a compile service with an exercise
	 * 
	 * @param exercise
	 */
	public CompileService(Exercise exercise) {
		this.exercise = exercise;
		ignoreMethodErrors = true;
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
		case REFACTOR:
			for (Class exerciseClass : exercise.getClasses()) {
				CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseClass.getName());
				compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(testUnit));
			}
		}

		return compileErrors;
	}

	/**
	 * Gets the last filtered compiler errors for mode
	 * 
	 * @return
	 */
	public Collection<CompileError> getFilteredCompileErrors() {
		Collection<CompileError> compileErrors = new ArrayList<CompileError>();
		switch (mode) {
		case RED:
			for (int i = 0; i < exercise.getTests().size(); i++) {
				Test exerciseTest = exercise.getTests().get(i);
				CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseTest.getName());
				compileErrors.addAll(compilerResult.getCompilerErrorsForCompilationUnit(testUnit));
				// Check if any compile error is caused by an not implemented
				// method, then remove them
				for (Iterator<CompileError> iterator = compileErrors.iterator(); iterator.hasNext();) {
					// Debug: System.out.println(error.getMessage());
					CompileError error = iterator.next();
					if (ignoreMethodErrors && error.getMessage().contains("method")
							&& error.getMessage().contains("type " + exercise.getClasses().get(i).getName())
							&& !error.getMessage().contains(exerciseTest.getName()))
						iterator.remove(); // Filter "valid" errors
				}
			}
			break;
		case GREEN:
		case REFACTOR:
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
		case REFACTOR:
			return isValidRefactor();
		default:
			return false;
		}
	}

	/**
	 * Checks for missing assertEquals
	 * 
	 * @return
	 */
	public boolean missingAssertEquals() {
		for (Test exerciseTest : exercise.getTests()) {
			if (!exerciseTest.getContent().contains("assertEquals")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if code is valid for red mode
	 * 
	 * @return
	 */
	private boolean isValidRed() {
		for (int i = 0; i < exercise.getTests().size(); i++) {
			Test exerciseTest = exercise.getTests().get(i);

			CompilationUnit testUnit = compiler.getCompilationUnitByName(exerciseTest.getName());
			Collection<CompileError> compilerErrors = new ArrayList<CompileError>(
					compilerResult.getCompilerErrorsForCompilationUnit(testUnit));

			// Check if it's very first compile and every possible compile error
			// is caused by an not implemented method, otherwise return false
			for (Iterator<CompileError> iterator = compilerErrors.iterator(); iterator.hasNext();) {
				// Debug: System.out.println(error.getMessage());
				CompileError error = iterator.next();
				if (!ignoreMethodErrors || !error.getMessage().contains("method")
						|| !error.getMessage().contains("type " + exercise.getClasses().get(i).getName())
						|| error.getMessage().contains(exerciseTest.getName()))
					return false;
				else
					iterator.remove(); // Filter "valid" errors
			}

			// Missing assert equals?
			if (!exerciseTest.getContent().contains("assertEquals")) {
				return false;
			}
		}

		if (!compilerResult.hasCompileErrors() && compiler.getTestResult().getNumberOfFailedTests() != 1) {
			return false;
		}

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
	 * Checks if code is valid for refactor mode
	 * 
	 * @return
	 */
	private boolean isValidRefactor() {
		return isValidGreen();
	}

}
