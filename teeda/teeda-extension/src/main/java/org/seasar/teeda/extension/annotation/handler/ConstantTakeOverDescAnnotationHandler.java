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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.TakeOverDesc;

/**
 * @author higa
 *
 */
public class ConstantTakeOverDescAnnotationHandler extends
        AbstractTakeOverDescAnnotationHandler {

    protected Map getTakeOverDescs(S2Container container,
            ComponentDef componentDef, Class componentClass,
            String componentName, BeanDesc beanDesc) {

        Map ret = new HashMap();
        int size = beanDesc.getFieldSize();
        for (int i = 0; i < size; ++i) {
            Field field = beanDesc.getField(i);
            boolean isConstantAnnotation = ConstantAnnotationUtil
                    .isConstantAnnotation(field);
            String fieldName = field.getName();
            if (!isConstantAnnotation
                    || !fieldName.endsWith(ExtensionConstants.TAKE_OVER_SUFFIX)) {
                continue;
            }
            String methodName = fieldName.substring(0, fieldName.length()
                    - ExtensionConstants.TAKE_OVER_SUFFIX.length());
            if (!beanDesc.hasMethod(methodName)
                    && !methodName.startsWith(ExtensionConstants.GO_PREFIX)
                    && !methodName.startsWith(ExtensionConstants.JUMP_PREFIX)) {
                continue;
            }
            String s = FieldUtil.getString(field);
            Map m = ConstantAnnotationUtil.convertExpressionToMap(s);
            String type = (String) m.get("type");
            String properties = (String) m.get("properties");
            TakeOverDesc takeOverDesc = createTakeOverDesc(type, properties);
            ret.put(methodName, takeOverDesc);
        }
        return ret;
    }

}
