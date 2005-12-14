package org.seasar.teeda.core.config.element;

import java.util.List;

public interface ApplicationElement extends JsfConfig{
    
    public void addActionListener(String actionListener);

    public void addStateManager(String stateManager);        
    
    public void addPropertyResolver(String propertyResolver);

    public void addVariableResolver(String variableResolver);
    
    public void addNavigationHandler(String navigationHandler);

    public void addViewHandler(String viewHandler);
    
    public void addDefaultRenderKitId(String defaultRenderKitId);
    
    public void addLocaleConfig(LocaleConfigElement localeConfig);
    
    public void addMessageBundle(String messageBundle);
    
    public List getActionListeners();

    public List getStateManagers();

    public List getPropertyResolvers();

    public List getVariableResolvers();

    public List getNavigationHandlers();

    public List getViewHandlers();

    public List getDefaultRenderKitIds();
    
    public List getLocaleConfigs();

    public List getMessageBundles();
    
}
