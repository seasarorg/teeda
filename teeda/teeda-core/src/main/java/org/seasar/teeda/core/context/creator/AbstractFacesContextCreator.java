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
package org.seasar.teeda.core.context.creator;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

/**
 * @author shot
 * 
 */
public abstract class AbstractFacesContextCreator implements
        FacesContextCreator {

    private ExternalContextCreator externalContextCreator = new NullExternalContextCreator();

    public FacesContext create(Object context, Object request, Object response,
            Lifecycle lifecycle) {
        ExternalContext externalContext = getExternalContextCreator().create(
                context, request, response);
        return doCreateFacesContext(externalContext);
    }

    public void setExternalContextCreator(
            ExternalContextCreator externalContextCreator) {
        this.externalContextCreator = externalContextCreator;
    }

    public ExternalContextCreator getExternalContextCreator() {
        return externalContextCreator;
    }

    //TODO if need to notify something to Lifecycle, impl this and modify doCreateFacesContext
    protected void handleLifecycle(Lifecycle lifecycle) {
        //do nothing.
    }

    protected abstract FacesContext doCreateFacesContext(
            ExternalContext externalContext);

    private static class NullExternalContextCreator implements
            ExternalContextCreator {

        public ExternalContext create(Object context, Object request,
                Object response) {
            return null;
        }

    }
}
