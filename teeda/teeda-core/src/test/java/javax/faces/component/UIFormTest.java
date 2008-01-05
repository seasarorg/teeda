/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author manhole
 */
public class UIFormTest extends UIComponentBaseTest {

    public void testSetGetSubmitted() throws Exception {
        // ## Arrange ##
        UIForm form = createUIForm();

        // ## Act & Assert ##
        assertEquals("default is false", false, form.isSubmitted());
        form.setSubmitted(true);
        assertEquals(true, form.isSubmitted());
    }

    public void testProcessValidators_SubmittedTrue() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIForm form = createUIForm();
        form.setSubmitted(true);
        UIComponentBase child = new MockUIComponentBase() {
            public void processValidators(FacesContext context) {
                calls[0] = true;
            }
        };
        form.getChildren().add(child);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        form.processValidators(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testProcessValidators_SubmittedFalse() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIForm form = createUIForm();
        form.setSubmitted(false);
        UIComponentBase child = new MockUIComponentBase() {
            public void processValidators(FacesContext context) {
                calls[0] = true;
            }
        };
        form.getChildren().add(child);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        form.processValidators(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
    }

    public void testProcessUpdates_SubmittedTrue() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIForm form = createUIForm();
        form.setSubmitted(true);
        UIComponentBase child = new MockUIComponentBase() {
            public void processUpdates(FacesContext context) {
                calls[0] = true;
            }
        };
        form.getChildren().add(child);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        form.processUpdates(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testProcessUpdates_SubmittedFalse() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIForm form = createUIForm();
        form.setSubmitted(false);
        UIComponentBase child = new MockUIComponentBase() {
            public void processUpdates(FacesContext context) {
                calls[0] = true;
            }
        };
        form.getChildren().add(child);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        form.processUpdates(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
    }

    private UIForm createUIForm() {
        return (UIForm) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIForm();
    }

}
