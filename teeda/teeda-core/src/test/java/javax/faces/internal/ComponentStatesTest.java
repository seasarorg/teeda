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
package javax.faces.internal;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIInput;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ComponentStatesTest extends TeedaTestCase {

    public void testSaveAndRestore() throws Exception {
        ComponentStates states = new ComponentStates();
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        MockUIInput input = new MockUIInput() {

            public String getClientId(FacesContext context) {
                return "hoge";
            }

        };
        input.setValue("aaa");
        input.setSubmittedValue("bbb");
        root.getChildren().add(input);
        states.saveDescendantComponentStates(context, root);

        UIViewRoot root2 = new UIViewRoot();
        root2.getChildren().add(new MockUIInput() {
            public String getClientId(FacesContext context) {
                return "hoge";
            }
        });
        states.restoreDescendantState(context, root2);

        MockUIInput child = (MockUIInput) root2.getChildren().get(0);
        assertEquals("aaa", child.getValue());
        assertEquals("bbb", child.getSubmittedValue());
    }

}
