package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.teeda.core.util.IteratorUtil;
import org.seasar.teeda.core.util.LifecycleUtil;


public abstract class LifecycleChildAssembler implements JsfAssembler{

    private List targetLists_ = Collections.EMPTY_LIST;
    private ExternalContext externalContext_;
    public LifecycleChildAssembler(List targetLists, ExternalContext externalContext){
        targetLists_ = targetLists;
        externalContext_ = externalContext;
    }
    
    public void assemble() {
        for(Iterator itr = IteratorUtil.getIterator(targetLists_);itr.hasNext();){
            String targetName = (String)itr.next();
            doAssemble(targetName);
        }
    }
    
    protected Lifecycle getLifecycle(){
        return LifecycleUtil.getLifecycle(externalContext_);
    }
    
    protected abstract void doAssemble(String targetName);
}