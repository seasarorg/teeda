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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.faces.assembler.ApplicationAssemblerHelper;
import org.seasar.teeda.core.config.faces.element.LocaleConfigElement;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 * 
 * TODO : Need refactoring to simplify because there is illegal to DRY.
 */
public class ApplicationAssemblerHelperImpl implements
        ApplicationAssemblerHelper {

    private Application application_;

    private List assemblers_ = Collections.EMPTY_LIST;

    public ApplicationAssemblerHelperImpl(Application application,
            List assemblers) {
        application_ = application;
        assemblers_ = assemblers;
    }

    public void setupActionListenerAssembler(List listenerNames) {
        // need to simplify?
        for (Iterator itr = IteratorUtil.getIterator(listenerNames); itr
                .hasNext();) {
            String actionListenerName = (String) itr.next();
            assemblers_.add(new ActionListenerAssembler(actionListenerName,
                    application_));
        }
    }

    public void setupLocaleConfigAssembler(List localeConfigs) {
        for (Iterator itr = IteratorUtil.getIterator(localeConfigs); itr
                .hasNext();) {
            LocaleConfigElement localeConfig = (LocaleConfigElement) itr.next();
            assemblers_.add(new LocaleConfigAssembler(localeConfig,
                    application_));
        }
    }

    public void setupNavigationHandlerAssembler(List navigationHandlers) {
        for (Iterator itr = IteratorUtil.getIterator(navigationHandlers); itr
                .hasNext();) {
            String handlerName = (String) itr.next();
            assemblers_.add(new NavigationHandlerAssembler(handlerName,
                    application_));
        }
    }

    public void setupPropertyResolverAssembler(List propertyResolvers) {
        for (Iterator itr = IteratorUtil.getIterator(propertyResolvers); itr
                .hasNext();) {
            String resolverName = (String) itr.next();
            assemblers_.add(new PropertyResolverAssembler(resolverName,
                    application_));
        }
    }

    public void setupStateManagerAssembler(List stateManagers) {
        for (Iterator itr = IteratorUtil.getIterator(stateManagers); itr
                .hasNext();) {
            String stateManagerName = (String) itr.next();
            assemblers_.add(new StateManagerAssembler(stateManagerName,
                    application_));
        }
    }

    public void setupVariableResolverAssembler(List variableResolvers) {
        for (Iterator itr = IteratorUtil.getIterator(variableResolvers); itr
                .hasNext();) {
            String resolverName = (String) itr.next();
            assemblers_.add(new VariableResolverAssembler(resolverName,
                    application_));
        }
    }

    public void setupViewHandlerAssembler(List viewHandlers) {
        for (Iterator itr = IteratorUtil.getIterator(viewHandlers); itr
                .hasNext();) {
            String handlerName = (String) itr.next();
            assemblers_
                    .add(new ViewHandlerAssembler(handlerName, application_));
        }
    }

    public void setupDefaultRenderKitIdAssembler(List defaultRenderKitIds) {
        for (Iterator itr = IteratorUtil.getIterator(defaultRenderKitIds); itr
                .hasNext();) {
            String defaultRenderKitId = (String) itr.next();
            assemblers_.add(new DefaultRenderKitIdAssembler(defaultRenderKitId,
                    application_));
        }
    }

    public void setupMessageBundleAssembler(List messageBundles) {
        for (Iterator itr = IteratorUtil.getIterator(messageBundles); itr
                .hasNext();) {
            String messageBundle = (String) itr.next();
            assemblers_.add(new MessageBundleAssembler(messageBundle,
                    application_));
        }
    }

    public List getCollectedAssemblers() {
        return assemblers_;
    }

}
