package javax.faces.component;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

public interface StateHolder {

	public boolean isTransient();

	public void setTransient(boolean transientValue);

	public Object saveState(FacesContext context);

	public void restoreState(FacesContext context, Object state);

}
