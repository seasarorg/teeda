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
package org.seasar.teeda.extension.util;

import java.util.ArrayList;
import java.util.Map;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.render.IncludedBody;

/**
 * @author koichik
 * 
 */
public class LayoutBuilderTest extends TeedaTestCase {

    public void testPopIncludedBody_listNull() throws Exception {
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        requestMap.put(LayoutBuilder.LIST_KEY, null);
        assertNull(LayoutBuilder.popIncludedBody(getFacesContext()));
    }

    public void testPopIncludedBody_indexNull() throws Exception {
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        requestMap.put(LayoutBuilder.LIST_KEY, new ArrayList());
        requestMap.put(LayoutBuilder.POP_INDEX_KEY, null);
        assertNull(LayoutBuilder.popIncludedBody(getFacesContext()));
    }

    public void testPopIncludedBody() throws Exception {
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        ArrayList list = new ArrayList();
        list.add(new IncludedBody("aaa", new ArrayList()));
        requestMap.put(LayoutBuilder.LIST_KEY, list);
        requestMap.put(LayoutBuilder.POP_INDEX_KEY, new Integer(0));
        IncludedBody popIncludedBody = LayoutBuilder
                .popIncludedBody(getFacesContext());
        assertNotNull(popIncludedBody);
        assertEquals("aaa", popIncludedBody.getViewId());
    }

}
