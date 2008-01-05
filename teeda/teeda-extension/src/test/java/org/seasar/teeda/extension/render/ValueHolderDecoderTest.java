/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.render;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIOutput;
import javax.faces.component.ValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.NullExternalContext;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author shot
 */
public class ValueHolderDecoderTest extends TestCase {

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        MockUIOutput component = new MockUIOutput();
        component.setClientId("foo");

        final Map map = new HashMap();
        map.put("foo", "bar");

        FacesContext context = new NullFacesContext() {
            public ExternalContext getExternalContext() {
                return new NullExternalContext() {
                    public Map getRequestParameterMap() {
                        return map;
                    }
                };
            }
        };

        // ## Act ##
        ValueHolderDecoder decoder = new ValueHolderDecoder();
        decoder.decode(context, component);

        // ## Assert ##
        assertEquals("bar", ((ValueHolder) component).getValue());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        MockUIOutput component = new MockUIOutput();
        component.setClientId("foo");

        final Map map = new HashMap();
        map.put("foo1", "bar");

        FacesContext context = new NullFacesContext() {
            public ExternalContext getExternalContext() {
                return new NullExternalContext() {
                    public Map getRequestParameterMap() {
                        return map;
                    }
                };
            }
        };

        // ## Act ##
        ValueHolderDecoder decoder = new ValueHolderDecoder();
        decoder.decode(context, component);

        // ## Assert ##
        assertEquals(null, ((ValueHolder) component).getValue());
    }

    public void testDecode_WithNullContext() throws Exception {
        try {
            ValueHolderDecoder decoder = new ValueHolderDecoder();
            decoder.decode(null, new MockUIOutput());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecode_WithNullComponent() throws Exception {
        try {
            ValueHolderDecoder decoder = new ValueHolderDecoder();
            decoder.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecode_WithNoValueHolder() throws Exception {
        try {
            ValueHolderDecoder decoder = new ValueHolderDecoder();
            decoder.decode(new NullFacesContext(), new NullUIComponent());
            fail();
        } catch (RuntimeException expected) {
            assertTrue(true);
        }
    }

    public void testDecodeMany_WithNullContext() throws Exception {
        try {
            ValueHolderDecoder decoder = new ValueHolderDecoder();
            decoder.decodeMany(null, new MockUIOutput());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecodeMany_WithNullComponent() throws Exception {
        try {
            ValueHolderDecoder decoder = new ValueHolderDecoder();
            decoder.decodeMany(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecodeMany_WithValueHolder() throws Exception {
        try {
            ValueHolderDecoder decoder = new ValueHolderDecoder();
            decoder.decodeMany(new NullFacesContext(), new NullUIComponent());
            fail();
        } catch (RuntimeException expected) {
        }
    }

    public static class MockUIOutput extends UIOutput {

        private String clientId;

        public void setId(String id) {
            super.setId(id);
            clientId = null;
        }

        public String getClientId(FacesContext context) {
            if (clientId != null) {
                return clientId;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getFamily() {
            return "mock";
        }

    }
}
