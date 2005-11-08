package javax.faces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.mock.MockFacesContext;
import javax.faces.mock.MockFacesEvent;
import javax.faces.mock.MockUIComponent;
import javax.faces.mock.MockValueBinding;

import junit.framework.TestCase;

public class TestUIViewRoot extends TestCase {

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
		vb.setValue(new MockFacesContext(), "aaa");
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
		vb.setValue(new MockFacesContext(), "bbb");
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
		
		MockFacesContext context = new MockFacesContext();
		root.processApplication(context);
		
	}	
}
