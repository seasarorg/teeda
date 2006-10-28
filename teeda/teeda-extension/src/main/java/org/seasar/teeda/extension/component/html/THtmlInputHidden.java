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

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author manhole
 */
public class THtmlInputHidden extends UIInput {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputHidden";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Hidden";

    private String pageName;

    public THtmlInputHidden() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public Object getPage(final FacesContext context) {
        final VariableResolver variableResolver = context.getApplication()
                .getVariableResolver();
        final String pageName = getPageName();
        return variableResolver.resolveVariable(context, pageName);
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
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
            updateModel(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }

    public void processUpdates(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = pageName;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        pageName = (String) values[1];
    }

}
