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
package org.seasar.teeda.extension.annotation.handler;

import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.impl.TakeOverDescImpl;
import org.seasar.teeda.extension.html.impl.TakeOverTypeDescFactory;

/**
 * @author higa
 */
public abstract class AbstractTakeOverDescAnnotationHandler implements
        TakeOverDescAnnotationHandler {

    public Map getTakeOverDescs(String componentName) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class clazz = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        return getTakeOverDescs(container, componentDef, clazz, componentName,
                beanDesc);
    }

    protected abstract Map getTakeOverDescs(S2Container container,
            ComponentDef componentDef, Class componentClass,
            String componentName, BeanDesc beanDesc);

    protected static TakeOverDesc createTakeOverDesc(String typeStr,
            String propertiesStr) {
        TakeOverDescImpl ret = new TakeOverDescImpl();
        ret.setTakeOverTypeDesc(TakeOverTypeDescFactory
                .getTakeOverTypeDesc(typeStr));
        ret.setProperties(StringUtil.split(propertiesStr, ", "));
        return ret;
    }
}