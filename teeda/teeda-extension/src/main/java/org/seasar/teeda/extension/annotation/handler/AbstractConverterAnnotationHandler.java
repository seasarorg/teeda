/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.internal.ConverterResource;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.teeda.core.util.BindingUtil;

/**
 * @author shot
 */
public abstract class AbstractConverterAnnotationHandler implements
        ConverterAnnotationHandler {

    private Map expressionsMap = new HashMap();

    public void registerConverter(String componentName, String propertyName,
            String converterName, Map properties) {
        final String expression = BindingUtil.getExpression(componentName,
                propertyName);
        ConverterResource.addConverter(expression, converterName, properties);
        Set expressions = (Set) expressionsMap.get(componentName);
        if (expressions == null) {
            expressions = new HashSet();
            expressionsMap.put(componentName, expressions);
        }
        expressions.add(expression);
    }

    public void removeConverters(String componentName) {
        Set expressions = (Set) expressionsMap.get(componentName);
        if (expressions == null) {
            return;
        }
        for (Iterator i = expressions.iterator(); i.hasNext();) {
            String expression = (String) i.next();
            ConverterResource.removeConverter(expression);
        }
        expressionsMap.remove(componentName);
    }

    public void removeAll() {
        for (Iterator i = expressionsMap.keySet().iterator(); i.hasNext();) {
            String componentName = (String) i.next();
            removeConverters(componentName);
        }
    }

    public int getExpressionSize(String componentName) {
        Set expressions = (Set) expressionsMap.get(componentName);
        if (expressions == null) {
            return 0;
        }
        return expressions.size();
    }

    protected S2Container getContainer() {
        return SingletonS2ContainerFactory.getContainer();
    }

}