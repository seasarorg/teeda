package org.seasar.teeda.core.config.assembler;

import java.util.List;

public interface ApplicationAssemblerHelper {
    
    public void setupActionListenerAssembler(List listenerNames);

    public void setupLocaleConfigAssembler(List localeConfigs);

    public void setupNavigationHandlerAssembler(List navigationHandlers);

    public void setupPropertyResolverAssembler(List propertyResolvers);

    public void setupStateManagerAssembler(List stateManagers);

    public void setupVariableResolverAssembler(List variableResolvers);

    public void setupViewHandlerAssembler(List viewHandlers);

    public void setupDefaultRenderKitIdAssembler(List defaultRenderKitIds);

    public void setupMessageBundleAssembler(List messageBundles);
    
    public List getCollectedAssemblers();
}