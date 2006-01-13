package org.seasar.teeda.core.config.assembler.impl;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.assembler.ApplicationAssembler;
import org.seasar.teeda.core.config.assembler.ApplicationAssemblerHelper;
import org.seasar.teeda.core.config.assembler.JsfAssembler;
import org.seasar.teeda.core.config.element.ApplicationElement;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;

public class SimpleApplicationAssembler extends ApplicationAssembler{
    
    private ApplicationAssemblerHelper helper_;

    public SimpleApplicationAssembler(List applications){
        super(applications);
    }

    public void assemble() {
        List assemblers = helper_.getCollectedAssemblers();
        for(Iterator itr = IteratorUtil.getIterator(assemblers);itr.hasNext();){
            JsfAssembler assembler = (JsfAssembler)itr.next();
            assembler.assemble();
        }
    }
    
    protected void setupChildAssembler(){
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

