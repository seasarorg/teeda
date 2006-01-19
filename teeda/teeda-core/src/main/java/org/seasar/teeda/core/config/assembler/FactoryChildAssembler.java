package org.seasar.teeda.core.config.assembler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.FactoryFinder;

/**
 * @author Shinpei Ohtani(aka shot)
 */ 
public abstract class FactoryChildAssembler implements JsfAssembler {

    private List factories_ = new LinkedList();
    
    public FactoryChildAssembler() {
    }

    public void setTargetFactories(List factories){
        if(factories != null){
            factories_.addAll(factories);
        }
    }
    
    public void assemble() {
        String factoryImplName = null;
        for (Iterator itr = factories_.iterator(); itr.hasNext();){
            factoryImplName = (String)itr.next();
            FactoryFinder.setFactory(getFactoryClassName(), factoryImplName);
        }
    }

    protected abstract String getFactoryClassName();

}
