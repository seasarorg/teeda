package org.seasar.teeda.core.config;

import org.seasar.teeda.core.config.element.FacesConfig;

public interface FacesConfigBuilder {
    
    public FacesConfig createFacesConfigs();
    
    public void addFacesConfigurator(FacesConfigurator configurator);
}