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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputCommaText;

/**
 * @author shot
 *
 */
public class TInputCommaTextTag extends InputTextTag {

    private String fraction;

    private String groupingSeparator;

    private String fractionSeparator;

    private String rendererType;

    public String getComponentType() {
        return THtmlInputCommaText.COMPONENT_TYPE;
    }

    public String getRendererType() {
        if (rendererType == null) {
            rendererType = THtmlInputCommaText.DEFAULT_RENDERER_TYPE;
        }
        return rendererType;
    }

    public void setRendererType(String rendererType) {
        this.rendererType = rendererType;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getFractionSeparator() {
        return fractionSeparator;
    }

    public void setFractionSeparator(String fractionSeparator) {
        this.fractionSeparator = fractionSeparator;
    }

    public String getGroupingSeparator() {
        return groupingSeparator;
    }

    public void setGroupingSeparator(String groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        String f = getFraction();
        if (isValueReference(f)) {
            f = BindingUtil.resolveBindingNoException(f);
        }
        setComponentProperty(component, ExtensionConstants.FRACTION_ATTR, f);
        String gs = getGroupingSeparator();
        if (isValueReference(gs)) {
            gs = BindingUtil.resolveBindingNoException(gs);
        }
        setComponentProperty(component,
                ExtensionConstants.GROUPING_SEPARATOR_ATTR, gs);
        String fs = getFractionSeparator();
        if (isValueReference(fs)) {
            fs = BindingUtil.resolveBindingNoException(fs);
        }
        setComponentProperty(component,
                ExtensionConstants.FRACTION_SEPARATOR_ATTR, fs);
    }

}
