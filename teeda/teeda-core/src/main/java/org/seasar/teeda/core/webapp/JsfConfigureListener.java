/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.webapp;

import javax.faces.FactoryFinder;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.teeda.core.config.FacesConfigBuilder;
import org.seasar.teeda.core.config.assembler.AssemblerFactory;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.util.DIContainerUtil;

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
            
            FacesConfigBuilder builder = 
                (FacesConfigBuilder)DIContainerUtil.getComponent(FacesConfigBuilder.class); 
            
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
