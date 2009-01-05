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
package javax.faces.internal;

import java.util.Map;

import javax.faces.convert.Converter;
import javax.faces.internal.ConverterResource.ConverterPair;

import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;

/**
 * @author shot
 */
public class HotDeployConverterBuilderImpl implements ConverterBuilder {

    private S2Container container;

    public HotDeployConverterBuilderImpl() {
        container = SingletonS2ContainerFactory.getContainer();
    }

    public HotDeployConverterBuilderImpl(S2Container container) {
        this.container = container;
    }

    public Converter build(String expression, ConverterPair[] pairs) {
        if (pairs == null || pairs.length == 0) {
            return null;
        }
        //TODO need ConverterChain? I guess not.
        return getSingleConverter(pairs[0]);
    }

    //see https://www.seasar.org/issues/browse/TEEDA-125
    protected Converter getSingleConverter(ConverterPair pair) {
        final ComponentDef cd = container.getComponentDef(pair.converterName);
        if (!Converter.class.isAssignableFrom(cd.getComponentClass())) {
            return null;
        }
        Map properties = pair.properties;
        if (HotdeployUtil.isHotdeploy()) {
            properties = EnumUtil.convertNameToEnum(properties);
        }
        final Converter converter = (Converter) cd.getComponent();
        BeanUtil.copyProperties(properties, converter);
        return converter;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public void clearAll() {
        //no op;
    }

    public void clearConverter(String expression) {
        //no op;
    }

}
