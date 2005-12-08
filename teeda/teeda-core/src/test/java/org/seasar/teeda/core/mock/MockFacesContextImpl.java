package org.seasar.teeda.core.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;


public class MockFacesContextImpl extends MockFacesContext {

    private UIViewRoot root_ = null;
    private ExternalContext context_;
    private Application application_;
    private Map messages_ = new HashMap();

    public MockFacesContextImpl(){
        setCurrentInstance(this);
    }
    
    public MockFacesContextImpl(ExternalContext context, Application application){
        context_ = context;
        application_ = application;
        setCurrentInstance(this);
    }
    
    public Application getApplication() {
        return application_;
    }

    public Iterator getClientIdsWithMessages() {
        return null;
    }

    public ExternalContext getExternalContext() {
        return context_;
    }

    public Severity getMaximumSeverity() {
        return null;
    }

    public Iterator getMessages() {
        return messages_.values().iterator();
    }

    public Iterator getMessages(String clientId) {
        Object o = messages_.get(clientId);
        List list = new ArrayList();
        list.add(o);
        return list.iterator();
    }

    public RenderKit getRenderKit() {
        return null;
    }

    public boolean getRenderResponse() {
        return false;
    }

    public boolean getResponseComplete() {
        return false;
    }

    public ResponseStream getResponseStream() {
        return null;
    }

    public void setResponseStream(ResponseStream responseStream) {
    }

    public ResponseWriter getResponseWriter() {
        return null;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
    }

    public UIViewRoot getViewRoot() {
        return root_;
    }

    public void setViewRoot(UIViewRoot root) {
        root_ = root;
    }

    public void addMessage(String clientId, FacesMessage message) {
        messages_.put(clientId, message);
    }

    public void release() {
    }

    public void renderResponse() {
    }

    public void responseComplete() {
    }

    public void setExternalContext(ExternalContext context) {
        context_ = context;
    }

    public void setApplication(Application application) {
        application_ = application;
    }

}
