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
package org.seasar.teeda.extension.html.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author higa
 * @author manhole
 */
public class ElementNodeImplTest extends TestCase {

    public void testToString() throws Exception {
        ElementNodeImpl n1 = new ElementNodeImpl(null, null, "hoge",
                new HashMap());
        assertEquals("<hoge></hoge>", n1.toString());

        Map props2 = new HashMap();
        props2.put("id", "aaa");
        ElementNodeImpl n2 = new ElementNodeImpl(null, null, "hoge", props2);
        assertEquals("<hoge id=\"aaa\"></hoge>", n2.toString());

        Map props3 = new HashMap();
        props3.put("id", "aaa");
        props3.put("type", "text");
        ElementNodeImpl n3 = new ElementNodeImpl(null, null, "input", props3);
        assertEquals("<input type=\"text\" id=\"aaa\" />", n3.toString());

        Map props4 = new HashMap();
        props4.put("id", "aaa");
        ElementNodeImpl n4 = new ElementNodeImpl(null, null, "hoge", props4);
        n4.addText("abc");
        n4.endElement();
        assertEquals("<hoge id=\"aaa\">abc</hoge>", n4.toString());

        Map props5 = new HashMap();
        props5.put("id", "aaa");
        ElementNodeImpl n5 = new ElementNodeImpl(null, null, "hoge", props5);
        n5.addText("abc");
        n5.addText("def");
        n5.endElement();
        assertEquals("<hoge id=\"aaa\">abcdef</hoge>", n5.toString());

        Map props6 = new HashMap();
        props6.put("id", "aaa");
        ElementNodeImpl n6 = new ElementNodeImpl(null, null, "hoge", props6);
        n6.addText("abc");
        Map props62 = new HashMap();
        props62.put("id", "bbb");
        ElementNodeImpl n62 = new ElementNodeImpl(null, null, "hoge2", props62);
        n6.addElement(n62);
        n6.addText("def");
        n6.endElement();
        assertEquals(
                "<hoge id=\"aaa\">abc<hoge2 id=\"bbb\"></hoge2>def</hoge>", n6
                        .toString());
    }

    public void testGetParent1() throws Exception {
        // ## Arrange ##
        ElementNodeImpl parent = new ElementNodeImpl(null, null, "parent",
                new HashMap());
        ElementNodeImpl child = new ElementNodeImpl(null, null, "child",
                new HashMap());

        // ## Act ##
        // ## Assert ##
        assertEquals(0, parent.getChildSize());
        assertEquals(null, child.getParent());
        parent.addElement(child);

        assertEquals(1, parent.getChildSize());
        assertEquals(parent, child.getParent());
    }

    public void testGetParent2() throws Exception {
        // ## Arrange ##
        ElementNodeImpl parent1 = new ElementNodeImpl(null, null, "parent1",
                new HashMap());
        ElementNodeImpl child = new ElementNodeImpl(null, null, "child",
                new HashMap());
        parent1.addElement(child);

        assertEquals(1, parent1.getChildSize());
        assertEquals(parent1, child.getParent());

        // ## Act ##
        ElementNodeImpl parent2 = new ElementNodeImpl(null, null, "parent2",
                new HashMap());
        parent2.addElement(child);

        // ## Assert ##
        assertEquals(0, parent1.getChildSize());
        assertEquals(1, parent2.getChildSize());
        assertEquals(parent2, child.getParent());
    }

    public void testGetId() throws Exception {
        HashMap map = new HashMap();
        map.put(JsfConstants.ID_ATTR, "aaa-1");
        ElementNodeImpl n1 = new ElementNodeImpl(null, null, "hoge", map);
        assertEquals("aaa", n1.getId());

        HashMap map2 = new HashMap();
        map2.put(JsfConstants.ID_ATTR, "aaa");
        ElementNodeImpl n2 = new ElementNodeImpl(null, null, "hoge", map2);
        assertEquals("aaa", n2.getId());

    }
}
