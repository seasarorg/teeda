package org.seasar.teeda.core.el.impl.commons;

import org.seasar.teeda.core.el.TeedaVariableResolver;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestCommonsELParser extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestCommonsELParser.class);
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
     * Constructor for TestCommonsELParser.
     * @param arg0
     */
    public TestCommonsELParser(String arg0) {
        super(arg0);
    }

    public void testParse(){
        getContainer().register(new Hoge(), "hoge");
    	CommonsELParser parser = new CommonsELParser();
    	Object o = parser.parse("#{hoge.name}");
    	MockApplication app = getApplication();
    	app.setVariableResolver(new TeedaVariableResolver());
    	Object obj = parser.getExpressionProcessor().evaluate(getFacesContext(), o);
    	assertEquals("foo", obj);
    }
    
    public static class Hoge{
    	public String name = "foo";
    	public String getName(){
    		return name;
    	}
    }
}
