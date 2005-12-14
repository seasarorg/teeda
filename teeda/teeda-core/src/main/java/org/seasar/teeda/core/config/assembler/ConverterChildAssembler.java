package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.element.ConverterElement;
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
        for(Iterator itr = IteratorUtil.getIterator(targetCovnerters_);itr.hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            converterKey = (String)entry.getKey();
            converterElement = (ConverterElement)entry.getValue();
            if(converterElement != null){
                doAssemble(converterKey, converterElement);
            }
        }
    }

    protected final Application getApplication() {
        return application_;
    }

    protected abstract void doAssemble(String converterKey, ConverterElement converterElement);
}