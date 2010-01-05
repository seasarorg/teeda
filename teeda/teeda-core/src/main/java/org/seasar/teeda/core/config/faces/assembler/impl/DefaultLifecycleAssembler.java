/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import java.util.LinkedList;
import java.util.List;

import org.seasar.teeda.core.config.faces.assembler.LifecycleAssembler;
import org.seasar.teeda.core.config.faces.assembler.LifecycleChildAssembler;
import org.seasar.teeda.core.config.faces.element.LifecycleElement;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultLifecycleAssembler extends LifecycleAssembler {

    private LifecycleChildAssembler child_;

    public DefaultLifecycleAssembler(List lifecycles) {
        super(lifecycles);
    }

    protected void setupBeforeAssemble() {
        List targets = new LinkedList();
        for (Iterator itr = IteratorUtil.getIterator(getLifecycles()); itr
                .hasNext();) {
            LifecycleElement element = (LifecycleElement) itr.next();
            targets.addAll(element.getPhaseListeners());
        }
        child_ = new PhaseListenerAssembler(targets);
    }

    public void assemble() {
        child_.assemble();
    }

}
