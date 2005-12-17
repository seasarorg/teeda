package org.seasar.teeda.core.util;

import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;

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
//		notDoneYet();
	}
	
	public void testGetLifecycleId(){
		assertEquals(LifecycleFactory.DEFAULT_LIFECYCLE, 
				LifecycleUtil.getLifecycleId(getExternalContext()));
		
		getServletContext().setInitParameter(FacesServlet.LIFECYCLE_ID_ATTR, "hoge");
		assertEquals("hoge", LifecycleUtil.getLifecycleId(getExternalContext()));
	}
}
