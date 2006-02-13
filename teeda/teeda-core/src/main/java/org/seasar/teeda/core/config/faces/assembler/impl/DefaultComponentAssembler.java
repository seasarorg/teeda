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

import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.faces.assembler.ComponentAssembler;
import org.seasar.teeda.core.config.faces.element.ComponentElement;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultComponentAssembler extends ComponentAssembler {

    private Application application_;
    public DefaultComponentAssembler(Map components) {
        super(components);
    }

    protected void setupBeforeAssemble() {
        application_ = ApplicationUtil.getApplicationFromFactory();
    }

    public void assemble() {
        String componentType = null;
        String componentClassName = null;
        ComponentElement component = null;
        for(Iterator itr = IteratorUtil.getEntryIterator(getComponents());itr.hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            componentType = (String)entry.getKey();
            component = (ComponentElement)entry.getValue();
            componentClassName = component.getComponentClass();
            application_.addComponent(componentType, componentClassName);
        }
    }

}
