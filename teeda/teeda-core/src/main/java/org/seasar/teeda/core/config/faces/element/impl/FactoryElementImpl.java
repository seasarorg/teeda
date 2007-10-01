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
package org.seasar.teeda.core.config.faces.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.FactoryElement;

/**
 * @author shot
 * 
 */
public class FactoryElementImpl implements FactoryElement {

    private List applicationFactories_;

    private List facesContextFactories_;

    private List lifecycleFactories_;

    private List renderKitFactories_;

    public FactoryElementImpl() {
        applicationFactories_ = new ArrayList();
        facesContextFactories_ = new ArrayList();
        lifecycleFactories_ = new ArrayList();
        renderKitFactories_ = new ArrayList();
    }

    public void addApplicationFactory(String applicationFactory) {
        applicationFactories_.add(applicationFactory);
    }

    public void addFacesContextFactory(String facesContextFactory) {
        facesContextFactories_.add(facesContextFactory);
    }

    public void addLifecycleFactory(String lifecycleFactory) {
        lifecycleFactories_.add(lifecycleFactory);
    }

    public void addRenderKitFactory(String renderKitFactory) {
        renderKitFactories_.add(renderKitFactory);
    }

    public List getApplicationFactories() {
        return applicationFactories_;
    }

    public List getFacesContextFactories() {
        return facesContextFactories_;
    }

    public List getLifecycleFactories() {
        return lifecycleFactories_;
    }

    public List getRenderKitFactories() {
        return renderKitFactories_;
    }

}
