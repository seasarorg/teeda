package javax.faces.webapp;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
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

    private Application application_ = null;

    private Lifecycle lifecycle_ = null;

    public void destroy() {
        config_ = null;
        facesContextFactory_ = null;
        application_ = null;
        lifecycle_ = null;
    }

    public ServletConfig getServletConfig() {
        return config_;
    }

    public String getServletInfo() {
        return this.getClass().getName();
    }

    public void init(ServletConfig config) throws ServletException {
        config_ = config;
        facesContextFactory_ = (FacesContextFactory)WebAppUtils_
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

        ApplicationFactory applicationFactory = 
            (ApplicationFactory)WebAppUtils_.getFactory(FactoryFinder.APPLICATION_FACTORY);
        application_ = applicationFactory.getApplication();

        LifecycleFactory lifecycleFactory = 
            (LifecycleFactory)WebAppUtils_.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        String lifecycleId = WebAppUtils_.getLifecycleId(config_);
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
