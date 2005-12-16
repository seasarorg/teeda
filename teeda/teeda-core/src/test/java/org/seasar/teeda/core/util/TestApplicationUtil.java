package org.seasar.teeda.core.util;

import javax.faces.application.Application;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestApplicationUtil extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestApplicationUtil.class);
	}

	public TestApplicationUtil(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testApplicationFromFactory(){
		Application application = 
			ApplicationUtil.getApplicationFromFactory();
		assertNotNull(application);
		assertTrue(application instanceof MockApplication);
	}
	
	public void testApplicationFromContext(){
		Application application = 
			ApplicationUtil.getApplicationFromContext();
		assertNotNull(application);
		assertTrue(application instanceof MockApplication);		
	}
	
}
