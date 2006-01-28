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
package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.assembler.ApplicationAssembler;
import org.seasar.teeda.core.config.assembler.ApplicationAssemblerHelper;
import org.seasar.teeda.core.config.assembler.ApplicationChildAssembler;
import org.seasar.teeda.core.config.element.ApplicationElement;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;

public class DefaultApplicationAssembler extends ApplicationAssembler{
    
    private ApplicationAssemblerHelper helper_;

    public DefaultApplicationAssembler(List applications){
        super(applications);
    }

    public void assemble() {
        List assemblers = helper_.getCollectedAssemblers();
        for(Iterator itr = IteratorUtil.getIterator(assemblers);itr.hasNext();){
            ApplicationChildAssembler assembler = (ApplicationChildAssembler)itr.next();
            assembler.assemble();
        }
    }
    
    protected void setupBeforeAssemble(){
        ApplicationElement appElement = null;
        helper_ = createApplicationAssemblerHelper();
        for(Iterator itr = IteratorUtil.getIterator(getApplications());itr.hasNext();){
            appElement = (ApplicationElement)itr.next();
            helper_.setupActionListenerAssembler(appElement.getActionListeners());
            helper_.setupLocaleConfigAssembler(appElement.getLocaleConfigs());
            helper_.setupNavigationHandlerAssembler(appElement.getNavigationHandlers());
            helper_.setupPropertyResolverAssembler(appElement.getPropertyResolvers());
            helper_.setupStateManagerAssembler(appElement.getStateManagers());
            helper_.setupVariableResolverAssembler(appElement.getVariableResolvers());
            helper_.setupViewHandlerAssembler(appElement.getViewHandlers());
            helper_.setupDefaultRenderKitIdAssembler(appElement.getDefaultRenderKitIds());
            helper_.setupMessageBundleAssembler(appElement.getMessageBundles());
        }
    }

    protected ApplicationAssemblerHelper createApplicationAssemblerHelper(){
        if(helper_ != null){
            return helper_;
        }
        Application application = ApplicationUtil.getApplicationFromFactory();
        return new ApplicationAssemblerHelperImpl(application, new LinkedList());
    }

}

