package gui.views.cycle;

import vk.core.api.TestFailure;

public class TestFailureItem extends InformationItem {
	
	/**
	 * Constructs a test failure item
	 * 
	 * @param testFailure
	 */
	public TestFailureItem(TestFailure testFailure) {
		super(testFailure.getMethodName(), testFailure.getMessage());
		getStyleClass().add("test-failure-item");
	}

}
