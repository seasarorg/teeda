package javax.faces.component;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

public abstract class UIComponent implements StateHolder {

    public UIComponent() {
    }

    public abstract Map getAttributes();

    public abstract ValueBinding getValueBinding(String name);

    public abstract void setValueBinding(String name, ValueBinding binding);

    public abstract String getClientId(FacesContext context);

    public abstract String getFamily();

    public abstract String getId();

    public abstract void setId(String id);

    public abstract UIComponent getParent();

    public abstract void setParent(UIComponent parent);

    public abstract boolean isRendered();

    public abstract void setRendered(boolean rendered);

    public abstract String getRendererType();

    public abstract void setRendererType(String rendererType);

    public abstract boolean getRendersChildren();

    public abstract List getChildren();

    public abstract int getChildCount();

    public abstract UIComponent findComponent(String expr);

    public abstract Map getFacets();

    public abstract UIComponent getFacet(String name);

    public abstract Iterator getFacetsAndChildren();

    public abstract void broadcast(FacesEvent event)
        throws AbortProcessingException;

    public abstract void decode(FacesContext context);

    public abstract void encodeBegin(FacesContext context) throws IOException;

    public abstract void encodeChildren(FacesContext context)
        throws IOException;

    public abstract void encodeEnd(FacesContext context) throws IOException;

    protected abstract void addFacesListener(FacesListener listener);

    protected abstract FacesListener[] getFacesListeners(Class clazz);

    protected abstract void removeFacesListener(FacesListener listener);

    public abstract void queueEvent(FacesEvent event);

    public abstract void processRestoreState(FacesContext context, Object state);

    public abstract void processDecodes(FacesContext context);

    public abstract void processValidators(FacesContext context);

    public abstract void processUpdates(FacesContext context);

    public abstract Object processSaveState(FacesContext context);

    protected abstract FacesContext getFacesContext();

    protected abstract Renderer getRenderer(FacesContext context);

}
