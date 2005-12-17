package org.seasar.teeda.core.util;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;

import org.seasar.teeda.core.mock.MockLifecycle;
import org.seasar.teeda.core.mock.MockPhaseListener;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestLifecycleUtil extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestLifecycleUtil.class);
	}

	public TestLifecycleUtil(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetLifecycle(){
        Lifecycle lifecycle = LifecycleUtil.getLifecycle(getExternalContext());
        assertNotNull(lifecycle);
        assertTrue(lifecycle instanceof MockLifecycle);
        
        LifecycleFactory factory = FactoryFinderUtil.getLifecycleFactory();
        MockLifecycle mock = new MockLifecycle();
        MockPhaseListener listener = new MockPhaseListener("mock");
        mock.addPhaseListener(listener);
        factory.addLifecycle("hoge", mock);

        getServletContext().setInitParameter(FacesServlet.LIFECYCLE_ID_ATTR, "hoge");
        lifecycle = LifecycleUtil.getLifecycle(getExternalContext());
        assertNotNull(lifecycle);
        assertTrue(lifecycle instanceof MockLifecycle);
        assertEquals("mock", (lifecycle.getPhaseListeners()[0].toString()));
	}
	
	public void testGetLifecycleId(){
		assertEquals(LifecycleFactory.DEFAULT_LIFECYCLE, 
				LifecycleUtil.getLifecycleId(getExternalContext()));
		
		getServletContext().setInitParameter(FacesServlet.LIFECYCLE_ID_ATTR, "hoge");
		assertEquals("hoge", LifecycleUtil.getLifecycleId(getExternalContext()));
	}
}
