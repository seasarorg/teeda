/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package javax.faces.webapp;

import javax.faces.component.UIViewRoot;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class UIComponentTagTest extends TeedaTestCase {

    public void testIsValueReference() {
        try {
            UIComponentTag.isValueReference(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
        assertTrue(UIComponentTag.isValueReference("#{aaa}"));
        assertFalse(UIComponentTag.isValueReference(""));
        assertFalse(UIComponentTag.isValueReference("aaa"));
        assertFalse(UIComponentTag.isValueReference("#{"));
        assertFalse(UIComponentTag.isValueReference("}"));
        assertFalse(UIComponentTag.isValueReference("#}"));
        assertFalse(UIComponentTag.isValueReference("#{aaa"));
        assertFalse(UIComponentTag.isValueReference("#aaa}"));
        assertFalse(UIComponentTag.isValueReference("#a{aa}"));
    }

    public void testSetBinding_notValidExpression() throws Exception {
        TargetUIComponentTag tag = new TargetUIComponentTag();
        try {
            tag.setBinding("aaaa");
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }
    }

    public void testSetId_notStartsWithUniqueIdPrefix() throws Exception {
        TargetUIComponentTag tag = new TargetUIComponentTag();
        try {
            tag.setId(UIViewRoot.UNIQUE_ID_PREFIX);
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }
    }

    // XXX more tests?

    private static class TargetUIComponentTag extends UIComponentTag {

        public String getComponentType() {
            return "mock";
        }

        public String getRendererType() {
            return "mock";
        }

    }
}
