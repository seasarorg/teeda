package javax.faces.component;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.mock.MockExternalContext;
import javax.faces.mock.MockFacesContext;
import javax.faces.mock.MockUIComponent;
import javax.faces.validator.Validator;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import junit.framework.TestCase;


public class TestFacesMessageUtils_ extends TestCase {

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
        Application app = new ApplicationImpl();
        app.setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = new MockFacesContext(app);
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
        Application app = new ApplicationImpl();
        app.setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = new MockFacesContext(app);
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
        Application app = new ApplicationImpl();
        app.setMessageBundle("javax.faces.component.NoFoundMessages");
        MockExternalContext extContext = new MockExternalContext(new EmptyServletContext());
        MockFacesContext context = new MockFacesContext(app, extContext);
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
    
    private static class ApplicationImpl extends Application{

        private String bundle_ = null;
        public ActionListener getActionListener() {
            return null;
        }

        public void setActionListener(ActionListener listener) {
        }

        public Locale getDefaultLocale() {
            return null;
        }

        public void setDefaultLocale(Locale locale) {
        }

        public String getDefaultRenderKitId() {
            return null;
        }

        public void setDefaultRenderKitId(String renderKitId) {
        }

        public String getMessageBundle() {
            return bundle_;
        }

        public void setMessageBundle(String bundle) {
            bundle_ = bundle;
        }

        public NavigationHandler getNavigationHandler() {
            return null;
        }

        public void setNavigationHandler(NavigationHandler handler) {
        }

        public PropertyResolver getPropertyResolver() {
            return null;
        }

        public void setPropertyResolver(PropertyResolver resolver) {
        }

        public VariableResolver getVariableResolver() {
            return null;
        }

        public void setVariableResolver(VariableResolver resolver) {
        }

        public ViewHandler getViewHandler() {
            return null;
        }

        public void setViewHandler(ViewHandler handler) {
        }

        public StateManager getStateManager() {
            return null;
        }

        public void setStateManager(StateManager manager) {
        }

        public void addComponent(String componentType, String componentClassName) {
        }

        public UIComponent createComponent(String componentType) throws FacesException {
            return null;
        }

        public UIComponent createComponent(ValueBinding componentBinding, FacesContext context, String componentType) throws FacesException {
            return null;
        }

        public Iterator getComponentTypes() {
            return null;
        }

        public void addConverter(String converterId, String converterClass) {
        }

        public void addConverter(Class targetClass, String converterClass) {
        }

        public Converter createConverter(String converterId) {
            return null;
        }

        public Converter createConverter(Class targetClass) {
            return null;
        }

        public Iterator getConverterIds() {
            return null;
        }

        public Iterator getConverterTypes() {
            return null;
        }

        public MethodBinding createMethodBinding(String ref, Class[] params) throws ReferenceSyntaxException {
            return null;
        }

        public Iterator getSupportedLocales() {
            return null;
        }

        public void setSupportedLocales(Collection locales) {
        }

        public void addValidator(String validatorId, String validatorClass) {
        }

        public Validator createValidator(String validatorId) throws FacesException {
            return null;
        }

        public Iterator getValidatorIds() {
            return null;
        }

        public ValueBinding createValueBinding(String ref) throws ReferenceSyntaxException {
            return null;
        }
        
    };

    private static class EmptyServletContext implements ServletContext{

        public ServletContext getContext(String arg0) {
            return null;
        }

        public int getMajorVersion() {
            return 0;
        }

        public int getMinorVersion() {
            return 0;
        }

        public String getMimeType(String arg0) {
            return null;
        }

        public Set getResourcePaths(String arg0) {
            return null;
        }

        public URL getResource(String arg0) throws MalformedURLException {
            return null;
        }

        public InputStream getResourceAsStream(String arg0) {
            return null;
        }

        public RequestDispatcher getRequestDispatcher(String arg0) {
            return null;
        }

        public RequestDispatcher getNamedDispatcher(String arg0) {
            return null;
        }

        public Servlet getServlet(String arg0) throws ServletException {
            return null;
        }

        public Enumeration getServlets() {
            return null;
        }

        public Enumeration getServletNames() {
            return null;
        }

        public void log(String arg0) {
        }

        public void log(Exception arg0, String arg1) {
        }

        public void log(String arg0, Throwable arg1) {
        }

        public String getRealPath(String arg0) {
            return null;
        }

        public String getServerInfo() {
            return null;
        }

        public String getInitParameter(String arg0) {
            return null;
        }

        public Enumeration getInitParameterNames() {
            return null;
        }

        public Object getAttribute(String arg0) {
            return null;
        }

        public Enumeration getAttributeNames() {
            return null;
        }

        public void setAttribute(String arg0, Object arg1) {
        }

        public void removeAttribute(String arg0) {
        }

        public String getServletContextName() {
            return null;
        }
        
    }
}
