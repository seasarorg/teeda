package org.seasar.teeda.core.webapp;

import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.teeda.core.config.FacesConfigBuilder;
import org.seasar.teeda.core.config.assembler.AssemblerFactory;
import org.seasar.teeda.core.config.element.FacesConfig;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class JsfConfigureListener implements ServletContextListener {

    static final String FACES_INIT_DONE = JsfConfigureListener.class.getName() + ".FACES_INIT_DONE";  
    
    public void contextInitialized(ServletContextEvent event) {
        initializeFaces(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        FactoryFinder.releaseFactories();
    }

    private void initializeFaces(ServletContext context){
        
        Boolean b = (Boolean)context.getAttribute(FACES_INIT_DONE);
        boolean isAlreadyInitialized = ( b != null) ? b.booleanValue() : false;
        if(!isAlreadyInitialized){
            
            S2Container container = SingletonS2ContainerFactory.getContainer();
            FacesConfigBuilder builder = 
                (FacesConfigBuilder)container.getComponent(FacesConfigBuilder.class); 
            
            FacesConfig facesConfig = builder.createFacesConfigs(); 

            AssemblerFactory.assembleFactories(facesConfig);
            
            AssemblerFactory.assembleApplication(facesConfig);
            
            AssemblerFactory.assembleManagedBeans(facesConfig);

            AssemblerFactory.assmbleNavigationRules(facesConfig);

            AssemblerFactory.assembleLifecycle(facesConfig);
            
            AssemblerFactory.assembleRenderKits(facesConfig);
            
            
            
            //init web.xml
            
            
            context.setAttribute(FACES_INIT_DONE, Boolean.TRUE);
            
        }
        
    }
    
}
