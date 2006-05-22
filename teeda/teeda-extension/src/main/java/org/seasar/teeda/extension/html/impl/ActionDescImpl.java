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
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.seasar.teeda.extension.html.ActionDesc;

/**
 * @author higa
 * 
 */
public class ActionDescImpl implements ActionDesc {

    private String actionName;

    private Set methodNames = new HashSet();

    private File file;

    private long lastModified;

    public ActionDescImpl(Class actionClass, String actionName) {
        this(actionClass, actionName, null);
    }

    public ActionDescImpl(Class actionClass, String actionName, File file) {
        this.actionName = actionName;
        setup(actionClass);
        if (file != null) {
            this.file = file;
            lastModified = file.lastModified();
        }
    }

    public String getActionName() {
        return actionName;
    }

    protected void setup(Class actionClass) {
        Method[] methods = actionClass.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (isMaybeActionMethod(m)) {
                methodNames.add(m.getName());
            }
        }
    }

    protected boolean isMaybeActionMethod(Method method) {
        return method.getReturnType().equals(String.class)
                && method.getParameterTypes().length == 0;
    }

    public boolean isValid(String id) {
        return methodNames.contains(id);
    }

    public boolean isModified() {
        if (file == null) {
            return false;
        }
        return file.lastModified() > lastModified;
    }
}