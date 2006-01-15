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
package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.List;

import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationContextFactory;
import org.seasar.teeda.core.config.assembler.NavigationRulesAssembler;
import org.seasar.teeda.core.config.element.NavigationCaseElement;
import org.seasar.teeda.core.config.element.NavigationRuleElement;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class SimpleNavigationRulesAssembler extends NavigationRulesAssembler {

    public SimpleNavigationRulesAssembler(List navigationRules){
        super(navigationRules);
    }
    
    protected void setupChildAssembler() {
        for(Iterator itr = IteratorUtil.getIterator(getNavigationRules()); itr.hasNext();){
            NavigationRuleElement rule = (NavigationRuleElement)itr.next();
            isAllSuitableJsfElement(rule.getNavigationCaseElements(), NavigationCaseElement.class);
        }
    }

    public void assemble() {
        for(Iterator itr = IteratorUtil.getIterator(getNavigationRules()); itr.hasNext();){
            NavigationRuleElement rule = (NavigationRuleElement)itr.next();
            NavigationContextWrapper wrapper = new NavigationContextWrapper(rule);
            NavigationContextFactory.addNavigationContext(getExternalContext(), wrapper);
        }
    }
    
    public static class NavigationContextWrapper extends NavigationContext {
        
        public NavigationContextWrapper(NavigationRuleElement navigationRule){
            String fromViewId = navigationRule.getFromViewId();
            setFromViewId(fromViewId);
            toNavigationCaseContext(navigationRule.getNavigationCaseElements());
        }
        
        private void toNavigationCaseContext(List navigationCases){
            for(Iterator itr = IteratorUtil.getIterator(navigationCases); itr.hasNext();){
                NavigationCaseElement caseElement = (NavigationCaseElement)itr.next();
                addNavigationCaseContext(new NavigationCaseContext(caseElement.getFromAction(), caseElement.getFromOutcome()));
            }
        }
    }
}
