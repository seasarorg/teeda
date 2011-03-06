/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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

/**
 * @author shot
 */
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
