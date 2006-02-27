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
package org.seasar.teeda.core.taglib;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.webapp.UIComponentTag;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.el.SimpleMethodBinding;
import org.seasar.teeda.core.exception.NoEditableValueHolderRuntimeException;
import org.seasar.teeda.core.exception.NoUICommandRuntimeException;
import org.seasar.teeda.core.exception.NoValueHolderRuntimeException;
import org.seasar.teeda.core.exception.NoValueReferenceRuntimeException;
import org.seasar.teeda.core.util.BindingUtil;

/**
 * @author yone
 */
public abstract class UIComponentTagBase extends UIComponentTag {
   
    private static final Class[] VALIDATOR_ARGTYPES = { FacesContext.class,
        UIComponent.class, Object.class };
    
    private static final Class[] ACTION_LISTENER_ARGS = { ActionEvent.class };
    
    private static final Class[] VALUE_LISTENER_ARGS = { ValueChangeEvent.class };

    
    protected String styleClass_ = null;
    
    protected String title_ = null;
    
    protected String enabledClass_ = null;
    
    protected String disabledClass_ = null;

    protected String onclick_ = null;
    
    protected String ondblclick_ = null;
    
    protected String onmousedown_ = null;
    
    protected String onmouseover_ = null;
    
    protected String onmousemove_ = null;
    
    protected String onmouseout_ = null;
    
    protected String onkeypress_ = null;
    
    protected String onkeydown_ = null;
    
    protected String onkeyup_ = null;

    protected String lang_ = null;
    
    protected String dir_ = null;
    
    protected String height_ = null;
    
    protected String width_ = null;
    
    protected String cellspacing_ = null;
    
    protected String cellpadding_ = null;

    protected String disabled_ = null;
    
    protected String size_ = null;
    
    protected String tabindex_ = null;
    
    protected String checked_ = null;
    
    protected String border_ = null;
    
    protected String readonly_ = null;
    
    protected String ismap_ = null;
    
    protected String maxlength_ = null;
    
    protected String rows_ = null;
    
    protected String cols_ = null;
    
    protected String formatStyle_ = null;
    
    protected String dateStyle_ = null;
    
    protected String timeStyle_ = null;
    
    protected String timezone_ = null;
    
    protected String formatPattern_ = null;

    protected String accept_ = null;
    
    protected String acceptcharset_ = null;
    
    protected String accesskey_ = null;
    
    protected String action_ = null;
    
    protected String alt_ = null;
    
    protected String charset_ = null;
    
    protected String coords_ = null;
    
    protected String enctype_ = null;
    
    protected String htmlFor_ = null;
    
    protected String href_ = null;
    
    protected String hreflang_ = null;
    
    protected String hspace_ = null;

    protected String label_ = null;
    
    protected String longdesc_ = null;
    
    protected String method_ = null;
    
    protected String multiple_ = null;
    
    protected String name_ = null;
    
    protected String onblur_ = null;
    
    protected String onchange_ = null;
    
    protected String onfocus_ = null;
    
    protected String onmouseup_ = null;
    
    protected String onreset_ = null;
    
    protected String onselect_ = null;
    
    protected String onsubmit_ = null;
    
    protected String rel_ = null;
    
    protected String rev_ = null;
    
    protected String selected_ = null;
    
    protected String shape_ = null;
    
    protected String src_ = null;
    
    protected String style_ = null;
    
    protected String target_ = null;
    
    protected String type_ = null;
    
    protected String usemap_ = null;
    
    protected String value_ = null;

    protected String summary_ = null;
    
    protected String bgcolor_ = null;
    
    protected String frame_ = null;
    
    protected String rules_ = null;

    protected String converter_ = null;

    public UIComponentTagBase() {
        super();
    }
    
    public void setAccept(String accept) {
        accept_ = accept;
    }

    public void setAcceptcharset(String acceptcharset) {
        acceptcharset_ = acceptcharset;
    }

    public void setAccesskey(String accesskey) {
        accesskey_ = accesskey;
    }

    public void setAction(String action) {
        action_ = action;
    }

    public void setAlt(String alt) {
        alt_ = alt;
    }

    public void setBgcolor(String bgcolor) {
        bgcolor_ = bgcolor;
    }

    public void setBorder(String border) {
        border_ = border;
    }

    public void setCellpadding(String cellpadding) {
        cellpadding_ = cellpadding;
    }

    public void setCellspacing(String cellspacing) {
        cellspacing_ = cellspacing;
    }

    public void setCharset(String charset) {
        charset_ = charset;
    }

    public void setChecked(String checked) {
        checked_ = checked;
    }

    public void setCols(String cols) {
        cols_ = cols;
    }

    public void setConverter(String converter) {
        converter_ = converter;
    }

    public void setCoords(String coords) {
        coords_ = coords;
    }

    public void setDateStyle(String dateStyle) {
        dateStyle_ = dateStyle;
    }

    public void setDir(String dir) {
        dir_ = dir;
    }

    public void setDisabled(String disabled) {
        disabled_ = disabled;
    }

    public void setDisabledClass(String disabledClass) {
        disabledClass_ = disabledClass;
    }

    public void setEnabledClass(String enabledClass) {
        enabledClass_ = enabledClass;
    }

    public void setEnctype(String enctype) {
        enctype_ = enctype;
    }

    public void setFormatPattern(String formatPattern) {
        formatPattern_ = formatPattern;
    }

    public void setFormatStyle(String formatStyle) {
        formatStyle_ = formatStyle;
    }

    public void setFrame(String frame) {
        frame_ = frame;
    }

    public void setHeight(String height) {
        height_ = height;
    }

    public void setHref(String href) {
        href_ = href;
    }

    public void setHreflang(String hreflang) {
        hreflang_ = hreflang;
    }

    public void setHspace(String hspace) {
        hspace_ = hspace;
    }

    public void setHtmlFor(String htmlFor) {
        htmlFor_ = htmlFor;
    }

    public void setIsmap(String ismap) {
        ismap_ = ismap;
    }

    public void setLabel(String label) {
        label_ = label;
    }

    public void setLang(String lang) {
        lang_ = lang;
    }

    public void setLongdesc(String longdesc) {
        longdesc_ = longdesc;
    }

    public void setMaxlength(String maxlength) {
        maxlength_ = maxlength;
    }

    public void setMethod(String method) {
        method_ = method;
    }

    public void setMultiple(String multiple) {
        multiple_ = multiple;
    }

    public void setName(String name) {
        name_ = name;
    }

    public void setOnblur(String onblur) {
        onblur_ = onblur;
    }

    public void setOnchange(String onchange) {
        onchange_ = onchange;
    }

    public void setOnclick(String onclick) {
        onclick_ = onclick;
    }

    public void setOndblclick(String ondblclick) {
        ondblclick_ = ondblclick;
    }

    public void setOnfocus(String onfocus) {
        onfocus_ = onfocus;
    }

    public void setOnkeydown(String onkeydown) {
        onkeydown_ = onkeydown;
    }

    public void setOnkeypress(String onkeypress) {
        onkeypress_ = onkeypress;
    }

    public void setOnkeyup(String onkeyup) {
        onkeyup_ = onkeyup;
    }

    public void setOnmousedown(String onmousedown) {
        onmousedown_ = onmousedown;
    }

    public void setOnmousemove(String onmousemove) {
        onmousemove_ = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        onmouseout_ = onmouseout;
    }

    public void setOnmouseover(String onmouseover) {
        onmouseover_ = onmouseover;
    }

    public void setOnmouseup(String onmouseup) {
        onmouseup_ = onmouseup;
    }

    public void setOnreset(String onreset) {
        onreset_ = onreset;
    }

    public void setOnselect(String onselect) {
        onselect_ = onselect;
    }

    public void setOnsubmit(String onsubmit) {
        onsubmit_ = onsubmit;
    }

    public void setReadonly(String readonly) {
        readonly_ = readonly;
    }

    public void setRel(String rel) {
        rel_ = rel;
    }

    public void setRev(String rev) {
        rev_ = rev;
    }

    public void setRows(String rows) {
        rows_ = rows;
    }

    public void setRules(String rules) {
        rules_ = rules;
    }

    public void setSelected(String selected) {
        selected_ = selected;
    }

    public void setShape(String shape) {
        shape_ = shape;
    }

    public void setSize(String size) {
        size_ = size;
    }

    public void setSrc(String src) {
        src_ = src;
    }

    public void setStyle(String style) {
        style_ = style;
    }

    public void setStyleClass(String styleClass) {
        styleClass_ = styleClass;
    }

    public void setSummary(String summary) {
        summary_ = summary;
    }

    public void setTabindex(String tabindex) {
        tabindex_ = tabindex;
    }

    public void setTarget(String target) {
        target_ = target;
    }

    public void setTimeStyle(String timeStyle) {
        timeStyle_ = timeStyle;
    }

    public void setTimezone(String timezone) {
        timezone_ = timezone;
    }

    public void setTitle(String title) {
        title_ = title;
    }

    public void setType(String type) {
        type_ = type;
    }

    public void setUsemap(String usemap) {
        usemap_ = usemap;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public void setWidth(String width) {
        width_ = width;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        if (component instanceof ValueHolder) {
            setConverterProperty(component, converter_);
        }
        setComponentProperty(component, JsfConstants.VALUE_ATTR, value_);
        setComponentProperty(component, JsfConstants.FORMAT_PATTERN_ATTR, formatPattern_);      
        setComponentProperty(component, JsfConstants.DATE_STYLE_ATTR, dateStyle_);
        setComponentProperty(component, JsfConstants.TIME_STYLE_ATTR, timeStyle_);
        setComponentProperty(component, JsfConstants.TIMEZONE_ATTR, timezone_);
        setComponentProperty(component, JsfConstants.ONCLICK_ATTR, onclick_);  
        setComponentProperty(component, JsfConstants.ONDBLCLICK_ATTR, ondblclick_);
        setComponentProperty(component, JsfConstants.ONKEYDOWN_ATTR, onkeydown_);
        setComponentProperty(component, JsfConstants.ONKEYPRESS_ATTR, onkeypress_);
        setComponentProperty(component, JsfConstants.ONKEYUP_ATTR, onkeyup_);
        setComponentProperty(component, JsfConstants.ONMOUSEDOWN_ATTR, onmousedown_);
        setComponentProperty(component, JsfConstants.ONMOUSEMOVE_ATTR, onmousemove_);
        setComponentProperty(component, JsfConstants.ONMOUSEOUT_ATTR, onmouseout_);
        setComponentProperty(component, JsfConstants.ONMOUSEOVER_ATTR, onmouseover_);
        setComponentProperty(component, JsfConstants.ONMOUSEUP_ATTR, onmouseup_);
        setComponentProperty(component, JsfConstants.ONFOCUS_ATTR, onfocus_);
        setComponentProperty(component, JsfConstants.ONBLUR_ATTR, onblur_);
        setComponentProperty(component, JsfConstants.TITLE_ATTR, title_);
        setComponentProperty(component, JsfConstants.DISABLED_ATTR, disabled_);
        setComponentProperty(component, JsfConstants.TABINDEX_ATTR, tabindex_);
        setComponentProperty(component, JsfConstants.ACCESSKEY_ATTR, accesskey_);
        setComponentProperty(component, JsfConstants.LANG_ATTR, lang_);
        setComponentProperty(component, JsfConstants.DIR_ATTR, dir_);
        setComponentProperty(component, JsfConstants.STYLE_ATTR, style_);
        setComponentProperty(component, JsfConstants.STYLE_CLASS_ATTR, styleClass_);
    }

    protected void setComponentProperty(UIComponent component,
            String propertyName, String value) {
        if (value == null) {
            return;
        }
        if (BindingUtil.isValueReference(value)) {
            BindingUtil.setValueBinding(component, propertyName, value);
        } else {
            setBeanProperty(component, propertyName, value);
        }
    }
    
    protected void setBeanProperty(UIComponent component,
            String propertyName, String value) {
        
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component
                .getClass());
        if (beanDesc.hasPropertyDesc(propertyName)) {
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            if (pd.hasWriteMethod()) {
                pd.setValue(component, value);
            }
        } else {
            component.getAttributes().put(propertyName, value);
        }
    }

    protected void setConverterProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof ValueHolder)) {
            throw new NoValueHolderRuntimeException(component.getClass());
        }
        if (isValueReference(value)) {
            BindingUtil.setValueBinding(component, JsfConstants.CONVERTER_ATTR,
                    value);
        } else {
            Converter converter = createConverter(value);
            ((ValueHolder) component).setConverter(converter);
        }
    }

    protected void setValidatorProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component.getClass());
        }
        if (!isValueReference(value)) {
            throw new NoValueReferenceRuntimeException(value);
        }
        MethodBinding mb = createMethodBinding(value, VALIDATOR_ARGTYPES);
        ((EditableValueHolder) component).setValidator(mb);
    }

    protected void setActionProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof UICommand)) {
            throw new NoUICommandRuntimeException(component.getClass());
        }
        MethodBinding mb = null;
        if (isValueReference(value)) {
            mb = createMethodBinding(value, null);
        } else {
            mb = new SimpleMethodBinding(value);
        }
        ((UICommand) component).setAction(mb);
    }
    
    protected void setActionListenerProperty(UIComponent component,
            String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof UICommand)) {
            throw new NoUICommandRuntimeException(component.getClass());
        }
        MethodBinding mb = null;
        if (!isValueReference(value)) {
            throw new NoValueReferenceRuntimeException(value);
        }
        mb = createMethodBinding(value, ACTION_LISTENER_ARGS);
        ((UICommand) component).setActionListener(mb);
    }

    protected void setValueChangeListenerProperty(UIComponent component,
            String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component.getClass());
        }
        if (!isValueReference(value)) {
            throw new NoValueReferenceRuntimeException(value);
        }
        MethodBinding mb = createMethodBinding(value, VALUE_LISTENER_ARGS);
        ((EditableValueHolder) component).setValueChangeListener(mb);
        
    }
    
    protected Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    protected Converter createConverter(String value) {
        return getApplication().createConverter(value);
    }

    protected MethodBinding createMethodBinding(String value, Class[] argTypes) {
        return getApplication().createMethodBinding(value, argTypes);
    }
    
}
