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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabelTeedaTest;
import javax.faces.context.FacesContext;

/**
 * @author manhole
 * 
 */
public class THtmlOutputLabelTeedaTest extends HtmlOutputLabelTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        // ## Arrange ##
        final THtmlOutputLabel label1 = createTHtmlOutputLabel();
        {
            label1.setKey("aaa");
            label1.setDefaultKey("bbb");
            label1.setPropertiesName("ccc");
            label1.setDefaultPropertiesName("ddd");
            label1.setTemplateValue("eee");
        }

        final FacesContext context = getFacesContext();

        // ## Act ##
        final Object decoded = serializeAndDeserialize(label1
                .saveState(context));
        final THtmlOutputLabel label2 = createTHtmlOutputLabel();
        label2.restoreState(context, decoded);

        // ## Assert ##
        assertEquals("aaa", label2.getKey());
        assertEquals("bbb", label2.getDefaultKey());
        assertEquals("ccc", label2.getPropertiesName());
        assertEquals("ddd", label2.getDefaultPropertiesName());
        assertEquals("eee", label2.getTemplateValue());
    }

    private THtmlOutputLabel createTHtmlOutputLabel() {
        return (THtmlOutputLabel) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new THtmlOutputLabel();
    }

}
