package javax.faces.application;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.Locale;

public abstract class ViewHandler {

	public abstract Locale calculateLocale(FacesContext context);

	public abstract String calculateRenderKitId(FacesContext context);

	public abstract UIViewRoot createView(FacesContext context, String viewId);

	public abstract String getActionURL(FacesContext context, String viewId);

	public abstract String getResourceURL(FacesContext context, String path);

	public abstract void restoreView(FacesContext context, String viewId)
			throws IOException;

	public abstract void writeState(FacesContext context) throws IOException;
}
