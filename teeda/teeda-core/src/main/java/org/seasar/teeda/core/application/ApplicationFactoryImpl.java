package org.seasar.teeda.core.application;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author Shinpei Ohtani
 */
public class ApplicationFactoryImpl extends ApplicationFactory {

    private Application application_ = null;
    
    public ApplicationFactoryImpl(){
        super();
        application_ = (Application)DIContainerUtil.getComponent(Application.class);
    }
    
    public Application getApplication() {
        return application_;
    }

    public void setApplication(Application application) {
        application_ = application;
    }

}
