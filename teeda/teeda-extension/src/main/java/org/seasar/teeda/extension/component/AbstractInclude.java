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
package org.seasar.teeda.extension.component;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author higa
 */
public abstract class AbstractInclude extends UIComponentBase implements
        NamingContainer {

    private transient String includedViewId;

    /**
     * @return Returns the included.
     */
    public boolean isIncluded() {
        return includedViewId != null;
    }

    public String getIncludedViewId() {
        return includedViewId;
    }

    public void setIncludedViewId(String includedViewId) {
        this.includedViewId = includedViewId;
    }

    public void processDecodes(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        decode(context);
    }
}