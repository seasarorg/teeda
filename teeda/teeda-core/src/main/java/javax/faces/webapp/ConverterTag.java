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
package javax.faces.webapp;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.WebAppUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author shot
 */
public class ConverterTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String converterId = null;

    public ConverterTag() {
    }

    public void setConverterId(String converterId) {
        this.converterId = converterId;
    }

    public int doStartTag() throws JspException {
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("No nested in UIComponentTag");
        }

        if (!tag.getCreated()) {
            return (SKIP_BODY);
        }

        Converter converter = createConverter();

        UIComponent component = tag.getComponentInstance();
        if (component == null || !(component instanceof ValueHolder)) {
            throw new JspException("Component is null or not value holder.");
        }
        ValueHolder valueHolder = (ValueHolder) component;

        valueHolder.setConverter(converter);

        Object localValue = valueHolder.getLocalValue();
        if (localValue instanceof String) {
            try {
                String str = (String) localValue;
                FacesContext context = WebAppUtil.getFacesContext();
                localValue = converter.getAsObject(context, component, str);
                valueHolder.setValue(localValue);
            } catch (ConverterException e) {
                JspException ex = new JspException(e);
                throw ex;
            }
        }
        return SKIP_BODY;
    }

    public void release() {
        converterId = null;
    }

    protected Converter createConverter() throws JspException {
        try {
            String id = this.converterId;
            if (UIComponentTag.isValueReference(id)) {
                id = (String) WebAppUtil.getValueFromCreatedValueBinding(id);
            }
            return WebAppUtil.createConverter(id);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }
}
