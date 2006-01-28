/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
