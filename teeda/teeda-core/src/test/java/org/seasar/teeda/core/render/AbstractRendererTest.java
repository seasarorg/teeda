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
package org.seasar.teeda.core.render;

import java.util.Map;

import javax.faces.component.UIComponentBase;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.render.RendererTest;

/**
 * @author manhole
 */
public class AbstractRendererTest extends RendererTest {

    private AbstractRenderer renderer = new AbstractRenderer() {
    };

    public void testSplitByComma() throws Exception {
        // ## Arrange ##
        // ## Act ##
        String[] result = renderer.splitByComma("a, b  ,c , d");

        // ## Assert ##
        assertEquals(4, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals("d", result[3]);
    }

    public void testGetAllAttributesAndProperties_setIgnore() throws Exception {
        MockNoComponent component = new MockNoComponent();
        component.setHoge("aaa");
        IgnoreAttribute ignore = new IgnoreAttribute();
        ignore.addAttributeName("hoge");
        ignore.addAttributeName("id");
        Map map = renderer.getAllAttributesAndProperties(component, ignore);
        assertTrue(map.size() == 0);
    }

    public static class MockNoComponent extends UIComponentBase {

        private String hoge;

        public String getHoge() {
            return hoge;
        }

        public void setHoge(String hoge) {
            this.hoge = hoge;
        }

        public String getFamily() {
            return "none";
        }

    }

}
