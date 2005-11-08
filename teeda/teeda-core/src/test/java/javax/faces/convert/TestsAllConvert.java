package javax.faces.convert;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestsAllConvert extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestsAllConvert.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for javax.faces.event");
		// $JUnit-BEGIN$
		suite.addTest(new TestSuite(TestConverterException.class));
		suite.addTest(new TestSuite(TestBigDecimalConverter.class));
		suite.addTest(new TestSuite(TestBigIntegerConverter.class));
		suite.addTest(new TestSuite(TestBooleanConverter.class));
		suite.addTest(new TestSuite(TestByteConverter.class));
		suite.addTest(new TestSuite(TestCharacterConverter.class));
		suite.addTest(new TestSuite(TestDateTimeConverter.class));
		// $JUnit-END$
		return suite;
	}

}
