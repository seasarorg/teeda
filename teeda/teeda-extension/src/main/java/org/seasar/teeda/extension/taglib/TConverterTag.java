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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.convert.Converter;
import javax.faces.internal.WebAppUtil;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.util.UIParameterUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 *
 */
public class TConverterTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    private String converterId;

    private String targetClass;

    public TConverterTag() {
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
        Converter converter = createConverter();
        UIComponent component = tag.getComponentInstance();
        if (component == null || !(component instanceof ValueHolder)) {
            throw new JspException(
                    "Component is null or not editable value holder.");
        }
        ValueHolder valueHolder = (ValueHolder) component;
        valueHolder.setConverter(converter);
        pageContext.setAttribute(ExtensionConstants.CONVERTER_STACK_ATTR,
                converter, PageContext.REQUEST_SCOPE);
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
                ExtensionConstants.CONVERTER_STACK_ATTR,
                PageContext.REQUEST_SCOPE);
        if (attribute instanceof Converter) {
            Converter converter = (Converter) attribute;
            UIParameterUtil.saveParametersToInstance(component, converter);
        }
        pageContext.removeAttribute(ExtensionConstants.CONVERTER_STACK_ATTR,
                PageContext.REQUEST_SCOPE);
        return super.doEndTag();
    }

    protected Converter createConverter() throws JspException {
        Converter converter = null;
        converter = createConverterById(converterId);
        if (converter == null) {
            converter = createConverterByClass(targetClass);
        }
        return converter;
    }

    protected Converter createConverterById(String converterId)
            throws JspException {
        try {
            if (UIComponentTag.isValueReference(converterId)) {
                converterId = (String) WebAppUtil
                        .getValueFromCreatedValueBinding(converterId);
            }
            return WebAppUtil.createConverter(converterId);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }

    protected Converter createConverterByClass(String className)
            throws JspException {
        try {
            if (UIComponentTag.isValueReference(className)) {
                className = (String) WebAppUtil
                        .getValueFromCreatedValueBinding(className);
            }
            Class clazz = ClassUtil.forName(className);
            return WebAppUtil.createConverter(clazz);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }

    public void release() {
        converterId = null;
        targetClass = null;
        super.release();
    }

    public String getConverterId() {
        return converterId;
    }

    public void setConverterId(String converterId) {
        this.converterId = converterId;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

}
