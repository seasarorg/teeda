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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.util.PathUtil;

/**
 * @author shot
 */
public class THtmlLink extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlLink";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlLink";

    public static final String DEFAULT_RENDERER_TYPE = COMPONENT_TYPE;

    private String rel;

    private String href;

    private String src;

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        if (!StringUtil.isEmpty(href)) {
            href = PathUtil.toAbsolutePath(context, this, href);
        }
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = rel;
        values[2] = href;
        values[3] = src;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        rel = (String) values[1];
        href = (String) values[2];
        src = (String) values[3];
    }

    public String getRel() {
        if (rel != null) {
            return rel;
        }
        ValueBinding vb = getValueBinding(JsfConstants.REL_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getHref() {
        if (href != null) {
            return href;
        }
        ValueBinding vb = getValueBinding(JsfConstants.HREF_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public String getSrc() {
        if (src != null) {
            return src;
        }
        ValueBinding vb = getValueBinding(JsfConstants.SRC_ATTR);
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
