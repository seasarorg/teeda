package org.seasar.teeda.core.config.element;

import java.util.List;


public interface NavigationRuleElement extends JsfConfig {

    public void setFromViewId(String fromViewId);
    
    public String getFromViewId();
    
    public void addNavigationCase(NavigationCaseElement navigationCase);
    
    public List getNavigationCaseElements();
}
