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
package org.seasar.teeda.core.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author manhole
 */
public class RendererUtilTest extends TestCase {

    public void testShouldRenderIdAttribute_True() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponent();
        component.setId(UIViewRoot.UNIQUE_ID_PREFIX + "aaa");

        // ## Act & Assert ##
        assertEquals(false, RendererUtil.shouldRenderIdAttribute(component));
    }

    public void testShouldRenderIdAttribute_False() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponent();
        component.setId("aaa");

        // ## Act & Assert ##
        assertEquals(true, RendererUtil.shouldRenderIdAttribute(component));
    }

    public void testShouldRenderIdAttribute_False_IdIsNull() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponent();
        component.setId(null);

        // ## Act & Assert ##
        assertEquals(false, RendererUtil.shouldRenderIdAttribute(component));
    }

    public void testIsDefaultValue() throws Exception {
        assertEquals(true, RendererUtil.isDefaultAttributeValue(null));

        assertEquals(true, RendererUtil.isDefaultAttributeValue(Boolean.FALSE));
        assertEquals(true, RendererUtil.isDefaultAttributeValue(new Integer(
                Integer.MIN_VALUE)));

        assertEquals(false, RendererUtil.isDefaultAttributeValue(Boolean.TRUE));
        assertEquals(false, RendererUtil
                .isDefaultAttributeValue(new Integer(0)));
        assertEquals(false, RendererUtil
                .isDefaultAttributeValue(new Integer(-1)));
        assertEquals(false, RendererUtil
                .isDefaultAttributeValue(new Integer(1)));
        assertEquals(false, RendererUtil.isDefaultAttributeValue(new Integer(
                Integer.MAX_VALUE)));

        assertEquals(false, RendererUtil.isDefaultAttributeValue(""));

    }

    // TODO more tests

}
