package org.seasar.teeda.core.util;

import junit.framework.TestCase;

public class ArrayUtilTest extends TestCase {

    public void testIsEmpty() {
        assertTrue(ArrayUtil.isEmpty(null));
        assertTrue(ArrayUtil.isEmpty(new Object[] {}));
        assertFalse(ArrayUtil.isEmpty(new Object[] { "" }));
        assertFalse(ArrayUtil.isEmpty(new Object[] { "aaa" }));
    }

    public void testEqualsIgnoreSequence() throws Exception {
        assertEquals(true, ArrayUtil.equalsIgnoreSequence(new Object[] { "1" },
                new Object[] { "1" }));
        assertEquals(true, ArrayUtil.equalsIgnoreSequence(new Object[] { "1",
                "2", "3" }, new Object[] { "2", "3", "1" }));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(
                new Object[] { "1" }, new Object[] { "2" }));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(
                new Object[] { "1" }, new Object[] {}));
        assertEquals(false, ArrayUtil.equalsIgnoreSequence(
                new Object[] { new Integer("1") }, new Object[] { "1" }));
    }

}
