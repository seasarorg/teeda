/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.io.InputStream;

import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.seasar.framework.container.servlet.S2ContainerListener;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.faces.FacesConfigBuilder;
import org.seasar.teeda.core.config.faces.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.webapp.WebappConfigBuilder;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 * @author manhole
 */
public class TeedaConfigureListener extends S2ContainerListener {

    private static final String FACES_INIT_DONE = TeedaConfigureListener.class
            .getName()
            + ".FACES_INIT_DONE";

    private static Logger logger = Logger.getLogger(TeedaConfigureListener.class);

    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        logger.debug("JSF initialize start");
        try {
            initializeFaces(event.getServletContext());
        } catch (RuntimeException e) {
            logger.log(e);
            throw e;
        }
        logger.debug("JSF initialize end");
    }

    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
        FactoryFinder.releaseFactories();
    }

    private void initializeFaces(ServletContext context) {
        Boolean b = (Boolean) context.getAttribute(FACES_INIT_DONE);
        boolean isAlreadyInitialized = (b != null) ? b.booleanValue() : false;
        if (!isAlreadyInitialized) {

            FacesConfigBuilder facesConfigBuilder = (FacesConfigBuilder) DIContainerUtil
                    .getComponent(FacesConfigBuilder.class);

            FacesConfig facesConfig = facesConfigBuilder.createFacesConfigs();

            AssemblerAssembler assembler = (AssemblerAssembler) DIContainerUtil
                    .getComponent(AssemblerAssembler.class);

            assembler.assembleFactories(facesConfig);
            assembler.assembleApplication(facesConfig);
            assembler.assembleManagedBeans(facesConfig);
            assembler.assmbleNavigationRules(facesConfig);
            assembler.assembleLifecycle(facesConfig);
            assembler.assembleRenderKits(facesConfig);

            WebappConfigBuilder webAppConfigBuilder = (WebappConfigBuilder) DIContainerUtil
                    .getComponent(WebappConfigBuilder.class);
            ExternalContext externalContext = (ExternalContext) DIContainerUtil
                    .getComponent(ExternalContext.class);

            InputStream is = null;
            WebappConfig webappConfig = null;
            try {
                is = externalContext
                        .getResourceAsStream(JsfConstants.WEB_XML_PATH);
                webappConfig = webAppConfigBuilder.build(is);
            } finally {
                InputStreamUtil.close(is);
            }

            externalContext.getApplicationMap().put(
                    WebappConfig.class.getName(), webappConfig);

            context.setAttribute(FACES_INIT_DONE, Boolean.TRUE);
        }

    }

}
