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
import java.util.HashSet;
import java.util.Set;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.extension.annotation.handler.ValidatorAnnotationHandlerFactory;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author higa
 * 
 */
public class PageDescImpl implements PageDesc {

    private static final String FORM = "Form";

    private String pageName;

    private Set names = new HashSet();

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
            names.add(pd.getPropertyName());
        }
        names.addAll(ActionDescUtil.getActionMethodNames(pageClass));
        ValidatorAnnotationHandlerFactory.getAnnotationHandler()
                .registerValidators(pageName, pageClass);
    }

    public boolean isValid(String id) {
        if (id == null) {
            return false;
        }
        if (names.contains(id)) {
            return true;
        }
        return id.endsWith(FORM);
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
