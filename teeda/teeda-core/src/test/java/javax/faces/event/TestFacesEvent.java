package javax.faces.event;

import javax.faces.component.UIComponent;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;


public class TestFacesEvent extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestFacesEvent.class);
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
	 * Constructor for TestFacesEvent.
	 * @param arg0
	 */
	public TestFacesEvent(String arg0) {
		super(arg0);
	}

	public void testFacesEvent() {
		
		MockUIComponent component = new MockUIComponent();
		FacesEvent event = new TargetFacesEvent(component);
		assertNotNull(event);
		
		try{
			FacesEvent event2 = new TargetFacesEvent(null);
			fail();
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}

	public void testGetComponent() {
		MockUIComponent component = new MockUIComponent();
		FacesEvent event = new TargetFacesEvent(component);
		assertEquals(component, event.getComponent());
	}

	public void testGetPhaseId() {
		MockUIComponent component = new MockUIComponent();
		FacesEvent event = new TargetFacesEvent(component);
		assertEquals(PhaseId.ANY_PHASE, event.getPhaseId());
	}

	public void testSetPhaseId() {
		MockUIComponent component = new MockUIComponent();
		FacesEvent event = new TargetFacesEvent(component);
		event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
		
		assertEquals(PhaseId.APPLY_REQUEST_VALUES, event.getPhaseId());
	}

	// TODO : need to test correctly. In now, no idea for this.
	public void testQueue() {
		MockUIComponent component = new MockUIComponent();
		FacesEvent event = new TargetFacesEvent(component);
		
		event.queue();
		
	}

	private class TargetFacesEvent extends FacesEvent {

		public TargetFacesEvent(UIComponent component) {
			super(component);
		}

		public boolean isAppropriateListener(FacesListener listener) {
			throw new UnsupportedOperationException();
		}

		public void processListener(FacesListener listener) {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
