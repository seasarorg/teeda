/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component;

import javax.faces.component.AbstractUIComponentTest;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContextImpl;

/**
 * @author shot
 *
 */
public class UITitleTest extends AbstractUIComponentTest {

    public void testStoreRestore() throws Exception {
        FacesContext context = new MockFacesContextImpl();
        UITitle title = (UITitle) createUIComponent();
        title.setDir("a");
        title.setLang("b");
        title.setDefaultKey("c");
        title.setPropertiesName("d");
        title.setDefaultPropertiesName("e");
        title.setTemplateValue("f");
        Object saveState = title.saveState(context);

        UITitle title2 = (UITitle) createUIComponent();
        title2.restoreState(context, saveState);
        assertEquals("a", title2.getDir());
        assertEquals("b", title2.getLang());
        assertEquals("c", title2.getDefaultKey());
        assertEquals("d", title2.getPropertiesName());
        assertEquals("e", title2.getDefaultPropertiesName());
        assertEquals("f", title2.getTemplateValue());
    }

    protected UIComponent createUIComponent() {
        return new UITitle();
    }

}
