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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.Map;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.application.ConfigurationSupport;
import org.seasar.teeda.core.application.ConverterConfiguration;
import org.seasar.teeda.core.config.faces.assembler.ConverterAssembler;
import org.seasar.teeda.core.config.faces.assembler.ConverterChildAssembler;
import org.seasar.teeda.core.config.faces.element.ConverterElement;

/**
 * @author shot
 */
public class DefaultConverterAssembler extends ConverterAssembler {

    private ConverterChildAssembler converterIdAssembler_;

    private ConverterChildAssembler conveterClassAssembler_;

    public DefaultConverterAssembler(Map convertersByClass, Map convertersById) {
        super(convertersByClass, convertersById);
    }

    protected void setupBeforeAssemble() {
        converterIdAssembler_ = new ConverterChildAssemblerById(
                getConvertersById());
        conveterClassAssembler_ = new ConverterChildAssemblerByTargetClass(
                getConvertersByClass());
    }

    public void assemble() {
        converterIdAssembler_.assemble();
        conveterClassAssembler_.assemble();
    }

    private static class ConverterChildAssemblerById extends
            ConverterChildAssembler {

        public ConverterChildAssemblerById(Map targetConverters) {
            super(targetConverters);
        }

        protected void doAssemble(String converterId,
                ConverterElement converterElement) {
            String converterClass = converterElement.getConverterClass();
            if (!StringUtil.isEmpty(converterClass)) {
                getApplication().addConverter(converterId, converterClass);
            }
        }

        protected void doAddConverterConfiguration(
                ConfigurationSupport support, String converterKey,
                ConverterConfiguration configuration) {
            support.addConverterConfiguration(converterKey, configuration);
        }
    }

    private static class ConverterChildAssemblerByTargetClass extends
            ConverterChildAssembler {

        public ConverterChildAssemblerByTargetClass(Map targetConverters) {
            super(targetConverters);
        }

        protected void doAssemble(String converterForClass,
                ConverterElement converterElement) {
            String converterClass = converterElement.getConverterClass();
            if (!StringUtil.isEmpty(converterClass)) {
                Class targetClazz = ClassUtil.forName(converterForClass);
                getApplication().addConverter(targetClazz, converterClass);
            }
        }

        protected void doAddConverterConfiguration(
                ConfigurationSupport support, String converterForClass,
                ConverterConfiguration configuration) {
            Class targetClazz = ClassUtil.forName(converterForClass);
            support.addConverterConfiguration(targetClazz, configuration);
        }
    }

}
