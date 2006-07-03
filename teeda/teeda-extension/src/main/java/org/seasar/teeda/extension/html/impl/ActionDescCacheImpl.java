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
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ActionDescCache;

/**
 * @author higa
 * 
 */
public class ActionDescCacheImpl implements ActionDescCache {

    private Map actionDescs = new HashMap();

    public static final String namingConvention_BINDING = "bindingType=must";
    
    private NamingConvention namingConvention;

    private S2Container container;

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public synchronized ActionDesc getActionDesc(String viewId) {
        return (ActionDesc) actionDescs.get(viewId);
    }

    public synchronized ActionDesc createActionDesc(String viewId) {
        ActionDesc actionDesc = null;
        String actionName = namingConvention.fromPathToActionName(viewId);
        if (!container.getRoot().hasComponentDef(actionName)) {
            return null;
        }
        ComponentDef cd = container.getRoot().getComponentDef(actionName);
        File file = ResourceUtil.getResourceAsFileNoException(cd
                .getComponentClass());
        if (file != null) {
            actionDesc = new ActionDescImpl(cd.getConcreteClass(), actionName,
                    file);
        } else {
            actionDesc = new ActionDescImpl(cd.getConcreteClass(), actionName);
        }
        actionDescs.put(viewId, actionDesc);
        return actionDesc;
    }
}