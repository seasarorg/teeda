/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.util.Map;
import java.util.Set;

import org.seasar.teeda.extension.annotation.handler.RedirectDescAnnotationHandlerFactory;
import org.seasar.teeda.extension.annotation.handler.TakeOverDescAnnotationHandlerFactory;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.RedirectDesc;
import org.seasar.teeda.extension.html.TakeOverDesc;

/**
 * @author higa
 * 
 */
public class ActionDescImpl implements ActionDesc {

    private String actionName;

    private Set methodNames;

    private Map takeOverDescs;

    private Map redirectDescs;

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
        methodNames = ActionDescUtil.getActionMethodNames(actionClass);
        takeOverDescs = TakeOverDescAnnotationHandlerFactory
                .getAnnotationHandler().getTakeOverDescs(actionName);
        redirectDescs = RedirectDescAnnotationHandlerFactory
                .getAnnotationHandler().getRedirectDescs(actionName);
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
        return file.lastModified() != lastModified;
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

}
