package org.seasar.teeda.core.lifecycle.impl;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.lifecycle.AbstractPhase;
import org.seasar.teeda.core.lifecycle.Postback;
import org.seasar.teeda.core.util.ExternalContextUtil;


public class RestoreViewPhase extends AbstractPhase implements Postback{

    private static final String VIEW_ID_ATTR = RestoreViewPhase.class.getName() + ".VIEW_ID";
    private boolean postback_;
    public RestoreViewPhase(){
    }
    
    protected void executePhase(FacesContext context) {
        ExternalContext externalContext = context.getExternalContext();
        String viewId = ExternalContextUtil.getViewId(externalContext);
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.restoreView(context, viewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, viewId);
        }
        String previousViewId = getViewIdFromSession(externalContext);
        context.setViewRoot(viewRoot);
        saveViewIdToSession(externalContext, viewId);
        initializeChildren(context, viewRoot);
        if (externalContext.getRequestParameterMap().isEmpty()) {
            context.renderResponse();
        }
        setPostback(viewId.equals(previousViewId));
    }
    
    protected String getViewIdFromSession(ExternalContext externalContext) {
        return (String) externalContext.getSessionMap().get(VIEW_ID_ATTR);
    }

    protected void saveViewIdToSession(ExternalContext externalContext, String viewId) {
        externalContext.getSessionMap().put(VIEW_ID_ATTR, viewId);
    }

    protected PhaseId getCurrentPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    protected void setPostback(boolean postback){
        postback_ = postback;
    }
    
    public boolean isPostBack() {
        return postback_;
    }
    
}

