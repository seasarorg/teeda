package javax.faces.component;

import junit.framework.TestCase;


public class TestArrayIterator_ extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestArrayIterator_.class);
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
     * Constructor for TestArrayIterator_.
     * @param arg0
     */
    public TestArrayIterator_(String arg0) {
        super(arg0);
    }
    
    public void testIterate(){
        Object[] o = new Object[]{"a", "b", "c"};
        ArrayIterator_ itr = new ArrayIterator_(o);
        for(int i = 0;itr.hasNext();i++){
            String s = (String)itr.next();
            assertEquals(o[i], s);
        }
    }
}
