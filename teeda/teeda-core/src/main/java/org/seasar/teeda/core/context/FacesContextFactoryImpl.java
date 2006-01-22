package org.seasar.teeda.core.context;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FacesContextFactoryImpl extends FacesContextFactory {

    public FacesContext getFacesContext(Object context, Object request,
            Object response, Lifecycle lifecycle) throws FacesException {
        // TODO impl this
        if (context == null || request == null || response == null
                || lifecycle == null) {
            throw new NullPointerException(
                    "Any of parameters are null context = " + context
                            + ", request = " + request + ", response = "
                            + response + ", lifecycle = " + lifecycle);
        }
        if (isServletEnvironment(context, request, response)) {
            return new ServletFacesContextImpl();
        }
        return null;
    }

    private static boolean isServletEnvironment(Object context, Object request,
            Object response) {
        return (context instanceof ServletContext)
                && (request instanceof ServletRequest)
                && (response instanceof ServletResponse);
    }
}
