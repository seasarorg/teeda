package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.NavigationCaseElement;
import org.seasar.teeda.core.config.element.NavigationRuleElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class NavigationRuleElementImpl implements NavigationRuleElement{

    private String fromViewId_ = DEFAULT_FROM_VIEW_ID;
    private List navigationCases_ = new ArrayList();
    public NavigationRuleElementImpl(){
    }

    public void setFromViewId(String fromViewId) {
        fromViewId_ = fromViewId;
    }

    public String getFromViewId() {
        return fromViewId_;
    }

    public void addNavigationCase(NavigationCaseElement navigationCase) {
        navigationCases_.add(navigationCase);
    }

    public List getNavigationCaseElements() {
        return navigationCases_;
    }
}
