package org.seasar.teeda.core.config;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class CoreFacesConfigurator extends AbstractFacesConfigurator {

    private static final String BASE_FACES_CONFIG = "/core-faces-config.xml"; 
        
    public CoreFacesConfigurator(){
        super();
    }

    protected String getPath() {
        return JsfConstants.CORE_PACKAGE_ROOT.replace('.', '/') + BASE_FACES_CONFIG;
    }
    
    
}
