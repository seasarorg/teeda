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
package org.seasar.teeda.extension.html.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.annotation.handler.ValidatorAnnotationHandlerFactory;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author higa
 * 
 */
public class PageDescImpl implements PageDesc {

    private String pageName;

    private Set propertyNames = new HashSet();

    private Set itemsPropertyNames = new HashSet();

    private Set methodNames;

    private File file;

    private long lastModified;

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
            } else {
                propertyNames.add(pd.getPropertyName());
            }
        }
        methodNames = ActionDescUtil.getActionMethodNames(pageClass);
        ValidatorAnnotationHandlerFactory.getAnnotationHandler()
                .registerValidators(pageName, pageClass);
    }

    protected boolean isItemsProperty(PropertyDesc pd) {
        if (!pd.getPropertyName().endsWith(ExtensionConstants.ITEMS_SUFFIX)) {
            return false;
        }
        Class clazz = pd.getPropertyType();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    public boolean hasProperty(String name) {
        return propertyNames.contains(name);
    }

    public boolean hasItemsProperty(String name) {
        return itemsPropertyNames.contains(name);
    }

    public boolean hasMethod(String name) {
        return methodNames.contains(name);
    }

    public boolean isModified() {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        return file.lastModified() > lastModified;
    }

}
