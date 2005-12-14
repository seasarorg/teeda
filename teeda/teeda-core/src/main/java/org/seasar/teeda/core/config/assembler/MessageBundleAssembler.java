package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;


public class MessageBundleAssembler implements JsfAssembler {

    private String messageBundle_;

    private Application application_;

    public MessageBundleAssembler(String messageBundle, Application application) {
        messageBundle_ = messageBundle;
        application_ = application;
    }

    public void assemble() {
        application_.setMessageBundle(messageBundle_);
    }

}
