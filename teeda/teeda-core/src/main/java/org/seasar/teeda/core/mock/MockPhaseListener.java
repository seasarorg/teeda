package org.seasar.teeda.core.mock;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


public class MockPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;
    private String name_;
    public MockPhaseListener(){
        name_ = this.getClass().getName();
    }

    public MockPhaseListener(String name){
        name_ = name;
    }
    
    public void setName(String name){
        name_ = name;
    }
    
    public void afterPhase(PhaseEvent event) {
    }

    public void beforePhase(PhaseEvent event) {
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public String toString(){
        return name_;
    }
}
