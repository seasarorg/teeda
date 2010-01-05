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
package org.seasar.teeda.core.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.exception.NoEditableValueHolderRuntimeException;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithEditableValueHolder;
import org.seasar.teeda.core.mock.NullExternalContext;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class DecodeUtilTest extends TestCase {

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        MockUIComponentBase component = new MockUIComponentBaseWithEditableValueHolder();
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
        DecodeUtil.decode(context, component);

        // ## Assert ##
        assertEquals("bar", ((EditableValueHolder) component)
                .getSubmittedValue());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        MockUIComponentBase component = new MockUIComponentBaseWithEditableValueHolder();
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
        DecodeUtil.decode(context, component);

        // ## Assert ##
        assertEquals(null, ((EditableValueHolder) component)
                .getSubmittedValue());
    }

    public void testDecode_WithNullContext() throws Exception {
        try {
            DecodeUtil.decode(null,
                    new MockUIComponentBaseWithEditableValueHolder());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecode_WithNullComponent() throws Exception {
        try {
            DecodeUtil.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecode_WithNoEditableValueHolder() throws Exception {
        try {
            DecodeUtil.decode(new NullFacesContext(), new NullUIComponent());
            fail();
        } catch (NoEditableValueHolderRuntimeException expected) {
            ExceptionAssert.assertMessageExist(expected);
        }
    }

    public void testDecodeMany_WithNullContext() throws Exception {
        try {
            DecodeUtil.decodeMany(null,
                    new MockUIComponentBaseWithEditableValueHolder());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecodeMany_WithNullComponent() throws Exception {
        try {
            DecodeUtil.decodeMany(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testDecodeMany_WithNoEditableValueHolder() throws Exception {
        try {
            DecodeUtil
                    .decodeMany(new NullFacesContext(), new NullUIComponent());
            fail();
        } catch (NoEditableValueHolderRuntimeException expected) {
            ExceptionAssert.assertMessageExist(expected);
        }
    }

}
