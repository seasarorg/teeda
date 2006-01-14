package org.seasar.teeda.core.util;

import junit.framework.TestCase;

public class ArrayUtilTest extends TestCase {

    public void testIsEmpty() {
        assertTrue(ArrayUtil.isEmpty(null));
        assertTrue(ArrayUtil.isEmpty(new Object[] {}));
        assertFalse(ArrayUtil.isEmpty(new Object[] { "" }));
        assertFalse(ArrayUtil.isEmpty(new Object[] { "aaa" }));
    }

}
