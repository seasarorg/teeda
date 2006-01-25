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
