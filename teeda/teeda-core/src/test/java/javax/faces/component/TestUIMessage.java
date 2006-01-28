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


public class TestUIMessage extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIMessage.class);
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
	 * Constructor for TestUIMessage.
	 * @param arg0
	 */
	public TestUIMessage(String arg0) {
		super(arg0);
	}

	public void testUIMessage(){
		UIMessage message = new UIMessage();
		assertEquals(message.getRendererType(), "javax.faces.Message");
	}

	public void testGetFamily(){
		UIMessage message = new UIMessage();
		assertEquals(UIMessage.COMPONENT_FAMILY, message.getFamily());
	}

	public void testGetFor(){
		UIMessage message = new UIMessage();
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), "aaa");
		message.setValueBinding("for", vb);
		assertEquals("aaa", message.getFor());
	}
	
	public void testSetFor(){
		UIMessage message = new UIMessage();
		message.setFor("FOR");
		assertEquals("FOR", message.getFor());
	}
	
	public void testIsShowDetail(){
		UIMessage message = new UIMessage();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		message.setValueBinding("showDetail", vb);
		assertTrue(message.isShowDetail());
	}
	
	public void testSetShowDetail(){
		UIMessage message = new UIMessage();
		message.setShowDetail(true);
		assertTrue(message.isShowDetail());
	}

	public void testIsShowSummary(){
		UIMessage message = new UIMessage();
		Boolean value = Boolean.TRUE;
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), value);
		message.setValueBinding("showSummary", vb);
		assertTrue(message.isShowSummary());
	}
	
	public void testSetShowSummary(){
		UIMessage message = new UIMessage();
		message.setShowSummary(true);
		assertTrue(message.isShowSummary());
	}

}
