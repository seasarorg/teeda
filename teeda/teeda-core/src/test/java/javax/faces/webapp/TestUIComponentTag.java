package javax.faces.webapp;

import junit.framework.TestCase;


public class TestUIComponentTag extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestUIComponentTag.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestUIComponentTag.
     * @param arg0
     */
    public TestUIComponentTag(String arg0) {
        super(arg0);
    }

    public void testIsValueReference(){
        try{
            UIComponentTag.isValueReference(null);
            fail();
        }catch(NullPointerException e){
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
}
