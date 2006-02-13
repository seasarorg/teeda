/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.config.faces.assembler;

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