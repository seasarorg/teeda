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
package org.seasar.teeda.core.config.faces.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.faces.element.ConverterElement;

public abstract class ConverterAssembler extends AbstractJsfAssembler {

    private Map convertersByClass_ = Collections.EMPTY_MAP;

    private Map convertersById_ = Collections.EMPTY_MAP;

    private static final Class TARGET_CLASS = ConverterElement.class;

    public ConverterAssembler(Map convertersByClass, Map convertersById) {
        if (convertersByClass == null || convertersById == null) {
            throw new IllegalArgumentException("converter maps");
        }
        isAllSuitableJsfElement(convertersByClass.values(), TARGET_CLASS);
        isAllSuitableJsfElement(convertersById.values(), TARGET_CLASS);
        convertersByClass_ = convertersByClass;
        convertersById_ = convertersById;
        setupBeforeAssemble();
    }

    protected final Map getConvertersByClass() {
        return convertersByClass_;
    }

    protected final Map getConvertersById() {
        return convertersById_;
    }
}
