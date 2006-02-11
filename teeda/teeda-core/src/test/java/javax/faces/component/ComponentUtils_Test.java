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

import java.util.LinkedList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ComponentUtils_Test extends TeedaTestCase {

    private static final String DECODE = "decode";

    private static final String UPDATE = "update";

    private static final String VALIDATE = "validate";

    public void testProcessAppropriatePhaseAction_callSuccess()
            throws Exception {
        List notify = new LinkedList();
        MockNotifyUIComponent component = new MockNotifyUIComponent(notify);
        ComponentUtils_.processAppropriatePhaseAction(getFacesContext(),
                component, PhaseId.APPLY_REQUEST_VALUES);
        ComponentUtils_.processAppropriatePhaseAction(getFacesContext(),
                component, PhaseId.UPDATE_MODEL_VALUES);
        ComponentUtils_.processAppropriatePhaseAction(getFacesContext(),
                component, PhaseId.PROCESS_VALIDATIONS);
        assertEquals(DECODE, notify.get(0));
        assertEquals(UPDATE, notify.get(1));
        assertEquals(VALIDATE, notify.get(2));
    }

    public void testProcessAppropriatePhaseAction_illegalAragument()
            throws Exception {
        List notify = new LinkedList();
        MockNotifyUIComponent component = new MockNotifyUIComponent(notify);
        try {
            ComponentUtils_.processAppropriatePhaseAction(getFacesContext(),
                    component, PhaseId.INVOKE_APPLICATION);
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }

    }

    public void testGetValueBindingValue_getValue() throws Exception {
        MockUIComponent component = new MockUIComponent();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "HOGE");
        component.setValueBinding("hoge", vb);
        Object o = ComponentUtils_.getValueBindingValue(component, "hoge");
        assertEquals("HOGE", o);
    }

    public void testGetValueBindingValue_getNull() throws Exception {
        MockUIComponent component = new MockUIComponent();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "HOGE");
        component.setValueBinding("foo", vb);
        Object o = ComponentUtils_.getValueBindingValue(component, "hoge");
        assertNull(o);
    }

    public void testGetValueBindingType_getType() throws Exception {
        MockUIComponent component = new MockUIComponent();
        MockValueBinding vb = new MockValueBinding();
        vb.setType(MockUIComponent.class);
        component.setValueBinding("foo", vb);
        Class type = ComponentUtils_.getValueBindingType(component, "foo");
        assertEquals(MockUIComponent.class, type);
    }

    public void testGetValueBindingType_getNull() throws Exception {
        MockUIComponent component = new MockUIComponent();
        MockValueBinding vb = new MockValueBinding();
        vb.setType(MockUIComponent.class);
        component.setValueBinding("foo", vb);
        Class type = ComponentUtils_.getValueBindingType(component, "bar");
        assertNull(type);
    }

    public void testConvertToBoolean() throws Exception {
        assertEquals(Boolean.TRUE, ComponentUtils_.convertToBoolean(true));
        assertEquals(Boolean.FALSE, ComponentUtils_.convertToBoolean(false));
    }

    public void testConvertToPrimitiveBoolean() throws Exception {
        assertTrue(ComponentUtils_.convertToPrimitiveBoolean(Boolean.TRUE));
        assertFalse(ComponentUtils_.convertToPrimitiveBoolean(Boolean.FALSE));
        assertFalse(ComponentUtils_.convertToPrimitiveBoolean(null));
        assertFalse(ComponentUtils_.convertToPrimitiveBoolean("hoge"));
    }

    public void testCreateConverter() throws Exception {
        getApplication().addConverter(MockUIComponent.class,
                MockConverter.class.getName());
        Converter c = ComponentUtils_.createConverter(getFacesContext(),
                MockUIComponent.class);
        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testIsPerformNoConversion() throws Exception {
        assertTrue(ComponentUtils_.isPerformNoConversion(null));
        assertTrue(ComponentUtils_.isPerformNoConversion(String.class));
        assertTrue(ComponentUtils_.isPerformNoConversion(Object.class));
        assertFalse(ComponentUtils_.isPerformNoConversion(MockUIComponent.class));
    }
    
    public void testCalculateLocale() throws Exception {
        getApplication().getViewHandler();
    }
    
    private static class MockNotifyUIComponent extends MockUIComponent {

        private List notify_;

        public MockNotifyUIComponent(List notify) {
            notify_ = notify;
        }

        public void processDecodes(FacesContext context) {
            notify_.add(DECODE);
        }

        public void processUpdates(FacesContext context) {
            notify_.add(UPDATE);
        }

        public void processValidators(FacesContext context) {
            notify_.add(VALIDATE);
        }

    }
}
