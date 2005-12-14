package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.LifecycleElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class LifecycleElementImpl implements LifecycleElement {

    private List phaseListeners_;
    public LifecycleElementImpl(){
        phaseListeners_ = new ArrayList();
    }
    
    public void addPhaseListener(String phaseListener) {
        phaseListeners_.add(phaseListener);
    }

    public List getPhaseListeners() {
        return phaseListeners_;
    }

}
