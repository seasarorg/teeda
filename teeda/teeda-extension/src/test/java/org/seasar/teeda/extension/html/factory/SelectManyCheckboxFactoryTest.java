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
import org.seasar.teeda.extension.html.factory.web.FooAction;
import org.seasar.teeda.extension.html.factory.web.FooPage;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TSelectManyCheckboxTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

public class SelectManyCheckboxFactoryTest extends TeedaExtensionTestCase {

    private MockTaglibManager taglibManager;

    protected void setUp() throws Exception {
        super.setUp();
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectManyCheckbox", TSelectManyCheckboxTag.class);
        taglibManager = getTaglibManager();
    }

    public void testIsMatch_ok1() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "checkbox");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ok2() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "checkbox");
        map2.put("value", "aaa");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        Map map3 = new HashMap();
        map3.put("name", "hoge");
        map3.put("type", "checkbox");
        map3.put("value", "bbb");
        ElementNodeImpl child2 = new ElementNodeImpl("input", map3);
        parent.addElement(child2);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ng1() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "checkbox");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertFalse(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ng2() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("type", "checkbox");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertFalse(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ng3() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("name", "foo");
        map2.put("type", "checkbox");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertFalse(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ng4() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "text");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertFalse(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ng5() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        Map map2 = new HashMap();
        map2.put("id", "hoge");
        map2.put("type", "text");
        ElementNodeImpl child = new ElementNodeImpl("input", map2);
        parent.addElement(child);

        ElementNodeImpl child2 = new ElementNodeImpl("span", new HashMap());
        parent.addElement(child2);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertFalse(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testIsMatch_ng6() throws Exception {
        SelectManyCheckboxFactory selectFactory = new SelectManyCheckboxFactory();
        Map map = new HashMap();
        map.put("id", "hoge");
        ElementNodeImpl parent = new ElementNodeImpl("span", map);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertFalse(selectFactory.isMatch(parent, pageDesc, actionDesc));
    }

    public void testCreateProcessor() throws Exception {
        // ## Arrange ##
        SelectManyCheckboxFactory factory = new SelectManyCheckboxFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "hoge");
        ElementNode elementNode = new ElementNodeImpl("span", properties);

        Map map2 = new HashMap();
        map2.put("name", "hoge");
        map2.put("type", "checkbox");
        map2.put("value", "aaa");
        ElementNodeImpl childNode = new ElementNodeImpl("input", map2);
        elementNode.addElement(childNode);

        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor parentProcessor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", parentProcessor);
        assertEquals("2", TSelectManyCheckboxTag.class, parentProcessor
                .getTagClass());
        assertEquals("3", "#{fooPage.hoge}", parentProcessor
                .getProperty("value"));
        assertEquals("4", "#{fooPage.hogeItems}", parentProcessor
                .getProperty("items"));
    }

}
