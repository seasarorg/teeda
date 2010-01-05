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

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 * @author yone
 */
public class THtmlOutputText extends HtmlOutputText {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlOutputText";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlOutputText";

    private static final boolean DEFAULT_INVISIBLE = false;

    private static final boolean DEFAULT_OMITTAG = false;

    private String tagName = JsfConstants.SPAN_ELEM;

    // spanタグを出力するかどうか
    private Boolean invisible = null;

    private Boolean omittag = null;

    public THtmlOutputText() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean isInvisible() {
        if (invisible != null) {
            return invisible.booleanValue();
        }
        ValueBinding vb = getValueBinding(ExtensionConstants.INVISIBLE_ATTR);
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_INVISIBLE;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = Boolean.valueOf(invisible);
    }

    public boolean isOmittag() {
        if (omittag != null) {
            return omittag.booleanValue();
        }
        ValueBinding vb = getValueBinding(ExtensionConstants.OMITTAG_ATTR);
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : DEFAULT_OMITTAG;
    }

    public void setOmittag(boolean omittag) {
        this.omittag = Boolean.valueOf(omittag);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = tagName;
        values[2] = invisible;
        values[3] = omittag;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        tagName = (String) values[1];
        invisible = (Boolean) values[2];
        omittag = (Boolean) values[3];
    }

}