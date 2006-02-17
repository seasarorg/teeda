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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.seasar.framework.log.Logger;

/**
 * @author shot
 * @author yone
 */
public class LoadBundleTag extends TagSupport {
    private static Logger logger_ = Logger.getLogger(LoadBundleTag.class);
    
    private static final long serialVersionUID = 1L;
    
    private String basename_;
    
    private String var_;
    
    public void setBasename(String basename) {
        basename_ = basename;
    }
    
    public void setVar(String var) {
        var_ = var;
    }
    
    public int doStartTag() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            throw new JspException("no faces context");
        }
        
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot == null) {
            throw new JspException("no view root");
        }

        Locale locale = viewRoot.getLocale();
        if (locale == null) {
            locale = context.getApplication().getDefaultLocale();
        }
        String basename = null;
        if (basename_ != null) {
            if (UIComponentTag.isValueReference(basename_)) {
                basename = (String)context.getApplication()
                                .createValueBinding(basename_).getValue(context);
            } else {
                basename = basename_;
            }
        }
        final ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(basename,
                                              locale,
                                              Thread.currentThread().getContextClassLoader());
        } catch (MissingResourceException e) {
            logger_.error("Resource bundle '" + basename + "' could not be found");
            return Tag.SKIP_BODY;
        }
        
        context.getExternalContext().getRequestMap().put(var_, new BundleMap(bundle));

        return Tag.SKIP_BODY;
    }

    private static class BundleMap implements Map {
        private ResourceBundle bundle_;
        private List values_;
        
        public BundleMap(ResourceBundle bundle) {
            bundle_ = bundle;
        }
        
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        public boolean containsKey(Object key) {
            boolean result = false;
            if (key != null) {
                result = (bundle_.getObject(key.toString()) != null);
            }
            return result;
        }
        
        public int hashCode() {
            return bundle_.hashCode();
        }
        
        public boolean isEmpty() {
            boolean result = true;
            Enumeration keys = bundle_.getKeys();
            result = !keys.hasMoreElements();
            return result;
        }

        public Collection values() {
            if (values_ == null) {
                values_ = new ArrayList();
                Enumeration keys = bundle_.getKeys();
                while (keys.hasMoreElements()) {
                    values_.add(bundle_.getString((String)keys.nextElement()));
                }
            }
            return values_;
        }

        public int size() {
            return values().size();
        }

        public boolean containsValue(Object value) {
            return values().contains(value);
        }

        public Set entrySet() {
            HashMap mappings = new HashMap();
            Enumeration keys = bundle_.getKeys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = bundle_.getObject((String)key);
                mappings.put(key, value);
            }
            return mappings.entrySet();
        }
        
        public boolean equals(Object obj) {
            if ((obj == null) || !(obj instanceof Map)) {
                return false;
            }
            if (entrySet().equals(((Map)obj).entrySet())) {
                return true;
            }
            return false;
        }
        
        public Object get(Object key) {
            if (key == null) {
                return null;
            }
            Object result = null;
            try {
                result = bundle_.getObject(key.toString());
            } catch (MissingResourceException e) {
                result = "???" + key + "???";
            }
            return result;
        }

        public Set keySet() {
            Set set = new HashSet();
            for (Enumeration enumer = bundle_.getKeys(); enumer.hasMoreElements(); ) {
                set.add(enumer.nextElement());
            }
            return set;
        }

        public Object remove(Object key) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Map t) {
            throw new UnsupportedOperationException();
        }

        public Object put(Object key, Object value) {
            throw new UnsupportedOperationException();
        }
    }
 
    public void release() {
        basename_ = null;
        var_ = null;
    }
}
