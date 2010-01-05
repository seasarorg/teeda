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
package org.seasar.teeda.core.config.faces.handler;

import java.util.Map;

import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.ListEntriesElement;
import org.seasar.teeda.core.config.faces.element.ListEntriesHolder;
import org.seasar.teeda.core.config.faces.element.impl.ManagedBeanElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.ManagedPropertyElementImpl;

/**
 * @author shot
 */
public class ListEntriesTagHandlerTest extends TagHandlerTestCase {

    /**
     * Constructor for ListEntriesTagHandlerTest.
     * 
     * @param name
     */
    public ListEntriesTagHandlerTest(String name) {
        super(name);
    }

    public void testListEntriesTagHandler_withManagedBeanElement()
            throws Exception {
        ListEntriesHolder holder = new ManagedBeanElementImpl();
        getContext().push(holder);
        ListEntriesTagHandler handler = new ListEntriesTagHandler();

        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "a");

        ListEntriesElement element = holder.getListEntries();
        assertNotNull(element);
    }

    public void testListEntriesTagHandler_withManagedPropertyElement()
            throws Exception {
        ListEntriesHolder holder = new ManagedPropertyElementImpl();
        getContext().push(holder);
        ListEntriesTagHandler handler = new ListEntriesTagHandler();

        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "a");

        ListEntriesElement element = holder.getListEntries();
        assertNotNull(element);
    }

    public void testListEntriesTagHandlerByXMLParse() throws Exception {
        FacesConfig config = parse("testListEntriesTagHandler.xml");
        Map mbs = config.getManagedBeanElements();
        ListEntriesHolder holder = (ListEntriesHolder) mbs.get("hoge");
        ListEntriesElement element = holder.getListEntries();
        assertNotNull(element);
        assertEquals("java.lang.String", element.getValueClass());
        assertEquals("aaa", element.getValues().get(0));
    }
}
