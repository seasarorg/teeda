/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.NullFacesContext;

/**
 * @author manhole
 */
public class UIFormOnlyTest extends TestCase {

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Form", UIForm.COMPONENT_FAMILY);
        assertEquals("javax.faces.Form", UIForm.COMPONENT_TYPE);
    }

    public void testGetFamily() {
        assertEquals("javax.faces.Form", new UIForm().getFamily());
    }

    public void testDefaultRendererType() throws Exception {
        assertEquals("javax.faces.Form", new UIForm().getRendererType());
    }

    public void testProcessDecodes() throws Exception {
        // ## Arrange ##
        final List calls = new ArrayList();
        UIForm form = new UIForm() {
            public void decode(FacesContext context) {
                calls.add("form");
                setSubmitted(true);
            }
        };
        UIComponentBase child = new MockUIComponentBase() {
            public void decode(FacesContext context) {
                calls.add("child");
            }
        };
        form.getChildren().add(child);

        // ## Act ##
        form.processDecodes(new NullFacesContext());

        // ## Assert ##
        assertEquals(2, calls.size());
        assertEquals("form should be decoded before its children", "form",
                calls.get(0));
        assertEquals("child", calls.get(1));
    }

}
