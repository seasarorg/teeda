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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlSelectOneMenu;
import org.seasar.teeda.extension.util.NullLabelStrategy;
import org.seasar.teeda.extension.util.NullLabelStrategyImpl;

/**
 * @author higa
 * @author shot
 */
public class TSelectOneMenuTag extends TSelectTagBase {

    private static final NullLabelStrategy defaultNullLabelStrategy = new NullLabelStrategyImpl();

    private String pageName;

    private String labelName;

    private String errorStyleClass;

    private String size;

    public String getComponentType() {
        return THtmlSelectOneMenu.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlSelectOneMenu.RENDERER_TYPE;
    }

    protected void setProperties(final UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, ExtensionConstants.PAGE_NAME_ATTR,
                getPageName());
        setComponentProperty(component, ExtensionConstants.LABEL_NAME_ATTR,
                getLabelName());
        setComponentProperty(component, ExtensionConstants.ERROR_STYLE_CLASS,
                getErrorStyleClass());
        setComponentProperty(component, JsfConstants.SIZE_ATTR, getSize());
    }

    protected boolean isNullLabelRequired() {
        final String v = getValue();
        FacesContext context = getFacesContext();
        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }
        NullLabelStrategy strategy = (NullLabelStrategy) DIContainerUtil
                .getComponentNoException(NullLabelStrategy.class);
        if (strategy == null) {
            strategy = defaultNullLabelStrategy;
        }
        return strategy.isNullLabelRequired(context, v);
    }

    public void release() {
        super.release();
        pageName = null;
        labelName = null;
        errorStyleClass = null;
        size = null;
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

    /**
     * @return Returns the size.
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size The size to set.
     */
    public void setSize(String size) {
        this.size = size;
    }

}
