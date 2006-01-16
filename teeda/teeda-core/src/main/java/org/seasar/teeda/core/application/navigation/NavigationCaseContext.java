/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.application.navigation;

/**
 * @author shot
 */
public class NavigationCaseContext {

    private String fromAction_;

    private String fromOutcome_;

    private String toViewId_;

    private boolean redirect_ = false;

    public NavigationCaseContext(String fromAction, String outcome, String toViewId,
            boolean redirect) {
        fromAction_ = fromAction;
        fromOutcome_ = outcome;
        toViewId_ = toViewId;
        redirect_ = redirect;
    }

    public NavigationCaseContext(String fromAction, String outcome) {
        this(fromAction, outcome, null, false);
    }

    public String getFromAction() {
        return fromAction_;
    }

    public String getFromOutcome() {
        return fromOutcome_;
    }

    public boolean isRedirect() {
        return redirect_;
    }

    public String getToViewId() {
        return toViewId_;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NavigationCaseContext)) {
            return false;
        }
        NavigationCaseContext navCase = (NavigationCaseContext) obj;
        String fromAction = navCase.getFromAction();
        String fromOutcome = navCase.getFromOutcome();
        
        return (fromAction == null || fromAction.equals(fromAction_)) &&
                (fromOutcome == null || fromOutcome.equals(fromOutcome_));
    }
}