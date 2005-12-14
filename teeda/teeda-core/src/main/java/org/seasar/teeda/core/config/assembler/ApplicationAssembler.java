package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Collections;


public abstract class ApplicationAssembler extends AbstractJsfAssembler {

    private List applications_ = Collections.EMPTY_LIST;
    
    public ApplicationAssembler(List applications){
        applications_ = applications;
        setupChildAssembler();
    }
     
    protected final List getApplications(){
        return applications_;
    }
        
}
