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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInputTest;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ConverterResource;

import org.seasar.teeda.core.convert.NullConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 * @author shot
 */
public class HtmlInputHiddenTest extends UIInputTest {

    protected UIComponent createUIComponent() {
        return new HtmlInputHidden();
    }

    public void testProcessValidatorAndUpdateModelImmediately()
            throws Exception {
        HtmlInputHidden hidden = (HtmlInputHidden) createUIComponent();
        MockFacesContext context = getFacesContext();
        MockValueBinding vb = new MockValueBinding("#{aaa}");
        ConverterResource.addConverter("#{aaa}", new NullConverter());
        vb.setValue(context, "aaa");
        hidden.setValueBinding("value", vb);
        hidden.setValue("bbb");
        hidden.setSubmittedValue("bbb");
        hidden.setValid(true);
        
        hidden.processValidators(context);
        
        ValueBinding vbRes = hidden.getValueBinding("value");
        String s = (String) vbRes.getValue(context);
        assertEquals("bbb", s);
        assertTrue(hidden.isLocalValueSet());
        assertNotNull(hidden.getValue());
    }
    
    public static class Aaa {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
    }
}
