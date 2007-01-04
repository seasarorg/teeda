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
package javax.faces.component.html;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 * @author higa
 */
public class HtmlInputHidden extends UIInput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlInputHidden";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Hidden";

    public HtmlInputHidden() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        super.processValidators(context);
        if (!isImmediate()) {
            executeValidate(context);
        }
        try {
            updateModelImmediately(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }

    protected void updateModelImmediately(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isValid() || !isLocalValueSet()) {
            return;
        }
        final ValueBinding valueBinding = getValueBinding("value");
        if (valueBinding == null) {
            return;
        }
        try {
            Object localValue = getLocalValue();
            valueBinding.setValue(context, localValue);
        } catch (RuntimeException e) {
            Object[] args = { getId() };
            context.getExternalContext().log(e.getMessage(), e);
            FacesMessageUtil.addErrorComponentMessage(context, this,
                    CONVERSION_MESSAGE_ID, args);
            setValid(false);
        }
    }
}