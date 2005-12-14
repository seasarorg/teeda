package org.seasar.teeda.core.config.element;

import java.util.List;

public interface FactoryElement extends JsfConfig {

    public void addApplicationFactory(String applicationFactory);

    public void addFacesContextFactory(String facesContextFactory);

    public void addLifecycleFactory(String lifecycleFactory);

    public void addRenderKitFactory(String renderKitFactory);

    public List getApplicationFactories();
    
    public List getFacesContextFactories();

    public List getLifecycleFactories();

    public List getRenderKitFactories();

}
