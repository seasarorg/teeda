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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;

/**
 * @author shot
 * @author manhole
 */
public class THtmlSelectOneMenu extends HtmlSelectOneMenu {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlSelectOneMenu";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.HtmlSelectOneMenu";

    private String pageName;

    private String labelName;

    private Integer size = null;

    private static final String VALIDATION_FAIL_CSS = "onTeedaError";

    private String errorStyleClass = VALIDATION_FAIL_CSS;

    public void validate(final FacesContext context) {
        super.validate(context);
        THtmlSelectUtil.validate(this);
        THtmlSelectUtil.takeOverLabel(context, this, getPage(context),
                getLabelName());
    }

    public void updateModel(FacesContext context) {
        super.updateModel(context);
        THtmlSelectUtil.takeOverLabel(context, this, getPage(context),
                getLabelName());
    }

    private Object getPage(final FacesContext context) {
        final VariableResolver variableResolver = context.getApplication()
                .getVariableResolver();
        return variableResolver.resolveVariable(context, getPageName());
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[5];
        values[0] = super.saveState(context);
        values[1] = pageName;
        values[2] = labelName;
        values[3] = errorStyleClass;
        values[4] = size;
        return values;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        pageName = (String) values[1];
        labelName = (String) values[2];
        errorStyleClass = (String) values[3];
        size = (Integer) values[4];
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(final String labelName) {
        this.labelName = labelName;
    }

    public String getErrorStyleClass() {
        return errorStyleClass;
    }

    public void setErrorStyleClass(String errorStyleClass) {
        this.errorStyleClass = errorStyleClass;
    }

    public int getSize() {
        if (size != null) {
            return size.intValue();
        }
        ValueBinding vb = getValueBinding("size");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : 1;
    }

    public void setSize(int size) {
        this.size = new Integer(size);
    }
}
