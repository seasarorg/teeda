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
package org.seasar.teeda.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class IteratorUtilTest extends TestCase {

    public void testGetIterator() {
        List list = new ArrayList();
        list.add("a");
        for (Iterator itr = IteratorUtil.getIterator(list); itr.hasNext();) {
            assertNotNull(itr);
            assertNotNull(itr.next());
        }
        list = null;
        Iterator itr = IteratorUtil.getIterator(list);
        assertNotNull(itr);
        for (; itr.hasNext();) {
            fail();
        }
    }

    public void testGetIterator2() {
        Map map = new HashMap();
        map.put("a", "A");
        for (Iterator itr = IteratorUtil.getEntryIterator(map); itr.hasNext();) {
            assertNotNull(itr);
            assertNotNull(itr.next());
        }
        map = null;
        Iterator itr = IteratorUtil.getEntryIterator(map);
        assertNotNull(itr);
        for (; itr.hasNext();) {
            fail();
        }
    }

}
