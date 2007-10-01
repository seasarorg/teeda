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

import java.util.Collections;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.FactoryElement;

/**
 * @author shot
 */
public abstract class FactoryAssembler extends AbstractJsfAssembler {

    private List factories_ = Collections.EMPTY_LIST;

    public FactoryAssembler(List factories) {
        isAllSuitableJsfElement(factories, FactoryElement.class);
        factories_ = factories;
        setupBeforeAssemble();
    }

    protected final List getFactories() {
        return factories_;
    }

    public void assemble() {
        assembleApplicationFactory();
        assembleFacesContextFactory();
        assembleLifecycleFactory();
        assembleRenderKitFactory();
    }

    protected abstract void assembleApplicationFactory();

    protected abstract void assembleFacesContextFactory();

    protected abstract void assembleLifecycleFactory();

    protected abstract void assembleRenderKitFactory();

}