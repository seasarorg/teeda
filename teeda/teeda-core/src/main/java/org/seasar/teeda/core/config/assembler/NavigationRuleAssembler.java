package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.List;

import org.seasar.teeda.core.config.element.NavigationRuleElement;

public abstract class NavigationRuleAssembler extends AbstractJsfAssembler {

    private List navigationRules_ = Collections.EMPTY_LIST;
    
    public NavigationRuleAssembler(List navigationRules){
        isAllSuitableJsfElement(navigationRules, NavigationRuleElement.class);
        navigationRules_ = navigationRules;
        setupBeforeAssemble();
    }
    
    protected final List getNavigationRules(){
        return navigationRules_;
    }
}
