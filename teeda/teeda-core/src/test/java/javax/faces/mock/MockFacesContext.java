package javax.faces.mock;

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
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

public class MockFacesContext extends FacesContext {

	private UIViewRoot root_ = null;
	private ExternalContext externalContext_;
    private Application app_;
    private Map messages_ = new HashMap();
    
	public MockFacesContext(){
		setCurrentInstance(this);
	}
	
    public MockFacesContext(ExternalContext externalContext){
        this(null, externalContext);
        setCurrentInstance(this);
    }
    
    public MockFacesContext(Application app){
        this(app, null);
        setCurrentInstance(this);
    }
    
    public MockFacesContext(Application app, ExternalContext externalContext){
        app_ = app;
        externalContext_ = externalContext;
    }
    
	public Application getApplication() {
		return app_;
	}

	public Iterator getClientIdsWithMessages() {
		return null;
	}

	public ExternalContext getExternalContext() {
		return externalContext_;
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

}
