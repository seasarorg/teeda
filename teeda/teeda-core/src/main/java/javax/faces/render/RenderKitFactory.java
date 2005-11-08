package javax.faces.render;

import java.util.Iterator;

import javax.faces.context.FacesContext;

public abstract class RenderKitFactory {

	public static final String HTML_BASIC_RENDER_KIT = "HTML_BASIC";

	public abstract RenderKit getRenderKit(FacesContext context,
			String renderKitId);

	public abstract Iterator getRenderKitIds();

	public abstract void addRenderKit(String renderKitID, RenderKit renderKit);
}
