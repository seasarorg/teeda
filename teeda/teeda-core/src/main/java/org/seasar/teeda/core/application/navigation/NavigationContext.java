/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author shot
 */
public class NavigationContext {

    protected static final String WILDCARD = "*";
    
    private String fromViewId_ = WILDCARD;

    private boolean isWildCardMatch_ = false;

    private List navigationCases_ = new ArrayList();

    public NavigationContext() {
    }

    public void setFromViewId(String fromViewId) {
        fromViewId_ = fromViewId;
    }

    public void addNavigationCaseContext(
            NavigationCaseContext navigationCaseContext) {
        if (navigationCaseContext != null) {
            navigationCases_.add(navigationCaseContext);
        }
        if (fromViewId_ != null) {
            isWildCardMatch_ = fromViewId_.endsWith(WILDCARD);
        }
    }

    public String getFromViewId() {
        return fromViewId_;
    }

    public List getNavigationCases() {
        return navigationCases_;
    }

    public NavigationCaseContext getNavigationCase(String fromAction,
            String outCome) {
        for (Iterator itr = getNavigationCases().iterator(); itr.hasNext();) {
            NavigationCaseContext caseContext = (NavigationCaseContext) itr
                    .next();
            String from = caseContext.getFromAction();
            String out = caseContext.getFromOutcome();
            if ((from == null || from.equals(fromAction))
                    && (out == null || out.equals(outCome))) {
                return caseContext;
            }
        }
        return null;
    }

    public boolean isWildCardMatch() {
        return isWildCardMatch_;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("navigation-rule = ");
        buf.append("[");
        buf.append("from-view-id = \"" + fromViewId_ + "\"");
        buf.append(" ");
        for (Iterator itr = navigationCases_.iterator(); itr.hasNext();) {
            buf.append(itr.next());
        }
        buf.append("]");
        return buf.toString();
    }
}
