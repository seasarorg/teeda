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
package javax.faces.webapp;

import javax.faces.component.UIComponent;
import javax.faces.internal.WebAppUtils;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author shot
 */
public class AttributeTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String name = null;

    private String value = null;

    public AttributeTag() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        UIComponentTag componentTag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (componentTag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        UIComponent component = componentTag.getComponentInstance();
        if (component == null) {
            throw new JspException(
                    "No component associated with UIComponentTag");
        }
        String nameObj = name;
        if (UIComponentTag.isValueReference(name)) {
            nameObj = (String) WebAppUtils
                    .getValueFromCreatedValueBinding(name);
        }
        Object valueObj = value;
        if (UIComponentTag.isValueReference(value)) {
            valueObj = WebAppUtils.getValueFromCreatedValueBinding(value);
        }
        if (component.getAttributes().get(nameObj) == null) {
            component.getAttributes().put(nameObj, valueObj);
        }
        return SKIP_BODY;
    }

    public void release() {
        name = null;
        value = null;
    }
}
