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
package org.seasar.teeda.extension.component.html;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class THtmlTreeHidden extends THtmlInputHidden {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlTreeHidden";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.TreeHidden";

    public THtmlTreeHidden() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void processDecodes(FacesContext context) {
        super.processDecodes(context);
        try {
            updateModelImmediately(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }

    protected void updateModelImmediately(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isValid()) {
            return;
        }
        final ValueBinding valueBinding = getValueBinding("value");
        if (valueBinding == null) {
            return;
        }
        try {
            final Object localValue = getLocalValue();
            valueBinding.setValue(context, localValue);
        } catch (final RuntimeException e) {
            final Object[] args = { getId() };
            context.getExternalContext().log(e.getMessage(), e);
            FacesMessageUtil.addErrorMessage(context, this,
                    CONVERSION_MESSAGE_ID, args);
            setValid(false);
        }
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
    }
}
