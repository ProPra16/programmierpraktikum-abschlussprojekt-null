package gui.views.cycle;

import vk.core.api.CompileError;

public class CompileErrorItem extends InformationItem {

	/**
	 * Constructs a compile error item
	 * 
	 * @param compileError
	 * @param className
	 */
	public CompileErrorItem(CompileError compileError, String className) {
		super("Compile error at " + compileError.getLineNumber() + ":" + compileError.getColumnNumber(), compileError.getMessage() + " (" + className + ")");
		getStyleClass().add("compile-error-item");		
	}
}
