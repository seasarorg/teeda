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
package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TSelectOneRadioTag;

public class SelectOneRadioFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new SelectOneRadioFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectOneRadio", TSelectOneRadioTag.class);
    }

    //<span id="hoge"><input type="radio" name="hoge"/></span>
    public void testIsMatch_ok1() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "radio");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertTrue(factory.isMatch(parent, pageDesc, actionDesc));
    }

    //<span id="hoge"><input type="radio" name="hoge"/><input type="radio" name="hoge"/></span>
    public void testIsMatch_ok2() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "radio");
        map2.put("value", "aaa");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        Map map3 = new HashMap();
        map3.put("name", "hoge");
        map3.put("type", "radio");
        map3.put("value", "bbb");
        ElementNode child2 = createElementNode("input", map3);
        parent.addElement(child2);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertTrue(factory.isMatch(parent, pageDesc, actionDesc));
    }

    //<span><input type="radio" name="hoge"/></span>
    public void testIsMatch_ng1() throws Exception {
        Map map = new HashMap();
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "radio");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertFalse(factory.isMatch(parent, pageDesc, actionDesc));
    }

    //<span id="hoge"><input type="radio" /></span>
    public void testIsMatch_ng2() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("type", "radio");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertFalse(factory.isMatch(parent, pageDesc, actionDesc));
    }

    //<span id="hoge"><input type="radio" name="foo"/></span>
    public void testIsMatch_ng3() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("name", "foo");
        map2.put("type", "radio");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertFalse(factory.isMatch(parent, pageDesc, actionDesc));
    }

    //<span id="hoge"><input type="text" name="hoge"/></span>
    public void testIsMatch_ng4() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "text");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertFalse(factory.isMatch(parent, pageDesc, actionDesc));
    }

    //<span id="hoge"><input type="radio" name="hoge"/><span /></span>
    public void testIsMatch_ng5() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        Map map2 = new HashMap();
        map2.put("id", "hoge");
        map2.put("type", "text");
        ElementNode child = createElementNode("input", map2);
        parent.addElement(child);

        //it's unacceptable.
        ElementNode child2 = createElementNode("span", new HashMap());
        parent.addElement(child2);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertFalse(factory.isMatch(parent, pageDesc, actionDesc));
    }

    /*
     * 子要素はtext node以外であること
     */
    public void testIsMatch_ng6() throws Exception {
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNode parent = createElementNode("span", map);

        parent.addText("aaaaa");
        parent.endElement();

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");
        assertFalse(factory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        Map properties = new HashMap();
        properties.put("id", "hoge");
        ElementNode elementNode = createElementNode("span", properties);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "radio");
        map2.put("value", "aaa");
        ElementNode childNode = createElementNode("input", map2);
        elementNode.addElement(childNode);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor parentProcessor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", parentProcessor);
        assertEquals("2", TSelectOneRadioTag.class, parentProcessor
                .getTagClass());
        assertEquals("3", "#{fooPage.hoge}", parentProcessor
                .getProperty("value"));
        assertEquals("4", "#{fooPage.hogeItems}", parentProcessor
                .getProperty("items"));
    }
}