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
package org.seasar.teeda.core.taglib.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ValueBindingUtil;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.util.ResourceBundleMap;

/**
 * @author shot
 * @author yone
 */
public class LoadBundleTag extends TagSupport {

    private static Logger logger = Logger.getLogger(LoadBundleTag.class);

    private static final long serialVersionUID = 1L;

    private String basename;

    private String var;

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getBasename() {
        return basename;
    }

    public String getVar() {
        return var;
    }

    public int doStartTag() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();
        AssertionUtil.assertNotNull("FacesContext", context);

        UIViewRoot viewRoot = context.getViewRoot();
        AssertionUtil.assertNotNull("ViewRoot", viewRoot);

        Locale locale = viewRoot.getLocale();
        if (locale == null) {
            locale = context.getApplication().getDefaultLocale();
        }
        String bname = null;
        final String basename = getBasename();
        if (basename != null) {
            if (UIComponentTag.isValueReference(basename)) {
                ValueBinding vb = ValueBindingUtil.createValueBinding(context,
                        basename);
                bname = (String) vb.getValue(context);
            } else {
                bname = basename;
            }
        }
        final ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(bname, locale, Thread
                    .currentThread().getContextClassLoader());
        } catch (MissingResourceException e) {
            logger.error("Resource bundle '" + bname + "' could not be found");
            return Tag.SKIP_BODY;
        }

        context.getExternalContext().getRequestMap().put(var,
                new ResourceBundleMap(bundle));

        return Tag.SKIP_BODY;
    }

    public void release() {
        basename = null;
        var = null;
    }

}
