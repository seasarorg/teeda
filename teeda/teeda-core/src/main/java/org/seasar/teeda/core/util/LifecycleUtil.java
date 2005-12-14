package org.seasar.teeda.core.util;

import javax.faces.context.ExternalContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;

public class LifecycleUtil {

    private LifecycleUtil() {
    }

    public static Lifecycle getLifecycle(ExternalContext externalContext) {
        LifecycleFactory lifecycleFactory = FactoryFinderUtil.getLifecycleFactory();
        Lifecycle lifecycle = 
            lifecycleFactory.getLifecycle(getLifecycleId(externalContext));
        return lifecycle;
    }

    public static String getLifecycleId(ExternalContext externalContext) {
        String lifecycleId = externalContext.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
        if(lifecycleId != null){
            return lifecycleId;
        }
        return LifecycleFactory.DEFAULT_LIFECYCLE;
    }
}
