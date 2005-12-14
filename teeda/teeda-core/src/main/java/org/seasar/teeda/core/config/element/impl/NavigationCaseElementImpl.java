package org.seasar.teeda.core.config.element.impl;

import org.seasar.teeda.core.config.element.NavigationCaseElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class NavigationCaseElementImpl implements NavigationCaseElement{

    private String fromAction_;
    private String fromOutcome_;
    private String toViewId_ = DEFAULT_TO_VIEW_ID;
    private boolean redirect_ = false;
    public NavigationCaseElementImpl(){
    }

    public void setFromAction(String fromAction) {
        fromAction_ = fromAction;
    }

    public void setFromOutcome(String outcome) {
        fromOutcome_ = outcome;
    }

    public void setToViewId(String toViewId) {
        toViewId_ = toViewId;
    }

    public void setRedirect(String dummy) {
        redirect_ = true;
    }

    public String getFromAction() {
        return fromAction_;
    }

    public String getFromOutcome() {
        return fromOutcome_;
    }

    public String getToViewId() {
        return toViewId_;
    }

    public boolean isRedirect() {
        return redirect_;
    }
    
}
