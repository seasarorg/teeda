/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.internal.RenderPreparable;
import javax.faces.internal.RenderPreparableUtil;

/**
 * @author shot
 *
 */
public class THtmlInputCommaText extends THtmlInputText implements
        RenderPreparable {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputCommaText";

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlInputCommaText";

    private String fraction;

    private String groupingSeparator;

    private String fractionSeparator;

    public THtmlInputCommaText() {
        setRendererType(DEFAULT_RENDERER_TYPE);
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

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = fraction;
        values[2] = groupingSeparator;
        values[3] = fractionSeparator;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        fraction = (String) values[1];
        groupingSeparator = (String) values[2];
        fractionSeparator = (String) values[3];
    }

    public void preEncodeBegin(FacesContext context) throws IOException {
        RenderPreparableUtil.encodeBeforeForRenderer(context, this);
    }

    public void postEncodeEnd(FacesContext context) throws IOException {
    }

}
