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
package org.seasar.teeda.extension.taglib;

import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.component.html.HtmlInputCommaText;

/**
 * @author shot
 *
 */
public class InputCommaTextTag extends InputTextTag {

    private String fraction;

    private String groupingSeparator;

    private String fractionSeparator;

    public String getComponentType() {
        return HtmlInputCommaText.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return HtmlInputCommaText.DEFAULT_RENDERER_TYPE;
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

}
