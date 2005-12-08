package javax.faces.event;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockUIComponent;

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

        FacesListener notActionListener = new FacesListener(){

            public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
            }
            
        };
		assertFalse(event.isAppropriateListener(notActionListener));
	}

	public void testProcessListener() throws Exception {

		ActionEvent event = new ActionEvent(new MockUIComponent());

		MockActionListener listener = new MockActionListener();
		event.processListener(listener);

		ActionEvent backEvent = listener.getEvent();
		assertEquals(event, backEvent);

	}

}
