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
package org.seasar.teeda.extension.render.html.support;

import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

import junitx.framework.ArrayAssert;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class AdjustableDecoderTest extends TeedaTestCase {

    public void testDecode_clientIdWithHiphen() throws Exception {
        MockUIComponent2 c = new MockUIComponent2();
        c.setClientId("form:aaa-2");
        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("form:aaa",
                "a");
        AdjustableDecoder decoder = new AdjustableDecoder();
        decoder.decode(context, c);
        assertEquals("a", c.getSubmittedValue());
    }

    public void testDecode_requestParamKeyWithHiphen() throws Exception {
        MockUIComponent2 c = new MockUIComponent2();
        c.setClientId("form:aaa");
        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("form:aaa-2",
                "b");
        AdjustableDecoder decoder = new AdjustableDecoder();
        decoder.decode(context, c);
        assertEquals("b", c.getSubmittedValue());
    }

    public void testDecode_clientIdAndRequestParamWithHiphen() throws Exception {
        MockUIComponent2 c = new MockUIComponent2();
        c.setClientId("form:aaa-3");
        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("form:aaa-4",
                "C");
        AdjustableDecoder decoder = new AdjustableDecoder();
        decoder.decode(context, c);
        assertEquals("C", c.getSubmittedValue());
    }

    public void testDecodeMany_clientIdWithHiphen() throws Exception {
        MockUIComponent2 c = new MockUIComponent2();
        c.setClientId("form:aaa-2");
        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterValuesMap().put(
                "form:aaa", new String[] { "a" });
        AdjustableDecoder decoder = new AdjustableDecoder();
        decoder.decodeMany(context, c);
        ArrayAssert.assertEquals(new String[] { "a" }, (String[]) c
                .getSubmittedValue());
    }

    public static class MockUIComponent2 extends MockUIComponent implements
            EditableValueHolder {

        private String clientId;

        private Object submittedValue;

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientId(FacesContext context) {
            return clientId;
        }

        public void addValidator(Validator validator) {
        }

        public void addValueChangeListener(ValueChangeListener listener) {
        }

        public Object getSubmittedValue() {
            return submittedValue;
        }

        public MethodBinding getValidator() {
            return null;
        }

        public Validator[] getValidators() {
            return null;
        }

        public MethodBinding getValueChangeListener() {
            return null;
        }

        public ValueChangeListener[] getValueChangeListeners() {
            return null;
        }

        public boolean isImmediate() {
            return false;
        }

        public boolean isLocalValueSet() {
            return false;
        }

        public boolean isRequired() {
            return false;
        }

        public boolean isValid() {
            return false;
        }

        public void removeValidator(Validator validator) {
        }

        public void removeValueChangeListener(ValueChangeListener listener) {
        }

        public void setImmediate(boolean immediate) {
        }

        public void setLocalValueSet(boolean localValueSet) {
        }

        public void setRequired(boolean required) {
        }

        public void setSubmittedValue(Object submittedValue) {
            this.submittedValue = submittedValue;
        }

        public void setValid(boolean valid) {
        }

        public void setValidator(MethodBinding validatorBinding) {
        }

        public void setValueChangeListener(MethodBinding valueChangeMethod) {
        }

        public Converter getConverter() {
            return null;
        }

        public Object getLocalValue() {
            return null;
        }

        public Object getValue() {
            return null;
        }

        public void setConverter(Converter converter) {
        }

        public void setValue(Object value) {
        }

    }
}
