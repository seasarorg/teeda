package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Iterator;

import javax.faces.context.ExternalContext;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.IteratorUtil;
import org.seasar.teeda.core.util.LifecycleUtil;


public abstract class LifecycleChildAssembler implements JsfAssembler{

    private List lifecycles_;
    private ExternalContext externalContext_;
    public LifecycleChildAssembler(List lifecycles, ExternalContext externalContext){
        lifecycles_ = lifecycles;
        externalContext_ = externalContext;
    }
    
    public void assemble() {
        for(Iterator itr = IteratorUtil.getIterator(lifecycles_);itr.hasNext();){
            String targetName = (String)itr.next();
            doAssemble(targetName);
        }
    }
    
    protected Lifecycle getLifecycle(){
        return LifecycleUtil.getLifecycle(externalContext_);
    }
    
    protected abstract void doAssemble(String targetName);
}