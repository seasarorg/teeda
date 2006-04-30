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
package org.seasar.teeda.core.annotation.factory;

import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.teeda.core.annotation.ConstantValidatorAnnotationHandler;
import org.seasar.teeda.core.annotation.ValidatorAnnotationHandler;
import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author shot
 */
public class ValidatorAnnotationHandlerFactoryImpl implements
        ValidatorAnnotationHandlerFactory {

    private ValidatorAnnotationHandler annotationHandler_;

    public ValidatorAnnotationHandler getAnnotationHandler() {
        return annotationHandler_;
    }

    public void addValidatorAnnotationHandler(String handlerName) {
        Class clazz = null;
        try {
            clazz = ClassUtil.forName(handlerName);
        } catch (ClassNotFoundRuntimeException ignore) {
            clazz = ConstantValidatorAnnotationHandler.class;
        }
        annotationHandler_ = createValidatorAnnotationHandler(clazz);
    }

    protected ValidatorAnnotationHandler createValidatorAnnotationHandler(
            Class clazz) {
        return (ValidatorAnnotationHandler) ClassUtil.newInstance(clazz);
    }

    public void setValidatorAnnotationHandler(
            ValidatorAnnotationHandler annotationHandler) {
        annotationHandler_ = annotationHandler;
    }
    
    public void reloadValidatorAnnotationHandler() {
        //do re-evaluate annotation.
    }

}
