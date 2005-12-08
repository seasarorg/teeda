package javax.faces.component;

import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockFacesEvent;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestUIViewRoot extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIViewRoot.class);
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
	 * Constructor for TestUIViewRoot.
	 * 
	 * @param arg0
	 */
	public TestUIViewRoot(String arg0) {
		super(arg0);
	}

	public void testGetFamily() {
		UIViewRoot root = new UIViewRoot();
		assertEquals(root.getFamily(), UIViewRoot.COMPONENT_FAMILY);
	}

	public void testGetRenderKitId() {
		UIViewRoot root = new UIViewRoot();
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(new MockFacesContextImpl(), "aaa");
		root.setValueBinding("renderKitId", vb);
		assertEquals("aaa", root.getRenderKitId());
	}

	public void testSetRenderKitId() {
		UIViewRoot root = new UIViewRoot();
		root.setRenderKitId("RENDER");
		assertEquals("RENDER", root.getRenderKitId());
	}

	public void testGetViewId() {
		UIViewRoot root = new UIViewRoot();
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(new MockFacesContextImpl(), "bbb");
		root.setValueBinding("viewId", vb);
		assertEquals("bbb", root.getViewId());
	}

	public void testSetViewId() {
		UIViewRoot root = new UIViewRoot();
		root.setRenderKitId("VIEW_ID");
		assertEquals("VIEW_ID", root.getRenderKitId());
	}

	public void testQueueEvent() {
		UIViewRoot root = new UIViewRoot();
		try {
			root.queueEvent(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
		
		MockUIComponent component = new MockUIComponent();
		MockFacesEvent event = new MockFacesEvent(component);
		//TODO impl test
		event.setPhaseId(PhaseId.ANY_PHASE);
		root.queueEvent(event);
		
		MockFacesContext context = getFacesContext();
		root.processApplication(context);
		
	}	
}
