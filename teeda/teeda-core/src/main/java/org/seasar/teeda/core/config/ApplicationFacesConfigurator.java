package org.seasar.teeda.core.config;

public class ApplicationFacesConfigurator extends AbstractFacesConfigurator {

    public ApplicationFacesConfigurator() {
    }

    protected String getPath() {
        return "WEB-INF/faces-config.xml";
    }

}
