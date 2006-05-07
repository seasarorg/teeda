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
package org.seasar.teeda.extension.annotation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.validator.Validator;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.resource.ValidatorResource;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.IteratorUtil;
import org.seasar.teeda.core.validator.ValidatorChain;

/**
 * @author shot
 */
public abstract class AbstractValidatorAnnotationHandler implements
        ValidatorAnnotationHandler {

    private ValidatorResource resource_;

    private List ignoreSuffixes_ = new ArrayList();
    
    public void registerValidator(ComponentDef componentDef) {
        Class targetClass = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(targetClass);
        doRegisterValidator(targetClass, beanDesc);
    }

    public void setValidatorResource(ValidatorResource resources) {
        resource_ = resources;
    }

    public ValidatorResource getValidatorResource() {
        return resource_;
    }

    public void addIgnoreSuffix(String suffix) {
        ignoreSuffixes_.add(suffix);
    }

    protected abstract void doRegisterValidator(Class targetClass, BeanDesc beanDesc);

    protected final Validator chainValidators(List validators) {
        if (validators.size() > 1) {
            ValidatorChain chain = new ValidatorChain();
            for (Iterator itr = IteratorUtil.getIterator(validators); itr
                    .hasNext();) {
                Validator v = (Validator) itr.next();
                chain.add(v);
            }
            return chain;
        } else {
            return (Validator) validators.get(0);
        }
    }

    protected Validator createValidator(String validatorType) {
        Object o = DIContainerUtil.getComponentNoException(validatorType);
        if (o == null) {
            o = DIContainerUtil.getComponent(JsfConstants.TEEDA_NAMESPACE
                    + JsfConstants.NS_SEP + validatorType);
        }
        return (Validator) o;
    }

    protected String getShortClassName(Class clazz) {
        String className = removeIgnoreSuffixes(clazz.getName());
        className = className.substring(clazz.getName().lastIndexOf(".") + 1);
        if (className.indexOf("$") != -1) {
            return className.substring(className.indexOf("$") + 1);
        }
        return className;
    }
    
    protected String removeIgnoreSuffixes(String className) {
        for(Iterator itr = getIgnoreSuffixes().iterator(); itr.hasNext();) {
            String suffix = (String) itr.next();
            if(className.endsWith(suffix)) {
                className = className.substring(0, className.length() - suffix.length());
            }
        }
        return className;
    }
    
    protected List getIgnoreSuffixes() {
        return ignoreSuffixes_;
    }
}
