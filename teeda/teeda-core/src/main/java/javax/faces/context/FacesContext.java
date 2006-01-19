package javax.faces.context;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.render.RenderKit;
import javax.faces.component.UIViewRoot;

/**
 * @author shot
 */
public abstract class FacesContext {

    private static ThreadLocal threadLocal_ = new ThreadLocal();

    public abstract javax.faces.application.Application getApplication();

    public abstract Iterator getClientIdsWithMessages();

    public abstract ExternalContext getExternalContext();

    public abstract FacesMessage.Severity getMaximumSeverity();

    public abstract Iterator getMessages();

    public abstract Iterator getMessages(String clientId);

    public abstract RenderKit getRenderKit();

    public abstract boolean getRenderResponse();

    public abstract boolean getResponseComplete();

    public abstract ResponseStream getResponseStream();

    public abstract void setResponseStream(ResponseStream responseStream);

    public abstract ResponseWriter getResponseWriter();

    public abstract void setResponseWriter(
            javax.faces.context.ResponseWriter responseWriter);

    public abstract UIViewRoot getViewRoot();

    public abstract void setViewRoot(UIViewRoot root);

    public abstract void addMessage(String clientId,
            javax.faces.application.FacesMessage message);

    public abstract void release();

    public abstract void renderResponse();

    public abstract void responseComplete();

    public static FacesContext getCurrentInstance() {
        return (FacesContext) threadLocal_.get();
    }

    protected static void setCurrentInstance(
            javax.faces.context.FacesContext context) {
        threadLocal_.set(context);
    }

}
