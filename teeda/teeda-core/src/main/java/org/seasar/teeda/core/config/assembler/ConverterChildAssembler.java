package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.application.ConfigurationSupport;
import org.seasar.teeda.core.application.ConverterConfiguration;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.config.element.PropertyElement;
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
                doAssemble(converterKey, converterElement);
                setConverterConfiguration(converterElement);
            }
        }
    }

    protected void setConverterConfiguration(ConverterElement converterElement) {
        if (converterElement == null) {
            return;
        }
        String targetClassName = converterElement.getConverterClass();
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
                    support.addConverterConfiguration(targetClassName,
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

}