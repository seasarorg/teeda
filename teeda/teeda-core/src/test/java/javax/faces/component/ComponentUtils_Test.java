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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.MockViewHandler;
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
        assertFalse(ComponentUtils_
                .isPerformNoConversion(MockUIComponent.class));
    }

    public void testCalculateLocale() throws Exception {
        MockViewHandler handler = getViewHandler();
        Locale org = handler.calculateLocale(getFacesContext());
        handler.setLocale(Locale.JAPANESE);
        setViewHandler(handler);
        Locale l = ComponentUtils_.calculateLocale(getFacesContext());
        assertEquals(Locale.JAPANESE, l);
        getViewHandler().setLocale(org);
    }

    public void testGetLocale() throws Exception {
        getFacesContext().getViewRoot().setLocale(Locale.CANADA);
        assertEquals(Locale.CANADA, ComponentUtils_
                .getLocale(getFacesContext()));
    }

    public void testIsLocaleShort_null() throws Exception {
        try {
            ComponentUtils_.isLocaleShort(null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testIsLocaleShort_short() throws Exception {
        assertTrue(ComponentUtils_.isLocaleShort("en"));
        assertTrue(ComponentUtils_.isLocaleShort("ja"));
    }

    public void testIsLocaleShort_longName() throws Exception {
        assertFalse(ComponentUtils_.isLocaleShort(Locale.US.toString()));
        assertFalse(ComponentUtils_.isLocaleShort(Locale.JAPAN.toString()));
    }

    public void testIsLocaleLong_null() throws Exception {
        try {
            ComponentUtils_.isLocaleLong(null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testIsLocaleLong_long() throws Exception {
        assertTrue(ComponentUtils_.isLocaleLong(Locale.US.toString()));
        assertTrue(ComponentUtils_.isLocaleLong(Locale.JAPAN.toString()));
    }

    public void testValueMatches_allValuesAreNull() throws Exception {
        SelectItem item = new SelectItem();
        List list = new ArrayList();
        list.add(item);
        assertTrue(ComponentUtils_.valueMatches(null, list.iterator()));
    }

    public void testValueMatches_allValuesAreNotNull() throws Exception {
        SelectItem item = new SelectItem();
        item.setValue("hoge");
        List list = new ArrayList();
        list.add(item);
        assertTrue(ComponentUtils_.valueMatches("hoge", list.iterator()));
    }

    public void testValueMatches_hasSelectItemGroup() throws Exception {
        SelectItemGroup group = new SelectItemGroup();
        SelectItem item = new SelectItem();
        item.setValue("aaa");
        group.setSelectItems(new SelectItem[] { item });
        List list = new ArrayList();
        list.add(group);
        assertTrue(ComponentUtils_.valueMatches("aaa", list.iterator()));
    }

    public void testIsObjectArray() throws Exception {
        assertTrue(ComponentUtils_.isObjectArray(new Object[] {}));
        assertFalse(ComponentUtils_.isObjectArray("hoge"));
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
