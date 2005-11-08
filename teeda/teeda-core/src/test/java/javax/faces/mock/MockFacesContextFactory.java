package javax.faces.mock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;


public class MockFacesContextFactory extends FacesContextFactory {

    public MockFacesContextFactory(){
        
    }
    
    public FacesContext getFacesContext(Object context, Object request,
            Object response, Lifecycle lifecycle) throws FacesException {
        return null;
    }

}
