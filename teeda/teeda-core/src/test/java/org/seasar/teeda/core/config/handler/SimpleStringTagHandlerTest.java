package org.seasar.teeda.core.config.handler;

import java.util.List;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.FactoryElement;
import org.seasar.teeda.core.config.element.impl.ConverterElementImpl;


public class SimpleStringTagHandlerTest extends TagHandlerTestCase {

    private static final String PATH = 
        "org/seasar/teeda/core/config/handler/testSimpleStringTagHandler.xml";

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SimpleStringTagHandlerTest.class);
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

    public SimpleStringTagHandlerTest(String arg0) {
        super(arg0);
    }

    public void testEndTagHandlerContextString() {
        ConverterElement tag = new ConverterElementImpl();
        getContext().push(tag);
        SimpleStringTagHandler handler = 
            new SimpleStringTagHandler("converter-id");
        handler.end(getContext(), "id");
        ConverterElement result = (ConverterElement)getContext().pop();
        assertEquals("id", result.getConverterId());
    }

    public void testSimpleStringTagHandlerByXMLParse() throws Exception{
        FacesConfig facesConfig = parse("testSimpleStringTagHandler.xml");
        assertEquals(1, facesConfig.getFactoryElements().size());
        FactoryElement factory = (FactoryElement)facesConfig.getFactoryElements().get(0);
        List list = factory.getApplicationFactories();
        String target = (String)list.get(0);
        assertEquals("org.seasar.teeda.core.mock.MockApplicationFactory", target);
    }

}
