package org.seasar.teeda.core.util;

import java.util.Vector;

import org.seasar.teeda.core.unit.ExceptionAssert;

import junit.framework.TestCase;

public class EnumerationIteratorTest extends TestCase {

    public void testEnumerationIterator() {
        Vector vector = new Vector();
        vector.add("a");
        EnumerationIterator itr = new EnumerationIterator(vector.elements());
        assertTrue(itr.hasNext());
        assertEquals("a", itr.next());
        try {
            itr.remove();
            fail();
        } catch (UnsupportedOperationException expected) {
            ExceptionAssert.assertMessageExist(expected);
        }
    }

}
