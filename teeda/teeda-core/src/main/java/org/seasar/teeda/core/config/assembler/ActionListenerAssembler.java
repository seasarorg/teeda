package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.event.ActionListener;

import org.seasar.teeda.core.util.ClassUtil;

public class ActionListenerAssembler implements JsfAssembler {

    private String actionListenerName_;

    private Application application_;

    public ActionListenerAssembler(String actionListenerName, Application application) {
        actionListenerName_ = actionListenerName;
        application_ = application;
    }

    public void assemble() {
        ActionListener previous = application_.getActionListener();
        ActionListener listener = 
            (ActionListener)ClassUtil.createMarshalInstance(actionListenerName_, ActionListener.class, previous);
        application_.setActionListener(listener);
    }
}
