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
package javax.faces.internal;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.Converter;
import javax.faces.internal.ConverterResource.ConverterPair;

import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * @author shot
 */
public class NormalConverterBuilderImpl implements ConverterBuilder {

    private S2Container container;

    private Map converters = new HashMap();

    public NormalConverterBuilderImpl() {
        container = SingletonS2ContainerFactory.getContainer();
    }

    public NormalConverterBuilderImpl(S2Container container) {
        this.container = container;
    }

    public Converter build(String expression, ConverterPair[] pairs) {
        if (pairs == null || pairs.length == 0) {
            return null;
        }
        if (converters.containsKey(expression)) {
            return (Converter) converters.get(expression);
        }
        Converter converter = getSingleConverter(pairs[0]);
        converters.put(expression, converter);
        return converter;
    }

    //see https://www.seasar.org/issues/browse/TEEDA-125
    protected Converter getSingleConverter(ConverterPair pair) {
        final ComponentDef cd = container.getComponentDef(pair.converterName);
        if (!Converter.class.isAssignableFrom(cd.getComponentClass())) {
            return null;
        }
        final Converter converter = (Converter) cd.getComponent();
        BeanUtil.copyProperties(pair.properties, converter);
        return converter;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public void clearAll() {
        converters.clear();
    }

    public void clearConverter(String expression) {
        converters.remove(expression);
    }

}
