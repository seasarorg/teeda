package org.seasar.teeda.core.config.assembler;

import java.util.Collections;
import java.util.List;

import javax.faces.context.ExternalContext;

import org.seasar.teeda.core.config.element.NavigationRuleElement;

public abstract class NavigationRuleAssembler extends AbstractJsfAssembler {

    private List navigationRules_ = Collections.EMPTY_LIST;
    
    public NavigationRuleAssembler(List navigationRules, ExternalContext externalContext){
        isAllSuitableJsfElement(navigationRules, NavigationRuleElement.class);
        navigationRules_ = navigationRules;
        setExternalContext(externalContext);
        setupBeforeAssemble();
    }
    
    protected final List getNavigationRules(){
        return navigationRules_;
    }
}
