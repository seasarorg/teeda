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