package org.seasar.teeda.core.config.assembler;

import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;


public class SimpleValidatorsAssembler extends ValidatorsAssembler {

    private Application application_;
    
    public SimpleValidatorsAssembler(Map validators) {
        super(validators);
    }

    protected void setupChildAssembler() {
        application_ = ApplicationUtil.getApplicationFromFactory();
    }

    public void assemble() {
        String validatorId = null;
        String validatorClass = null;
        for(Iterator itr = IteratorUtil.getIterator(getValidators());itr.hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            validatorId = (String)entry.getKey();
            validatorClass = (String)entry.getValue();
            application_.addValidator(validatorId, validatorClass);
        }
    }

}
