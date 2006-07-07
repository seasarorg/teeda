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
package org.seasar.teeda.ajax.autoregister;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.autoregister.ComponentCustomizer;
import org.seasar.framework.container.impl.MetaDefImpl;

/**
 * @author shot
 *
 */
public class AjaxComponentCustomizer implements ComponentCustomizer {

    private List methodNames = new ArrayList();

    public AjaxComponentCustomizer() {
        addMethodPattern("ajaxAction");
    }

    public void customize(ComponentDef componentDef) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentDef
                .getComponentClass());
        String[] names = beanDesc.getMethodNames();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (isMatch(name)) {
                componentDef.addMetaDef(new MetaDefImpl("teeda-ajax"));
            }
        }
    }

    public void addMethodPattern(String methodName) {
        Pattern pattern = Pattern.compile(methodName);
        this.methodNames.add(pattern);
    }

    protected boolean isMatch(String name) {
        for (Iterator itr = methodNames.iterator(); itr.hasNext();) {
            Pattern pattern = (Pattern) itr.next();
            if (pattern.matcher(name).matches()) {
                return true;
            }
        }
        return false;
    }
}
