/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.application.ConfigurationSupport;
import org.seasar.teeda.core.application.ConverterConfiguration;
import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;

public abstract class ConverterChildAssembler implements JsfAssembler {

    private Application application_;

    private Map targetCovnerters_ = Collections.EMPTY_MAP;

    public ConverterChildAssembler(Map targetConverters) {
        application_ = ApplicationUtil.getApplicationFromFactory();
        targetCovnerters_ = targetConverters;
    }

    public void assemble() {
        String converterKey = null;
        ConverterElement converterElement = null;
        for (Iterator itr = IteratorUtil.getEntryIterator(targetCovnerters_); itr
                .hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            converterKey = (String) entry.getKey();
            converterElement = (ConverterElement) entry.getValue();
            if (!StringUtil.isEmpty(converterKey) && converterElement != null) {
                setConverterConfiguration(converterKey, converterElement);
                doAssemble(converterKey, converterElement);
            }
        }
    }

    protected void setConverterConfiguration(String converterKey,
            ConverterElement converterElement) {
        if (converterElement == null) {
            return;
        }
        Application app = getApplication();
        if (app instanceof ConfigurationSupport) {
            List properties = converterElement.getPropertyElements();
            ConfigurationSupport support = (ConfigurationSupport) app;
            for (Iterator itr = IteratorUtil.getIterator(properties); itr
                    .hasNext();) {
                PropertyElement property = (PropertyElement) itr.next();
                String propertyName = property.getPropertyName();
                String propertyClass = property.getPropertyClass();
                String defaultValue = property.getDefaultValue();
                if (!StringUtil.isEmpty(propertyName)
                        && !StringUtil.isEmpty(propertyClass)) {
                    doAddConverterConfiguration(support, converterKey,
                            new ConverterConfiguration(propertyName,
                                    propertyClass, defaultValue));
                }
            }
        }
    }

    protected final Application getApplication() {
        return application_;
    }

    protected abstract void doAssemble(String converterKey,
            ConverterElement converterElement);

    protected abstract void doAddConverterConfiguration(
            ConfigurationSupport support, String converterKey,
            ConverterConfiguration configuration);

}