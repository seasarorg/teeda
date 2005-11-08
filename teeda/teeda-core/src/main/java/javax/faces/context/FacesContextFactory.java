package javax.faces.context;

import javax.faces.FacesException;
import javax.faces.lifecycle.Lifecycle;

public abstract class FacesContextFactory {

	public FacesContextFactory() {
	}

	public abstract FacesContext getFacesContext(Object context,
			Object request, Object response, Lifecycle lifecycle)
			throws FacesException;
}
