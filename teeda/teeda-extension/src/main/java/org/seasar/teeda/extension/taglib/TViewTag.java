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

import java.io.IOException;
import java.util.Locale;

import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.PageContextUtil;
import javax.faces.internal.ValueBindingUtil;
import javax.faces.internal.WebAppUtil;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.core.Config;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.LocaleUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.ContentTypeUtil;

/**
 * @author shot
 *
 */
public class TViewTag extends UIComponentTag {

    private static final String COMPONENT_TYPE = UIViewRoot.COMPONENT_TYPE;

    protected String locale = null;

    public TViewTag() {
        super();
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    public int doStartTag() throws JspException {
        final FacesContext context = FacesContext.getCurrentInstance();
        AssertionUtil.assertNotNull("FacesContext", context);
        String encoding = PageContextUtil.getCharacterEncoding(pageContext);
        if (encoding == null || "null".equals(encoding)) {
            ExternalContext externalContext = context.getExternalContext();
            encoding = ((HttpServletRequest) externalContext.getRequest())
                    .getCharacterEncoding();
            if (encoding == null || "null".equals(encoding)) {
                encoding = JsfConstants.DEFAULT_ENCODING;
            }
        }
        final ServletResponse response = pageContext.getResponse();
        final Locale locale = context.getViewRoot().getLocale();
        response.setLocale(locale);
        final String acceptContentTypes = WebAppUtil.getAcceptHeader(context);
        if (acceptContentTypes != null) {
            final String contentType = ContentTypeUtil
                    .getContentType(acceptContentTypes);
            response.setContentType(contentType + "; charset=" + encoding);
        } else {
            response.setContentType(JsfConstants.HTML_CONTENT_TYPE
                    + "; charset=" + encoding);
        }

        final int rc = super.doStartTag();
        final ResponseWriter writer = context.getResponseWriter();
        AssertionUtil.assertNotNull("ResponseWriter", writer);
        try {
            writer.startDocument();
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return rc;
    }

    public int doEndTag() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();
        AssertionUtil.assertNotNull("FacesContext", context);
        StateManager stateManager = context.getApplication().getStateManager();
        stateManager.saveSerializedView(context);
        JspWriter out = pageContext.getOut();
        try {
            out.flush();
        } catch (IOException ignore) {
            //throw new IORuntimeException(e);
        }
        int rc = super.doEndTag();
        ResponseWriter responseWriter = context.getResponseWriter();
        AssertionUtil.assertNotNull("ResponseWriter", responseWriter);
        try {
            responseWriter.endDocument();
        } catch (IOException ignore) {
            //throw new JspException(e.getMessage());
        }
        HttpSession session = null;
        if ((session = pageContext.getSession()) != null) {
            final String encoding = PageContextUtil
                    .getCharacterEncoding(pageContext);
            session.setAttribute(ViewHandler.CHARACTER_ENCODING_KEY, encoding);
        }
        return rc;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        final String localeStr = getLocale();
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = (Locale) ValueBindingUtil.getValue(context, localeStr);
        if (locale == null) {
            locale = LocaleUtil.getLocale(localeStr);
        }
        ((UIViewRoot) component).setLocale(locale);
        Config.set(pageContext.getRequest(), Config.FMT_LOCALE, locale);
    }

}
