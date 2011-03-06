/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class LoadBundleTagTest extends TeedaTestCase {

    /**
     * Constructor for LoadBundleTest.
     * @param name
     */
    public LoadBundleTagTest(String name) {
        super(name);
    }

    public void testLoadBundle() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.setDefaultLocale(Locale.JAPAN);
        getFacesContext().setApplication(mockApp);
        LoadBundleTag tag = new LoadBundleTag();
        tag.setBasename("org.seasar.teeda.core.taglib.core.TestMessages");
        tag.setVar("messages");
        tag.doStartTag();
        assertEquals(((Map) getFacesContext().getExternalContext()
                .getRequestMap().get("messages")).get("aaa"), "AAA");
        assertEquals(((Map) getFacesContext().getExternalContext()
                .getRequestMap().get("messages")).get("bbb"), "BBB");
        assertEquals(((Map) getFacesContext().getExternalContext()
                .getRequestMap().get("messages")).get("xxx"), "???xxx???");
    }

    public void testLoadBndle_setBadBaseName() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.setDefaultLocale(Locale.JAPAN);
        getFacesContext().setApplication(mockApp);
        LoadBundleTag tag = new LoadBundleTag();
        tag.setBasename("hogehoge");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
    }

    public void testLoadBundleMap() throws Exception {
        boolean gotException = false;
        Object key = "aaa";
        Object value = "AAA";
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.setDefaultLocale(Locale.JAPAN);
        getFacesContext().setApplication(mockApp);
        LoadBundleTag tag = new LoadBundleTag();
        tag.setBasename("org.seasar.teeda.core.taglib.core.TestMessages");
        tag.setVar("messages");
        tag.doStartTag();
        Map testMap = (Map) getFacesContext().getExternalContext()
                .getRequestMap().get("messages");
        LoadBundleTag tag2 = new LoadBundleTag();
        tag2.setBasename("org.seasar.teeda.core.taglib.core.TestMessages");
        tag2.setVar("messages");
        tag2.doStartTag();
        Map testMap2 = (Map) getFacesContext().getExternalContext()
                .getRequestMap().get("messages");
        try {
            testMap.clear();
            fail();
        } catch (UnsupportedOperationException e) {
            gotException = true;
        }
        assertTrue(gotException);

        assertTrue(testMap.containsKey(key));
        assertTrue(testMap.containsValue(value));
        assertTrue(testMap.entrySet().equals(testMap2.entrySet()));
        assertTrue(testMap.equals(testMap2));
        assertEquals(testMap.get(key), value);
        assertTrue(testMap.hashCode() == testMap2.hashCode());
        assertFalse(testMap.isEmpty());
        assertTrue(testMap.keySet().contains(key));
        try {
            testMap.put(key, value);
            fail();
        } catch (UnsupportedOperationException e) {
            gotException = true;
        }
        assertTrue(gotException);

        gotException = false;
        try {
            testMap.putAll(new HashMap());
            fail();
        } catch (UnsupportedOperationException e) {
            gotException = true;
        }
        assertTrue(gotException);

        gotException = false;
        try {
            testMap.remove(key);
            fail();
        } catch (UnsupportedOperationException e) {
            gotException = true;
        }
        assertTrue(gotException);

        assertTrue(testMap.size() == 2);
        assertTrue(testMap.values().contains(value));
    }

    public void testRelease() throws Exception {
        // # Arrange #
        LoadBundleTag tag = new LoadBundleTag();
        tag.setBasename("org.seasar.teeda.core.taglib.core.TestMessages");
        tag.setVar("messages");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getBasename());
        assertEquals(null, tag.getVar());
    }

}