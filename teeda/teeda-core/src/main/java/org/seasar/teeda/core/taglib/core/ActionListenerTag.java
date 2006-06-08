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

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionListener;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author shot
 */
public class ActionListenerTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String type = null;

    public ActionListenerTag() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int doStartTag() throws JspException {
        UIComponentTag componentTag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (componentTag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        if (!componentTag.getCreated()) {
            return Tag.SKIP_BODY;
        }
        UIComponent component = componentTag.getComponentInstance();
        if (component instanceof ActionSource) {
            String className = getActionListenerClassName();
            ActionListener actionListener = createActionListener(className);
            ((ActionSource) component).addActionListener(actionListener);
        } else {
            throw new JspException("Component:" + component
                    + " is not instance of ActionSource");
        }
        return Tag.SKIP_BODY;
    }

    public void release() {
        type = null;
    }

    protected String getActionListenerClassName() {
        String className = type;
        if (UIComponentTag.isValueReference(type)) {
            FacesContext context = FacesContext.getCurrentInstance();
            ValueBinding vb = context.getApplication().createValueBinding(type);
            className = (String) vb.getValue(context);
        }
        return className;
    }

    protected ActionListener createActionListener(String className) {
        return (ActionListener) ClassUtil.newInstance(className);
    }

}
