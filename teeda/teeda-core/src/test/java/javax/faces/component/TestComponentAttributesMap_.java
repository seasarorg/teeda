package javax.faces.component;

import junit.framework.TestCase;


public class TestComponentAttributesMap_ extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestComponentAttributesMap_.class);
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
     * Constructor for TestComponentAttributesMap_.
     * @param arg0
     */
    public TestComponentAttributesMap_(String arg0) {
        super(arg0);
    }

    public void testGetComponentProperty(){
        MockUIComponent c = new MockUIComponent("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(c);
        assertNotNull(map.get("family"));
        assertEquals("mock", map.get("family"));
    }
    
    public void testPutComponentProperty(){
        MockUIComponent c = new MockUIComponent("mock");
        ComponentAttributesMap_ map = new ComponentAttributesMap_(c);
        assertEquals("mock", map.get("family"));
        c.setFamily("mock2");
        map.put("family", c);
        assertEquals("mock2", map.get("family"));
    }
    
    //TODO more test
    
    
    
    public static class MockUIComponent extends UIComponentBase{

        private String familyName_;
        public MockUIComponent(String familyName){
            familyName_ = familyName;
        }
        
        public void setFamily(String familyName){
            familyName_ = familyName;
        }
        
        public String getFamily() {
            return familyName_;
        }
        
    }
}
