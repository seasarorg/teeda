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

import java.io.IOException;
import java.util.Locale;

import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.AssertionUtil;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.BodyContent;

import org.seasar.framework.util.LocaleUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.ConverterUtil;
import org.seasar.teeda.core.util.ValueBindingUtil;

/**
 * @author yone
 */
public class ViewTag extends UIComponentBodyTag {

    //private static final String COMPONENT_TYPE = UIViewRoot.COMPONENT_TYPE;
    
    protected String locale_ = null;
    
    public ViewTag() {
        super();
    }
    
    public void setLocale(String local) {
        locale_ = local;
    }

    public String getComponentType() {
        //return COMPONENT_TYPE;
        throw new IllegalStateException();
    }

    public String getRendererType() {
        return null;
    }

    public int doStartTag() throws JspException {
        int rc = 0;
        rc = super.doStartTag();
        FacesContext context = FacesContext.getCurrentInstance();
        AssertionUtil.assertNotNull("FacesContext", context);
        
        pageContext.getResponse().setLocale(context.getViewRoot().getLocale());
        
        ResponseWriter writer = context.getResponseWriter();
        AssertionUtil.assertNotNull("ResponseWriter", writer);
        try {
            writer.startDocument();
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return rc;        
    }
    
    public int doAfterBody() throws JspException {
        // TODO implements
        return 0;
    }
 
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = (Locale) ValueBindingUtil.getValue(context, locale_);
        if (locale == null) {
            locale = LocaleUtil.getLocale(locale_);
        }
        ((UIViewRoot)component).setLocale(locale);
        Config.set(pageContext.getRequest(),Config.FMT_LOCALE, locale);
    }
}
