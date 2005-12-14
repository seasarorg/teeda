package org.seasar.teeda.core.util;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;


public class FactoryFinderUtil {
    
    private FactoryFinderUtil(){
    }
    
    public static ApplicationFactory getApplicationFactory(){
        return (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
    }
    
    public static FacesContextFactory getFacesContextFactory(){
        return (FacesContextFactory)FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
    }
    
    public static LifecycleFactory getLifecycleFactory(){
        return (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
    }
    
    public static RenderKitFactory getRenderKitFactory(){
        return (RenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    }
}
