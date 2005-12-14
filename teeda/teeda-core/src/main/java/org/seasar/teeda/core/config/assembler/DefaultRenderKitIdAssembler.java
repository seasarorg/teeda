package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;


public class DefaultRenderKitIdAssembler implements JsfAssembler {

    private String defaultRenderKitId_;

    private Application application_;

    public DefaultRenderKitIdAssembler(String defaultRenderKitId, Application application) {
        defaultRenderKitId_ = defaultRenderKitId;
        application_ = application;
    }

    public void assemble() {
        application_.setDefaultRenderKitId(defaultRenderKitId_);
    }

}
