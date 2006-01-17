package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.Map;

import org.seasar.teeda.core.config.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.element.ManagedBeanElement;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.IteratorUtil;

public class DefaultManagedBeanAssembler extends ManagedBeanAssembler {

    private ManagedBeanFactory managedBeanFactory_;

    private ScopeManager scopeManager_;

    public DefaultManagedBeanAssembler(Map managedBeans) {
        super(managedBeans);
    }

    protected void setupBeforeAssemble() {
        managedBeanFactory_ = 
            (ManagedBeanFactory)DIContainerUtil.getComponent(ManagedBeanFactory.class);
        scopeManager_ = 
            (ScopeManager)DIContainerUtil.getComponent(ScopeManager.class);
    }

    public void assemble() {
        String managedBeanName = null;
        ManagedBeanElement element = null;
        for (Iterator itr = IteratorUtil.getIterator(getManagedBeans()); itr
                .hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            managedBeanName = (String)entry.getKey();
            element = (ManagedBeanElement)entry.getValue();
            Class mbClass = ClassUtil.forName(element.getManagedBeanClass());
            Scope scope = scopeManager_.getScope(element.getManagedBeanScope());
            managedBeanFactory_.setManagedBean(managedBeanName, mbClass, scope);
        }
    }

}
