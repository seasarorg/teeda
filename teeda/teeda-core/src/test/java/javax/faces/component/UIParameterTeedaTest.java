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

import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 * @author shot
 */
public class UIParameterTeedaTest extends UIComponentBaseTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        createUIParameter();
        UIParameter param1 = createUIParameter();
        param1.setName("name");
        param1.setValue("value");
        MockFacesContext context = getFacesContext();
        Object state = param1.saveState(context);

        UIParameter param2 = createUIParameter();
        param2.restoreState(context, state);

        assertEquals(param1.getName(), param2.getName());
        assertEquals(param1.getValue(), param2.getValue());
    }

    private UIParameter createUIParameter() {
        return (UIParameter) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIParameter();
    }

}
