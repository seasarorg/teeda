package javax.faces.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestsAllModel extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestsAllModel.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for javax.faces.model");
		// $JUnit-BEGIN$
		suite.addTest(new TestSuite(TestDataModel.class));
		suite.addTest(new TestSuite(TestArrayDataModel.class));
		suite.addTest(new TestSuite(TestDataModelEvent.class));
		suite.addTest(new TestSuite(TestListDataModel.class));
		suite.addTest(new TestSuite(TestResultDataModel.class));
		// $JUnit-END$
		return suite;
	}

}
