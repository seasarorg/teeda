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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.internal.WebAppUtil;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.seasar.teeda.core.util.UIParameterUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 *
 */
public class TValidatorTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    private String validatorId;

    public TValidatorTag() {
    }

    public int doStartTag() throws JspException {
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        if (!tag.getCreated()) {
            return EVAL_PAGE;
        }
        Validator validator = createValidator();
        UIComponent component = tag.getComponentInstance();
        if (component == null || !(component instanceof EditableValueHolder)) {
            throw new JspException(
                    "Component is null or not editable value holder.");
        }
        EditableValueHolder editableValueHolder = (EditableValueHolder) component;
        editableValueHolder.addValidator(validator);
        pageContext.setAttribute(ExtensionConstants.VALIDATOR_STACK_ATTR,
                validator, PageContext.REQUEST_SCOPE);
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        if (!tag.getCreated()) {
            return EVAL_PAGE;
        }
        UIComponent component = tag.getComponentInstance();
        Object attribute = pageContext.getAttribute(
                ExtensionConstants.VALIDATOR_STACK_ATTR,
                PageContext.REQUEST_SCOPE);
        if (attribute instanceof Validator) {
            Validator validator = (Validator) attribute;
            UIParameterUtil.saveParametersToInstance(component, validator);
        }
        pageContext.removeAttribute(ExtensionConstants.VALIDATOR_STACK_ATTR,
                PageContext.REQUEST_SCOPE);

        return super.doEndTag();
    }

    protected Validator createValidator() throws JspException {
        try {
            if (UIComponentTag.isValueReference(validatorId)) {
                validatorId = (String) WebAppUtil
                        .getValueFromCreatedValueBinding(validatorId);
            }
            return WebAppUtil.createValidator(validatorId);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }

    public void release() {
        validatorId = null;
        super.release();
    }

    public String getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

}
