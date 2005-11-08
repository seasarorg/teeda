package javax.faces.mock;

import java.util.Iterator;

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

	UIViewRoot root_ = null;
	private ExternalContext externalContext_;
	public MockFacesContext(){
		super();
		setCurrentInstance(this);
	}
	
    public MockFacesContext(ExternalContext externalContext){
        super();
        externalContext_ = externalContext;
        setCurrentInstance(this);
    }
    
	public Application getApplication() {
		return null;
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
		return null;
	}

	public Iterator getMessages(String clientId) {
		return null;
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
	}

	public void release() {
	}

	public void renderResponse() {
	}

	public void responseComplete() {
	}

}
