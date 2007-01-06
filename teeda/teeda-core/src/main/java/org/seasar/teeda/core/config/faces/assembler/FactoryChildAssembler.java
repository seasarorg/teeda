/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.FactoryFinder;

/**
 * @author shot
 */
public abstract class FactoryChildAssembler implements JsfAssembler {

    private List factories_ = new LinkedList();

    public FactoryChildAssembler() {
    }

    public void setTargetFactories(List factories) {
        if (factories != null) {
            factories_.addAll(factories);
        }
    }

    public void assemble() {
        String factoryImplName = null;
        for (Iterator itr = factories_.iterator(); itr.hasNext();) {
            factoryImplName = (String) itr.next();
            FactoryFinder.setFactory(getFactoryClassName(), factoryImplName);
        }
    }

    protected abstract String getFactoryClassName();

}
