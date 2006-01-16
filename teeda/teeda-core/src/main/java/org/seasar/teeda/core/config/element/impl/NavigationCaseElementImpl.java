package org.seasar.teeda.core.config.element.impl;

import org.seasar.teeda.core.config.element.NavigationCaseElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class NavigationCaseElementImpl implements NavigationCaseElement {

    private String fromAction_;

    private String fromOutcome_;

    private String toViewId_;

    private boolean redirect_ = false;

    public NavigationCaseElementImpl() {
    }

    public void setFromAction(String fromAction) {
        fromAction_ = fromAction;
    }

    public void setFromOutcome(String outcome) {
        fromOutcome_ = outcome;
    }

    public void setToViewId(String toViewId) {
        if (toViewId == null) {
            throw new IllegalArgumentException("toViewID");
        }
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

    public boolean equals(Object obj) {
        if (!(obj instanceof NavigationCaseElementImpl)) {
            return false;
        }
        NavigationCaseElementImpl navCase = (NavigationCaseElementImpl) obj;
        String fromAction = navCase.getFromAction();
        String fromOutcome = navCase.getFromOutcome();
        String toViewId = navCase.getToViewId();
        return (fromAction == null ? fromAction_ == null : fromAction
                .equals(fromAction_))
                && (fromOutcome == null ? fromOutcome_ == null : fromOutcome
                        .equals(fromOutcome_))
                && (toViewId == null ? toViewId_ == null : toViewId
                        .equals(toViewId));
    }
}
