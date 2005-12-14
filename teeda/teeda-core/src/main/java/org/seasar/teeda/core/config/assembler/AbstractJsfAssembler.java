package org.seasar.teeda.core.config.assembler;

import java.util.Collection;
import java.util.Iterator;

import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.IteratorUtil;


public abstract class AbstractJsfAssembler implements JsfAssembler{

    protected final void isAllSuitableJsfElement(Collection elements, Class target){
        Object o = null;
        for(Iterator itr = IteratorUtil.getIterator(elements);itr.hasNext();){
            o = itr.next();
            if(!ClassUtil.isAssignableFrom(target, o.getClass())){
                throw new IllegalJsfConfigStateException(new Object[]{o, target});
            }
        }
    }

    protected abstract void setupChildAssembler();

}
