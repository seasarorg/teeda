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
package org.seasar.teeda.core.taglib.core;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeListener;
import javax.faces.internal.ValueBindingUtil;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.seasar.teeda.core.exception.NoEditableValueHolderRuntimeException;
import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author yone
 */
public class ValueChangeListenerTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String type = null;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int doStartTag() throws JspException {
        final String type = getType();
        if (type == null) {
            throw new JspException("type attribute not set");
        }
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        if (!tag.getCreated()) {
            return SKIP_BODY;
        }

        UIComponent component = tag.getComponentInstance();
        if (component == null) {
            throw new JspException(
                    "No component associated with UIComponentTag");
        }
        if (component instanceof EditableValueHolder) {
            String className = null;
            if (UIComponentTag.isValueReference(type)) {
                FacesContext context = FacesContext.getCurrentInstance();
                ValueBinding vb = ValueBindingUtil.createValueBinding(context,
                        type);
                className = (String) vb.getValue(context);
            } else {
                className = type;
            }
            ValueChangeListener listener = (ValueChangeListener) ClassUtil
                    .newInstance(className);
            ((EditableValueHolder) component).addValueChangeListener(listener);
        } else {
            throw new NoEditableValueHolderRuntimeException(component
                    .getClass());
        }
        return SKIP_BODY;
    }

    public void release() {
        super.release();
        type = null;
    }

}
