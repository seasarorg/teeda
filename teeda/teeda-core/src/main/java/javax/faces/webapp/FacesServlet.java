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
package javax.faces.webapp;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.internal.WebAppUtils;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FacesServlet implements Servlet {

    public static final String CONFIG_FILES_ATTR = "javax.faces.CONFIG_FILES";

    public static final String LIFECYCLE_ID_ATTR = "javax.faces.LIFECYCLE_ID";

    private ServletConfig config_ = null;

    private FacesContextFactory facesContextFactory_ = null;

    private Lifecycle lifecycle_ = null;

    public void destroy() {
        config_ = null;
        facesContextFactory_ = null;
        lifecycle_ = null;
    }

    public ServletConfig getServletConfig() {
        return config_;
    }

    public String getServletInfo() {
        return "Teeda - A JSF implementation with DI x AOP by Seasar Foundation -";
    }

    public void init(ServletConfig config) throws ServletException {
        config_ = config;
        facesContextFactory_ = (FacesContextFactory)WebAppUtils
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = 
            (LifecycleFactory)WebAppUtils.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        String lifecycleId = WebAppUtils.getLifecycleId(config_);
        lifecycle_ = lifecycleFactory.getLifecycle(lifecycleId);
    }

    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = config_.getServletContext();
        FacesContext context = 
            facesContextFactory_.getFacesContext(servletContext, request, response, lifecycle_);
        try{
            lifecycle_.execute(context);
            lifecycle_.render(context);
        }catch (FacesException e){
            Throwable t = e.getCause();
            if(t == null){
                throw new ServletException(e.getMessage(), e);
            }else{
                if(t instanceof ServletException){
                    throw ((ServletException)t);
                }else if(t instanceof IOException){
                    throw ((IOException)t);
                }else{
                    throw new ServletException(t.getMessage(), t);
                }
            }
        }finally{
            context.release();
        }
    }

}
