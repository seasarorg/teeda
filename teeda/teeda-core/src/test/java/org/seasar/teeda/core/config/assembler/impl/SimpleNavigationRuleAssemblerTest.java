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

import org.seasar.teeda.core.config.element.NavigationCaseElement;
import org.seasar.teeda.core.config.element.NavigationRuleElement;
import org.seasar.teeda.core.config.element.impl.NavigationCaseElementImpl;
import org.seasar.teeda.core.config.element.impl.NavigationRuleElementImpl;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class SimpleNavigationRuleAssemblerTest extends TestCase {

    /**
     * Constructor for SimpleNavigationRuleAssemblerTest.
     * @param name
     */
    public SimpleNavigationRuleAssemblerTest(String name) {
        super(name);
    }

    public void testSetupChildAssembler() throws Exception {
        //TODO
    }
    
    public void testAssemble() throws Exception {
        //TODO
    }

    public void testNavigationContextWrapper() throws Exception {
        // ## Arrange ##
        NavigationRuleElement rule = new NavigationRuleElementImpl();
        rule.setFromViewId("aaa*");
        NavigationCaseElement element = new NavigationCaseElementImpl();
        element.setFromAction("outcome");
        rule.addNavigationCase(element);
        
        // ## Act ##
        SimpleNavigationRulesAssembler.NavigationContextWrapper wrapper = 
            new SimpleNavigationRulesAssembler.NavigationContextWrapper(rule);
        
        // ## Assert ##
        assertEquals("aaa*", wrapper.getFromViewId());
        assertTrue(wrapper.isWildCardMatch());
        assertEquals(1, wrapper.getNavigationCases().size());
    }
    
    public void testNavigationCaseContextWrapper() throws Exception {
        // ## Arrange ##
        NavigationCaseElement element = new NavigationCaseElementImpl();
        element.setFromAction("action");
        element.setFromOutcome("outcome");

        // ## Act ##
        SimpleNavigationRulesAssembler.NavigationCaseContextWrapper wrapper = 
            new SimpleNavigationRulesAssembler.NavigationCaseContextWrapper(element);

        // ## Assert ##
        assertEquals("action", wrapper.getFromAction());
        assertEquals("outcome", wrapper.getFromOutcome());
    }

}
