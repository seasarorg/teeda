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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;
import javax.faces.internal.SelectItemsIterator;
import javax.faces.model.SelectItem;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author shot
 * @author manhole
 */
public class THtmlSelectOneMenu extends HtmlSelectOneMenu {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlSelectOneMenu";

    private String pageName;

    private String labelName;

    public void validate(final FacesContext context) {
        super.validate(context);
        THtmlSelectUtil.validate(this);
        final Object selected = getValue();
        final Object page = getPage(context);
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        if (!beanDesc.hasPropertyDesc(getLabelName())) {
            return;
        }
        final PropertyDesc labelPd = beanDesc.getPropertyDesc(getLabelName());

        for (final SelectItemsIterator it = new SelectItemsIterator(this); it
                .hasNext();) {
            final SelectItem item = (SelectItem) it.next();
            final Object v = item.getValue();
            if (v.equals(selected)) {
                final String l = item.getLabel();
                labelPd.setValue(page, l);
                break;
            }
        }
    }

    private Object getPage(final FacesContext context) {
        final VariableResolver variableResolver = context.getApplication()
                .getVariableResolver();
        return variableResolver.resolveVariable(context, getPageName());
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = pageName;
        values[2] = labelName;
        return values;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        pageName = (String) values[1];
        labelName = (String) values[2];
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

}
