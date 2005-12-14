package org.seasar.teeda.core.unit;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextFactory;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockLifecycle;
import org.seasar.teeda.core.mock.MockLifecycleFactory;
import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockRenderKitFactory;


/**
 * @author Shinpei Ohtani(aka shot)
 * 
 * Set up JSF env for easy-testing JSF Application and view.
 */
public class TeedaTestCase extends S2FrameworkTestCase {

    private MockExternalContext externalContext;
    
    private MockApplication application;
    
    private MockFacesContext facesContext;
    
    private MockLifecycle lifecycle;
    
    private MockRenderKit renderKit;
    
    public TeedaTestCase(){
    }
    
    public TeedaTestCase(String name){
        super(name);
    }
    
    protected void setUpContainer() throws Throwable {
        super.setUpContainer();
        externalContext = new MockExternalContextImpl(getServletContext(), getRequest(), getResponse());
        application = new MockApplication();
        facesContext = new MockFacesContextImpl(externalContext, application);
        renderKit = new MockRenderKit();
        lifecycle = new MockLifecycle();
        initFactories();
        setFactories();
    }
        
    protected void initFactories(){
        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
        "org.seasar.teeda.core.mock.MockApplicationFactory");

       FactoryFinder.setFactory(FactoryFinder.FACES_CONTEXT_FACTORY,
       "org.seasar.teeda.core.mock.MockFacesContextFactory");

       FactoryFinder.setFactory(FactoryFinder.LIFECYCLE_FACTORY,
       "org.seasar.teeda.core.mock.MockLifecycleFactory");

       FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY,
       "org.seasar.teeda.core.mock.MockRenderKitFactory");
    }

    protected void setFactories(){
        MockApplicationFactory appFactory = 
            (MockApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        appFactory.setApplication(application);
        MockFacesContextFactory facesContextFactory = 
            (MockFacesContextFactory)FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        facesContextFactory.setFacesContext(facesContext);
        MockLifecycleFactory lifecycleFactory = 
            (MockLifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        lifecycleFactory.addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, lifecycle);
        MockRenderKitFactory renderKitFactory = 
            (MockRenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        renderKitFactory.addRenderKit(RenderKitFactory.HTML_BASIC_RENDER_KIT, renderKit);
    }
    
    protected void tearDownContainer() throws Throwable {
        super.tearDownContainer();
        externalContext = null;
        application = null;
        facesContext = null;
        renderKit = null;
        FactoryFinder.releaseFactories();
    }
    /**
     * all i want is this!
     */
    protected static void success(){
        assertTrue(true);
    }
    
    public MockApplication getApplication() {
        return application;
    }
    
    public void setApplication(MockApplication application) {
        this.application = application;
    }
    
    public MockExternalContext getExternalContext() {
        return externalContext;
    }
    
    public void setExternalContext(MockExternalContext externalContext) {
        this.externalContext = externalContext;
    }
    
    public MockFacesContext getFacesContext() {
        return facesContext;
    }
    
    public void setFacesContext(MockFacesContextImpl facesContext) {
        this.facesContext = facesContext;
    }
    
    public MockLifecycle getLifecycle() {
        return lifecycle;
    }
    
    public void setLifecycle(MockLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }
    
    public MockRenderKit getRenderKit() {
        return renderKit;
    }
    
    public void setRenderKit(MockRenderKit renderKit) {
        this.renderKit = renderKit;
    }
}
