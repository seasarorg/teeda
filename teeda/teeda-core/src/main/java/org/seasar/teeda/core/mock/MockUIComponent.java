package org.seasar.teeda.core.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

public class MockUIComponent extends UIComponent {

    private UIComponent parent_ = null;

    private FacesEvent event_;

    private String id_;

    private String clientId_ = "";

    private Map facets_ = new HashMap();

    private String family_;

    public Map getAttributes() {
        return null;
    }

    public ValueBinding getValueBinding(String name) {
        return null;
    }

    public void setValueBinding(String s, ValueBinding valuebinding) {
    }

    public String getClientId(FacesContext context) {
        return clientId_;
    }

    public void setClientId(String clientId) {
        clientId_ = clientId;
    }

    public String getFamily() {
        return family_;
    }

    public void setFamily(String family) {
        family_ = family;
    }

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        id_ = id;
    }

    public UIComponent getParent() {
        return parent_;
    }

    public void setParent(UIComponent parent) {
        parent_ = parent;
    }

    public boolean isRendered() {
        return false;
    }

    public void setRendered(boolean flag) {
    }

    public String getRendererType() {
        return null;
    }

    public void setRendererType(String type) {
    }

    public boolean getRendersChildren() {
        return false;
    }

    public List getChildren() {
        return null;
    }

    public int getChildCount() {
        return 0;
    }

    public UIComponent findComponent(String expr) {
        return null;
    }

    public Map getFacets() {
        return facets_;
    }

    public UIComponent getFacet(String s) {
        return (UIComponent) facets_.get(s);
    }

    public Iterator getFacetsAndChildren() {
        return null;
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
    }

    public void decode(FacesContext context) {
    }

    public void encodeBegin(FacesContext context) throws IOException {
    }

    public void encodeChildren(FacesContext context) throws IOException {
    }

    public void encodeEnd(FacesContext context) throws IOException {
    }

    protected void addFacesListener(FacesListener listener) {
    }

    protected FacesListener[] getFacesListeners(Class clazz) {
        return null;
    }

    protected void removeFacesListener(FacesListener listener) {
    }

    public void queueEvent(FacesEvent event) {
        event_ = event;
    }

    public void processRestoreState(FacesContext context, Object state) {
    }

    public void processDecodes(FacesContext context) {
    }

    public void processValidators(FacesContext context) {
    }

    public void processUpdates(FacesContext context) {
    }

    public Object processSaveState(FacesContext context) {
        return null;
    }

    protected FacesContext getFacesContext() {
        return null;
    }

    protected Renderer getRenderer(FacesContext context) {
        return null;
    }

    public boolean isTransient() {
        return false;
    }

    public void setTransient(boolean transientValue) {
    }

    public Object saveState(FacesContext context) {
        return null;
    }

    public void restoreState(FacesContext context, Object state) {
    }

    public FacesEvent getQueueEvent() {
        return event_;
    }

}
