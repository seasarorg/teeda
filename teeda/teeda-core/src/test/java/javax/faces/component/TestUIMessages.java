package javax.faces.component;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestUIMessages extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIMessages.class);
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
	 * Constructor for TestUIMessages.
	 * @param arg0
	 */
	public TestUIMessages(String arg0) {
		super(arg0);
	}

	public void testUIMessages(){
		UIMessages messages = new UIMessages();
		assertEquals(messages.getRendererType(), "javax.faces.Messages");
	}

	public void testGetFamily(){
		UIMessages messages = new UIMessages();
		assertEquals(messages.getFamily(), UIMessages.COMPONENT_FAMILY);
	}
	
	public void testIsGlobalOnly(){
		UIMessages messages = new UIMessages();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		messages.setValueBinding("globalOnly", vb);
		assertTrue(messages.isGlobalOnly());
	}
	
	public void testSetGlobalOnly(){
		UIMessages messages = new UIMessages();
		messages.setGlobalOnly(true);
		assertTrue(messages.isGlobalOnly());
	}

	public void testIsShowDetail(){
		UIMessages messages = new UIMessages();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		messages.setValueBinding("showDetail", vb);
		assertTrue(messages.isShowDetail());
	}
	
	public void testSetShowDetail(){
		UIMessages messages = new UIMessages();
		messages.setShowDetail(true);
		assertTrue(messages.isShowDetail());
	}

	public void testIsShowSummary(){
		UIMessages messages = new UIMessages();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		messages.setValueBinding("showSummary", vb);
		assertTrue(messages.isShowSummary());
	}
	
	public void testSetShowSummary(){
		UIMessages messages = new UIMessages();
		messages.setShowSummary(true);
		assertTrue(messages.isShowSummary());
	}

}
