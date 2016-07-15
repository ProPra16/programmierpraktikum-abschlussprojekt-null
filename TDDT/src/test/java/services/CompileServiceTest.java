package services;

import static org.junit.Assert.*;
import org.junit.Test;

import gui.views.cycle.JavaCodeArea;
import javafx.scene.layout.VBox;
import models.Exercise;
import services.CompileService.Mode;
import models.Class;


public class CompileServiceTest {
	
	@Test
	public void testInvalidCompileError() {
		Exercise exercise = new Exercise();
		models.Test exerciseTest = new models.Test();
		exerciseTest.setName("CompileErrorTest");
		exerciseTest.setContent("compile error");
		
		Class exerciseClass = new Class();
		exerciseClass.setName("CompileError");
		exerciseClass.setContent("also compile error");

		exercise.addTest(exerciseTest);
		exercise.addClass(exerciseClass);
		
		CompileService compileService = new CompileService(exercise, new JavaCodeArea(), new VBox());
		
		compileService.setMode(Mode.RED);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), false);
		
		compileService.setMode(Mode.GREEN);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), false);
		
		compileService.setMode(Mode.BLUE);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), false);
	}
	
	@Test
	public void testInvalidRed() {
		Exercise exercise = new Exercise();
		models.Test exerciseTest = new models.Test();
		exerciseTest.setName("RedValidTest");
		exerciseTest.setContent(""
				+ "import static org.junit.Assert.*;"
				+ "import org.junit.Test;"
				+ "public class RedValidTest {"
				+ "  @Test"
				+ "  public void failingTest() { "
				+ "    assertEquals(1,1);"
				+ "  }"
				+ "}");
		
		Class exerciseClass = new Class();
		exerciseClass.setName("RedValid");
		exerciseClass.setContent("public class RedValid {}");

		exercise.addTest(exerciseTest);
		exercise.addClass(exerciseClass);
		
		CompileService compileService = new CompileService(exercise, new JavaCodeArea(), new VBox());
		compileService.setMode(Mode.RED);
		compileService.compileAndRunTests();
		
		assertEquals(compileService.isValid(), false);
	}
	
	@Test
	public void testValidRed() {
		Exercise exercise = new Exercise();
		models.Test exerciseTest = new models.Test();
		exerciseTest.setName("RedValidTest");
		exerciseTest.setContent(""
				+ "import static org.junit.Assert.*;"
				+ "import org.junit.Test;"
				+ "public class RedValidTest {"
				+ "  @Test"
				+ "  public void failingTest() { "
				+ "    fail();"
				+ "  }"
				+ "}");
		
		Class exerciseClass = new Class();
		exerciseClass.setName("RedValid");
		exerciseClass.setContent("public class RedValid {}");

		exercise.addTest(exerciseTest);
		exercise.addClass(exerciseClass);
		
		CompileService compileService = new CompileService(exercise, new JavaCodeArea(), new VBox());
		compileService.setMode(Mode.RED);
		compileService.compileAndRunTests();
		
		assertEquals(compileService.isValid(), true);
	}
	
	@Test
	public void testInvalidGreenAndBlue() {
		Exercise exercise = new Exercise();
		models.Test exerciseTest = new models.Test();
		exerciseTest.setName("InvalidGreenAndBlueTest");
		exerciseTest.setContent(""
				+ "import static org.junit.Assert.*;"
				+ "import org.junit.Test;"
				+ "public class InvalidGreenAndBlueTest {"
				+ "  @Test"
				+ "  public void aTest() { "
				+ "    assertEquals(InvalidGreenAndBlue.get(), 2);"
				+ "  }"
				+ "}");
		
		Class exerciseClass = new Class();
		exerciseClass.setName("InvalidGreenAndBlue");
		exerciseClass.setContent(""
				+ "public class InvalidGreenAndBlue {"
				+ "  public static int get() {"
				+ "    return 1;"
				+ "  }"
				+ "}");

		exercise.addTest(exerciseTest);
		exercise.addClass(exerciseClass);
		
		CompileService compileService = new CompileService(exercise, new JavaCodeArea(), new VBox());
		
		compileService.setMode(Mode.GREEN);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), false);
		
		compileService.setMode(Mode.BLUE);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), false);
	}
	
	@Test
	public void testValidGreenAndBlue() {
		Exercise exercise = new Exercise();
		models.Test exerciseTest = new models.Test();
		exerciseTest.setName("ValidGreenAndBlueTest");
		exerciseTest.setContent(""
				+ "import static org.junit.Assert.*;"
				+ "import org.junit.Test;"
				+ "public class ValidGreenAndBlueTest {"
				+ "  @Test"
				+ "  public void aTest() { "
				+ "    assertEquals(ValidGreenAndBlue.get(), 1);"
				+ "  }"
				+ "}");
		
		Class exerciseClass = new Class();
		exerciseClass.setName("ValidGreenAndBlue");
		exerciseClass.setContent(""
				+ "public class ValidGreenAndBlue {"
				+ "  public static int get() {"
				+ "    return 1;"
				+ "  }"
				+ "}");

		exercise.addTest(exerciseTest);
		exercise.addClass(exerciseClass);
		
		CompileService compileService = new CompileService(exercise, new JavaCodeArea(), new VBox());
		
		compileService.setMode(Mode.GREEN);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), true);
		
		compileService.setMode(Mode.BLUE);
		compileService.compileAndRunTests();
		assertEquals(compileService.isValid(), true);
	}

}
