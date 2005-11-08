package javax.faces.event;

import javax.faces.mock.MockActionListener;
import javax.faces.mock.MockNotActionListener;
import javax.faces.mock.MockUIComponent;

import junit.framework.TestCase;

public class TestActionEvent extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestActionEvent.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for TestActionEvent.
	 * 
	 * @param arg0
	 */
	public TestActionEvent(String arg0) {
		super(arg0);
	}

	public void testActionEvent() throws Exception {
		ActionEvent event = new ActionEvent(new MockUIComponent());
		assertTrue(event != null);

		try {
			ActionEvent event2 = new ActionEvent(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	public void testIsAppropriateListener() throws Exception {

		ActionEvent event = new ActionEvent(new MockUIComponent());
		assertTrue(event.isAppropriateListener(new MockActionListener()));

		assertFalse(event.isAppropriateListener(new MockNotActionListener()));
	}

	public void testProcessListener() throws Exception {

		ActionEvent event = new ActionEvent(new MockUIComponent());

		MockActionListener listener = new MockActionListener();
		event.processListener(listener);

		ActionEvent backEvent = listener.getEvent();
		assertEquals(event, backEvent);

	}

}
