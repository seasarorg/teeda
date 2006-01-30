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

public class AttributeTag extends TagSupport {

    private String name_ = null;

    private String value_ = null;

    public AttributeTag() {
    }

    public void setName(String name) {
        name_ = name;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public int doStartTag() throws JspException {
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        UIComponent component = tag.getComponentInstance();
        if (component == null) {
            throw new JspException(
                    "No component associated with UIComponentTag");
        }
        String name = name_;
        if (UIComponentTag.isValueReference(name_)) {
            name = (String) WebAppUtils.getValueFromCreatedValueBinding(name_);
        }
        Object value = value_;
        if (UIComponentTag.isValueReference(value_)) {
            value = WebAppUtils.getValueFromCreatedValueBinding(value_);
        }
        if (component.getAttributes().get(name) == null) {
            component.getAttributes().put(name, value);
        }
        return SKIP_BODY;
    }

    public void release() {
        name_ = null;
        value_ = null;
    }
}
