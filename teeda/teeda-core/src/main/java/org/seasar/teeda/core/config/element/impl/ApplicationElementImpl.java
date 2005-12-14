package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.ApplicationElement;
import org.seasar.teeda.core.config.element.LocaleConfigElement;

/**
 * @author Shinpei Ohtani(aka shot)
 * 
 */
public class ApplicationElementImpl implements ApplicationElement {

    private List actionListeners_;

    private List stateManagers_;

    private List propertyResolvers_;

    private List variableResolvers_;

    private List navigationHandlers_;
    
    private List viewHandlers_;
    
    private List defaultRenderKitIds_;
    
    private List localeConfigs_;
    
    private List messageBundles_;
    
    public ApplicationElementImpl() {
        actionListeners_ = new ArrayList();
        stateManagers_ = new ArrayList();
        propertyResolvers_ = new ArrayList();
        variableResolvers_ = new ArrayList();
        navigationHandlers_ = new ArrayList();
        viewHandlers_ = new ArrayList();
        defaultRenderKitIds_ = new ArrayList();
        localeConfigs_ = new ArrayList();
        messageBundles_ = new ArrayList();
    }

    public void addActionListener(String actionListener) {
        actionListeners_.add(actionListener);
    }

    public void addStateManager(String stateManager) {
        stateManagers_.add(stateManager);
    }

    public void addPropertyResolver(String propertyResolver) {
        propertyResolvers_.add(propertyResolver);
    }

    public void addVariableResolver(String variableResolver) {
        variableResolvers_.add(variableResolver);
    }

    public void addNavigationHandler(String navigationHandler) {
        navigationHandlers_.add(navigationHandler);
    }

    public void addViewHandler(String viewHandler) {
        viewHandlers_.add(viewHandler);
    }

    public void addDefaultRenderKitId(String defaultRenderKitId) {
        defaultRenderKitIds_.add(defaultRenderKitId);
    }

    public void addLocaleConfig(LocaleConfigElement localeConfig) {
        localeConfigs_.add(localeConfig);
    }

    public void addMessageBundle(String messageBundle) {
        messageBundles_.add(messageBundle);
    }

    public List getActionListeners() {
        return actionListeners_;
    }

    public List getStateManagers() {
        return stateManagers_;
    }

    public List getPropertyResolvers() {
        return propertyResolvers_;
    }

    public List getVariableResolvers() {
        return variableResolvers_;
    }

    public List getNavigationHandlers() {
        return navigationHandlers_;
    }

    public List getViewHandlers() {
        return viewHandlers_;
    }

    public List getDefaultRenderKitIds() {
        return defaultRenderKitIds_;
    }

    public List getLocaleConfigs() {
        return localeConfigs_;
    }
    
    public List getMessageBundles() {
        return messageBundles_;
    }

}
