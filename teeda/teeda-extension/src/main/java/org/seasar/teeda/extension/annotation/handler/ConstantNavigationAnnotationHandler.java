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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.OgnlUtil;
import org.seasar.teeda.core.application.navigation.NavigationCaseContext;

/**
 * @author higa
 * 
 */
public class ConstantNavigationAnnotationHandler extends
        AbstractNavigationAnnotationHandler {

    private static final String NAVIGATION_SUFFIX = "_NAVIGATION";

    private static final String REDIRECT = "redirect";

    protected NavigationCaseContext createNavigationCaseContext(
            BeanDesc beanDesc, Field field, S2Container container,
            NamingConvention namingConvention) {

        String pageName = (String) FieldUtil.get(field, null);
        if (!container.hasComponentDef(pageName)) {
            return null;
        }
        Boolean redirect = Boolean.TRUE;
        String name = field.getName() + NAVIGATION_SUFFIX;
        if (beanDesc.hasField(name)) {
            String s = (String) beanDesc.getFieldValue(name, null);
            Map m = (Map) OgnlUtil.getValue(OgnlUtil.parseExpression(s),
                    container);
            if (m.containsKey(REDIRECT)) {
                redirect = (Boolean) m.get(REDIRECT);
            }
        }
        String path = namingConvention.fromPageNameToPath(pageName);
        return createNavigationCaseContext(pageName, path, redirect
                .booleanValue());
    }

}
