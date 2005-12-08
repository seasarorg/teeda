package javax.faces.component;

import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.FacesMessage;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestFacesMessageUtils_ extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestFacesMessageUtils_.class);
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
     * Constructor for TestFacesMessageUtils_.
     * @param arg0
     */
    public TestFacesMessageUtils_(String arg0) {
        super(arg0);
    }

    public void testGetSimpleErrorMessage() {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("a");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtils_.addErrorMessage(context, component, "aaa");
        assertNotNull(context.getMessages("a"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage)itr.next();
        assertEquals("AAA", message.getSummary());
    }

    public void testGetParameterizedMessage(){
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("b");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtils_.addErrorMessage(context, component, "bbb", new Object[]{"B1","B2"});
        assertNotNull(context.getMessages("b"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage)itr.next();
        assertEquals("B1,B2", message.getSummary());
        assertEquals("B1,B2 detail", message.getDetail());
    }

    public void testApplicationResourceBundleMissing(){
        getApplication().setMessageBundle("javax.faces.component.NoFoundMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("c");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtils_.addErrorMessage(context, component, "ccc");
        assertNotNull(context.getMessages("c"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage)itr.next();
        assertEquals("CCC", message.getSummary());
    }
    
}
