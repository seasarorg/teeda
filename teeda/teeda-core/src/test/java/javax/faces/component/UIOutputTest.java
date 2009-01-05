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

import javax.faces.convert.Converter;

import org.seasar.teeda.core.convert.NullConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author shot
 * @author manhole
 */
public class UIOutputTest extends UIComponentBaseTest {

    public final void testSetGetConverter() {
        UIOutput output = createUIOutput();
        Converter converter = new NullConverter();
        output.setConverter(converter);
        assertSame(converter, output.getConverter());
    }

    public final void testSetGetConverter_ValueBinding() {
        UIOutput output = createUIOutput();
        Converter converter = new NullConverter();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), converter);
        output.setValueBinding("converter", vb);
        assertSame(converter, output.getConverter());
    }

    public final void testSetValueGetLocalValue() {
        UIOutput output = createUIOutput();
        output.setValue("aaa");
        assertEquals("aaa", output.getLocalValue());
    }

    public final void testSetGetValue() {
        UIOutput output = createUIOutput();
        output.setValue("aaa");
        assertEquals("aaa", output.getValue());
    }

    public final void testSetGetValue_ValueBinding() {
        UIOutput output = createUIOutput();
        MockValueBinding vb = new MockValueBinding();
        final MockFacesContext context = getFacesContext();
        vb.setValue(context, "bbb");
        output.setValueBinding("value", vb);
        assertEquals("bbb", output.getValue());
        assertEquals("bbb", output.getValueBinding("value").getValue(context));
    }

    private UIOutput createUIOutput() {
        return (UIOutput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIOutput();
    }

}
