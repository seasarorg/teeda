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
 * 
 * TODO : provide easy way to get NavigationCaseContext(generate key and cache to map)
 */
public class NavigationContext {

    private String fromViewId_;

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
        isWildCardMatch_ = fromViewId_.endsWith("*");
    }

    public String getFromViewId() {
        return fromViewId_;
    }

    public List getNavigationCases() {
        return navigationCases_;
    }

    public NavigationCaseContext getNavigationCase(String fromAction, String outCome) {
        for(Iterator itr = getNavigationCases().iterator(); itr.hasNext(); ) {
            NavigationCaseContext caseContext = (NavigationCaseContext) itr.next();
            String from = caseContext.getFromAction();
            String out = caseContext.getFromOutcome();
            if((fromAction == null || fromAction.equals(from)) &&
                (outCome == null || outCome.equals(out))) {
                return caseContext;
            }
        }
        return null;
    }
    
    public boolean isWildCardMatch() {
        return isWildCardMatch_;
    }

}
