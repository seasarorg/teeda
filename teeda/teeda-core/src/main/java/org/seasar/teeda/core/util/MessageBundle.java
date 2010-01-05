/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;

/**
 * @author manhole
 */
public class MessageBundle implements Map {

    private final String baseName;

    public MessageBundle(String baseName) {
        if (baseName == null) {
            throw new NullPointerException("baseName");
        }
        if (StringUtil.isBlank(baseName)) {
            throw new IllegalArgumentException("baseName [" + baseName + "]");
        }
        this.baseName = baseName;
    }

    ResourceBundle getResourceBundle() {
        return ResourceBundle
                .getBundle(baseName, getLocale(), getClassLoader());
    }

    private Locale getLocale() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final Locale locale = context.getViewRoot().getLocale();
        if (locale != null) {
            return locale;
        }
        return context.getApplication().getDefaultLocale();
    }

    private ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    Map getResourceBundleMap() {
        return new ResourceBundleMap(getResourceBundle());
    }

    public void clear() {
        getResourceBundleMap().clear();
    }

    public boolean containsKey(Object key) {
        return getResourceBundleMap().containsKey(key);
    }

    public boolean containsValue(Object value) {
        return getResourceBundleMap().containsValue(value);
    }

    public Set entrySet() {
        return getResourceBundleMap().entrySet();
    }

    public Object get(Object key) {
        return getResourceBundleMap().get(key);
    }

    public boolean isEmpty() {
        return getResourceBundleMap().isEmpty();
    }

    public Set keySet() {
        return getResourceBundleMap().keySet();
    }

    public Object put(Object key, Object value) {
        return getResourceBundleMap().put(key, value);
    }

    public void putAll(Map t) {
        getResourceBundleMap().putAll(t);
    }

    public Object remove(Object key) {
        return getResourceBundleMap().remove(key);
    }

    public int size() {
        return getResourceBundleMap().size();
    }

    public Collection values() {
        return getResourceBundleMap().values();
    }

}