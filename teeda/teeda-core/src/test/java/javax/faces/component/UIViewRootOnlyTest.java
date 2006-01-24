/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class UIViewRootOnlyTest extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(UIViewRootOnlyTest.class);
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
	 * Constructor for UIViewRootOnlyTest.
	 * 
	 * @param arg0
	 */
	public UIViewRootOnlyTest(String arg0) {
		super(arg0);
	}

	public void testGetRenderKitId() {
		UIViewRoot root = new UIViewRoot();
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), "aaa");
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

    public void testGetFamily() {
        assertEquals("javax.faces.ViewRoot", createUIViewRoot().getFamily());
    }

    public void testGetComponentType() {
        assertEquals("javax.faces.ViewRoot", createUIViewRoot().getFamily());
    }

    public void testCreateUniqueId() {
        UIViewRoot root = createUIViewRoot();
        assertEquals(UIViewRoot.UNIQUE_ID_PREFIX + "0", root.createUniqueId());
        assertEquals(UIViewRoot.UNIQUE_ID_PREFIX + "1", root.createUniqueId());
    }

    private UIViewRoot createUIViewRoot() {
        return (UIViewRoot) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIViewRoot();
    }

}
