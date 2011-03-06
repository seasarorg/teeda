/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

/**
 * @author manhole
 */
public class UIFormTeedaTest extends UIComponentBaseTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        // ## Arrange ##
        UIForm form1 = createUIForm();
        form1.setSubmitted(true);

        // ## Act ##
        FacesContext context = getFacesContext();
        final Object state = form1.saveState(context);

        UIForm form2 = createUIForm();
        form2.restoreState(context, serializeAndDeserialize(state));

        // ## Assert ##
        assertEquals("'setSubmitted' should not be saved.", false, form2
                .isSubmitted());
    }

    private UIForm createUIForm() {
        return (UIForm) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIForm();
    }

}
