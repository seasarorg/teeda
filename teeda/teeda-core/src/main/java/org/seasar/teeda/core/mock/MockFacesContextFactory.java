package org.seasar.teeda.core.mock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;


public class MockFacesContextFactory extends FacesContextFactory {

    private FacesContext context_;
    public MockFacesContextFactory(){
    }
    
    public FacesContext getFacesContext(Object context, Object request,
            Object response, Lifecycle lifecycle) throws FacesException {
        if(context_ != null){
            return context_;
        }
        return null;
    }

    public void setFacesContext(FacesContext context){
        context_ = context;
    }
}
