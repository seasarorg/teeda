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

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class UIOutputTeedaTest extends UIComponentBaseTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        UIOutput output1 = createUIOutput();
        output1.setConverter(new MockConverter());
        output1.setValue("foo value");
        MockFacesContext context = getFacesContext();
        Object state = output1.saveState(context);

        UIOutput output2 = createUIOutput();
        output2.restoreState(context, state);

        ObjectAssert.assertInstanceOf(MockConverter.class, output2
                .getConverter());
        assertEquals(output1.getValue(), output2.getValue());
    }

    private UIOutput createUIOutput() {
        return (UIOutput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIOutput();
    }

}
