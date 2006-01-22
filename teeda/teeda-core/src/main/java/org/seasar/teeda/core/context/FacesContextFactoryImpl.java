package org.seasar.teeda.core.context;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.teeda.core.exception.FacesContextNotFoundRuntimeException;
import org.seasar.teeda.core.util.DIContainerUtil;

public class FacesContextFactoryImpl extends FacesContextFactory {

    public FacesContext getFacesContext(Object context, Object request,
            Object response, Lifecycle lifecycle) throws FacesException {
        if (context == null || request == null || response == null
                || lifecycle == null) {
            throw new NullPointerException(
                    "Any of parameters are null context = " + context
                            + ", request = " + request + ", response = "
                            + response + ", lifecycle = " + lifecycle);
        }
        if (isServletEnvironment(context, request, response)) {
            ExternalContext externalContext = (ExternalContext) DIContainerUtil
                    .getComponent(ExternalContext.class);
            return new ServletFacesContextImpl(externalContext);
        }
        throw new FacesContextNotFoundRuntimeException();
    }

    private static boolean isServletEnvironment(Object context, Object request,
            Object response) {
        return (context instanceof ServletContext)
                && (request instanceof ServletRequest)
                && (response instanceof ServletResponse);
    }
}
