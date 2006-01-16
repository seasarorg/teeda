package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Collections;

import org.seasar.teeda.core.config.element.ApplicationElement;


public abstract class ApplicationAssembler extends AbstractJsfAssembler {

    private List applications_ = Collections.EMPTY_LIST;
    
    public ApplicationAssembler(List applications){
        isAllSuitableJsfElement(applications, ApplicationElement.class);
        applications_ = applications;
        setupBeforeAssemble();
    }
     
    protected final List getApplications(){
        return applications_;
    }
        
}
