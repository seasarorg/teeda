/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlSelectOneRadio;

/**
 * @author shot
 *
 */
public class TSelectOneRadioTag extends TSelectTagBase {

    private String col;

    private String pageName;

    private String labelName;

    public String getComponentType() {
        return THtmlSelectOneRadio.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlSelectOneRadio.DEFAULT_RENDERER_TYPE;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public void release() {
        super.release();
        col = null;
        pageName = null;
        labelName = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, ExtensionConstants.COL_ATTR, col);
        setComponentProperty(component, ExtensionConstants.PAGE_NAME_ATTR,
                pageName);
        setComponentProperty(component, ExtensionConstants.LABEL_NAME_ATTR,
                labelName);
    }

    public String getLabelName() {
        return labelName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

}
