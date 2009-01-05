/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import java.util.List;

import javax.faces.FactoryFinder;

import org.seasar.teeda.core.config.faces.assembler.FactoryAssembler;
import org.seasar.teeda.core.config.faces.assembler.FactoryChildAssembler;
import org.seasar.teeda.core.config.faces.element.FactoryElement;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultFactoryAssembler extends FactoryAssembler {

    private FactoryChildAssembler applicationFactory_;

    private FactoryChildAssembler facesContextFactory_;

    private FactoryChildAssembler lifecycleFactory_;

    private FactoryChildAssembler renderKitFactory_;

    public DefaultFactoryAssembler(List factories) {
        super(factories);
    }

    protected void setupBeforeAssemble() {
        applicationFactory_ = new FactoryChildAssembler() {
            protected String getFactoryClassName() {
                return FactoryFinder.APPLICATION_FACTORY;
            }
        };

        facesContextFactory_ = new FactoryChildAssembler() {
            protected String getFactoryClassName() {
                return FactoryFinder.FACES_CONTEXT_FACTORY;
            }
        };

        lifecycleFactory_ = new FactoryChildAssembler() {
            protected String getFactoryClassName() {
                return FactoryFinder.LIFECYCLE_FACTORY;
            }
        };

        renderKitFactory_ = new FactoryChildAssembler() {
            protected String getFactoryClassName() {
                return FactoryFinder.RENDER_KIT_FACTORY;
            }
        };

        for (Iterator itr = IteratorUtil.getIterator(getFactories()); itr
                .hasNext();) {
            FactoryElement factory = (FactoryElement) itr.next();
            applicationFactory_.setTargetFactories(factory
                    .getApplicationFactories());
            facesContextFactory_.setTargetFactories(factory
                    .getFacesContextFactories());
            lifecycleFactory_.setTargetFactories(factory
                    .getLifecycleFactories());
            renderKitFactory_.setTargetFactories(factory
                    .getRenderKitFactories());
        }

    }

    protected void assembleApplicationFactory() {
        applicationFactory_.assemble();
    }

    protected void assembleFacesContextFactory() {
        facesContextFactory_.assemble();
    }

    protected void assembleLifecycleFactory() {
        lifecycleFactory_.assemble();
    }

    protected void assembleRenderKitFactory() {
        renderKitFactory_.assemble();
    }

}
