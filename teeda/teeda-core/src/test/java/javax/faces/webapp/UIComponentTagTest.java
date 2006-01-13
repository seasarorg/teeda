package javax.faces.webapp;

import junit.framework.TestCase;

public class UIComponentTagTest extends TestCase {

    public void testIsValueReference() {
        try {
            UIComponentTag.isValueReference(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
        assertTrue(UIComponentTag.isValueReference("#{aaa}"));
        assertFalse(UIComponentTag.isValueReference(""));
        assertFalse(UIComponentTag.isValueReference("aaa"));
        assertFalse(UIComponentTag.isValueReference("#{"));
        assertFalse(UIComponentTag.isValueReference("}"));
        assertFalse(UIComponentTag.isValueReference("#}"));
        assertFalse(UIComponentTag.isValueReference("#{aaa"));
        assertFalse(UIComponentTag.isValueReference("#aaa}"));
        assertFalse(UIComponentTag.isValueReference("#a{aa}"));
    }

    // XXX more tests?

}
