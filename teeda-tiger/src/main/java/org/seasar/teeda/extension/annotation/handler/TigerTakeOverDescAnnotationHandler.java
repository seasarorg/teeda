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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.teeda.extension.annotation.takeover.TakeOver;
import org.seasar.teeda.extension.html.TakeOverDesc;

/**
 * @author higa
 * @author shot
 */
public class TigerTakeOverDescAnnotationHandler extends ConstantTakeOverDescAnnotationHandler {

    @SuppressWarnings("unchecked")
    @Override
    protected Map getTakeOverDescs(S2Container container, ComponentDef componentDef,
            Class componentClass, String componentName, BeanDesc beanDesc) {
        Map<String, TakeOverDesc> ret = new HashMap<String, TakeOverDesc>();
        Method[] methods = componentClass.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            Method method = methods[i];
            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }
            String methodName = method.getName();
            if (!methodName.startsWith("do") && !methodName.startsWith("go")
                    && !methodName.startsWith("jump")) {
                continue;
            }
            TakeOver takeOver = method.getAnnotation(TakeOver.class);
            if (takeOver == null) {
                continue;
            }
            TakeOverDesc takeOverDesc = createTakeOverDesc(takeOver.type().getName(), takeOver
                    .properties());
            ret.put(methodName, takeOverDesc);
        }
        final Map<String, TakeOverDesc> m = super.getTakeOverDescs(container, componentDef,
                componentClass, componentName, beanDesc);
        if (!m.isEmpty()) {
            ret.putAll(m);
        }
        return ret;
    }

}
