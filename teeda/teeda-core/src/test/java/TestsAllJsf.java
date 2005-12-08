import javax.faces.convert.TestsAllConvert;
import javax.faces.event.TestsAllEvent;
import javax.faces.model.TestsAllModel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



public class TestsAllJsf extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestsAllEvent.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for javax.faces.*");
		// $JUnit-BEGIN$
		suite.addTest(TestsAllEvent.suite());
		suite.addTest(TestsAllModel.suite());
		suite.addTest(TestsAllConvert.suite());
		// $JUnit-END$
		return suite;
	}

}
