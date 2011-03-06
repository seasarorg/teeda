/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib.core;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.ConverterUtil;

/**
 * @author yone
 * @author shot
 */
public class VerbatimTag extends UIComponentBodyTag {

    private static final String COMPONENT_TYPE = "javax.faces.Output";

    private static final String RENDER_TYPE = "javax.faces.Text";

    private String escape = null;

    public VerbatimTag() {
        super();
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public String getRendererType() {
        return RENDER_TYPE;
    }

    public void setEscape(String escape) {
        this.escape = escape;
    }

    public String getEscape() {
        return escape;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        final String escapeStr = getEscape();
        if (escapeStr != null) {
            if (BindingUtil.isValueReference(escapeStr)) {
                BindingUtil.setValueBinding(component,
                        JsfConstants.ESCAPE_ATTR, escapeStr);
            } else {
                boolean escape = ConverterUtil.convertToBoolean(escapeStr);
                component.getAttributes().put(JsfConstants.ESCAPE_ATTR,
                        escape ? Boolean.TRUE : Boolean.FALSE);
            }
        } else {
            component.getAttributes().put(JsfConstants.ESCAPE_ATTR,
                    Boolean.FALSE);
        }
        component.setTransient(true);
    }

    public int doAfterBody() throws JspException {
        BodyContent bodyContent = getBodyContent();
        if (bodyContent != null) {
            String value = bodyContent.getString().trim();
            if (value != null) {
                UIOutput component = (UIOutput) getComponentInstance();
                component.setValue(value);
            }
        }
        return super.doAfterBody();
    }

}
