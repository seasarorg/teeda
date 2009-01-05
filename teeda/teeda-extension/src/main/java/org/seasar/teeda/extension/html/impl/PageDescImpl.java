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
package org.seasar.teeda.extension.html.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.annotation.handler.ConverterAnnotationHandlerFactory;
import org.seasar.teeda.extension.annotation.handler.FacesMessageAnnotationHandlerFactory;
import org.seasar.teeda.extension.annotation.handler.RedirectDescAnnotationHandlerFactory;
import org.seasar.teeda.extension.annotation.handler.ScopeAnnotationHandlerFactory;
import org.seasar.teeda.extension.annotation.handler.TakeOverDescAnnotationHandlerFactory;
import org.seasar.teeda.extension.annotation.handler.ValidatorAnnotationHandlerFactory;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.RedirectDesc;
import org.seasar.teeda.extension.html.TakeOverDesc;

/**
 * @author higa
 * @author shot
 */
public class PageDescImpl implements PageDesc, Serializable {

    private static final long serialVersionUID = 1L;

    private String pageName;

    private Set propertyNames = new HashSet();

    private Set itemsPropertyNames = new HashSet();

    private Set mapItemsPropertyNames = new HashSet();

    private Set dynamicPropertyNames = new HashSet();

    private Set methodNames;

    private Map takeOverDescs;

    private Map propertyScopes;

    private Map redirectDescs;

    private File file;

    private long lastModified;

    private static final String[] EMPTY_SCOPES = new String[0];

    public PageDescImpl(Class pageClass, String pageName) {
        this(pageClass, pageName, null);
    }

    public PageDescImpl(Class pageClass, String pageName, File file) {
        this.pageName = pageName;
        setup(pageClass);
        if (file != null) {
            this.file = file;
            lastModified = file.lastModified();
        }
    }

    public String getPageName() {
        return pageName;
    }

    protected void setup(Class pageClass) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(pageClass);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (isItemsProperty(pd)) {
                itemsPropertyNames.add(pd.getPropertyName());
            }
            if (isMapItemsProperty(pd)) {
                mapItemsPropertyNames.add(pd.getPropertyName());
            }
            propertyNames.add(pd.getPropertyName());
            if (isDynamicProperty(pd)) {
                dynamicPropertyNames.add(pd.getPropertyName());
            }
        }
        methodNames = ActionDescUtil.getActionMethodNames(pageClass);
        String pageName = getPageName();
        handleAnnotations(pageName);
    }

    protected void handleAnnotations(String pageName) {
        ValidatorAnnotationHandlerFactory.getAnnotationHandler()
                .registerValidators(pageName);
        ConverterAnnotationHandlerFactory.getAnnotationHandler()
                .registerConverters(pageName);
        takeOverDescs = TakeOverDescAnnotationHandlerFactory
                .getAnnotationHandler().getTakeOverDescs(pageName);
        propertyScopes = ScopeAnnotationHandlerFactory.getAnnotationHandler()
                .getPropertyScopes(pageName);
        redirectDescs = RedirectDescAnnotationHandlerFactory
                .getAnnotationHandler().getRedirectDescs(pageName);
        FacesMessageAnnotationHandlerFactory.getAnnotationHandler()
                .registerFacesMessages(pageName);
    }

    protected boolean isItemsProperty(PropertyDesc pd) {
        if (!pd.getPropertyName().endsWith(ExtensionConstants.ITEMS_SUFFIX)) {
            return false;
        }
        Class clazz = pd.getPropertyType();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    protected boolean isMapItemsProperty(PropertyDesc pd) {
        if (!pd.getPropertyName().endsWith(ExtensionConstants.ITEMS_SUFFIX)) {
            return false;
        }
        Class clazz = pd.getPropertyType();
        return Map.class.isAssignableFrom(clazz);
    }

    protected boolean isDynamicProperty(PropertyDesc pd) {
        return pd.isReadable();
    }

    public boolean isRedirectScopeProperty(String name) {
        Integer scope = (Integer) propertyScopes.get(name);
        if (scope == null) {
            return false;
        }
        return ExtensionConstants.REDIRECT_SCOPE.equals(scope);
    }

    public boolean isSubapplicationScopeProperty(String name) {
        Integer scope = (Integer) propertyScopes.get(name);
        if (scope == null) {
            return false;
        }
        return ExtensionConstants.SUBAPP_SCOPE.equals(scope);
    }

    public boolean isPageScopeProperty(String name) {
        Integer scope = (Integer) propertyScopes.get(name);
        if (scope == null) {
            return false;
        }
        return ExtensionConstants.PAGE_SCOPE.equals(scope);
    }

    public boolean hasProperty(String name) {
        return propertyNames.contains(name);
    }

    public boolean hasItemsProperty(String name) {
        return itemsPropertyNames.contains(name);
    }

    public boolean hasMapItemsProperty(String name) {
        return mapItemsPropertyNames.contains(name);
    }

    public boolean hasDynamicProperty(String name) {
        return dynamicPropertyNames.contains(name);
    }

    public boolean hasMethod(String name) {
        return methodNames.contains(name);
    }

    public TakeOverDesc getTakeOverDesc(String methodName) {
        if (!hasTakeOverDesc(methodName)) {
            throw new IllegalArgumentException(methodName);
        }
        return (TakeOverDesc) takeOverDescs.get(methodName);
    }

    public boolean hasTakeOverDesc(String methodName) {
        return takeOverDescs.containsKey(methodName);
    }

    public boolean hasRedirectDesc(String methodName) {
        return redirectDescs.containsKey(methodName);
    }

    public RedirectDesc getRedirectDesc(String methodName) {
        if (!hasRedirectDesc(methodName)) {
            throw new IllegalArgumentException(methodName);
        }
        return (RedirectDesc) redirectDescs.get(methodName);
    }

    public boolean isModified() {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        return file.lastModified() != lastModified;
    }

    public String[] getPageScopePropertyNames() {
        return getScopePropertyNames(ExtensionConstants.PAGE_SCOPE);
    }

    public String[] getRedirectScopePropertyNames() {
        return getScopePropertyNames(ExtensionConstants.REDIRECT_SCOPE);
    }

    public String[] getSubapplicationScopePropertyNames() {
        return getScopePropertyNames(ExtensionConstants.SUBAPP_SCOPE);
    }

    protected String[] getScopePropertyNames(Integer targetScope) {
        if (propertyScopes == null) {
            return EMPTY_SCOPES;
        }
        List list = new ArrayList();
        for (Iterator itr = propertyScopes.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            Integer scope = (Integer) entry.getValue();
            if (targetScope.equals(scope)) {
                list.add(entry.getKey());
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public boolean hasPageScopeProperty() {
        return propertyScopes.containsValue(ExtensionConstants.PAGE_SCOPE);
    }

    public boolean hasRedirectScopeProperty() {
        return propertyScopes.containsValue(ExtensionConstants.REDIRECT_SCOPE);
    }

    public boolean hasSubapplicationScopeProperty() {
        return propertyScopes.containsValue(ExtensionConstants.SUBAPP_SCOPE);
    }

}
