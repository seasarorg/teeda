package javax.faces.event;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestsAllEvent extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestsAllEvent.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for javax.faces.event");
		// $JUnit-BEGIN$
		suite.addTest(new TestSuite(TestActionEvent.class));
		suite.addTest(new TestSuite(TestFacesEvent.class));
		// $JUnit-END$
		return suite;
	}

}
