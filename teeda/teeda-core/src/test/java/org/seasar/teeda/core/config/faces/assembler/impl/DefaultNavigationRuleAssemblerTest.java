/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationResource;
import org.seasar.teeda.core.config.faces.element.NavigationCaseElement;
import org.seasar.teeda.core.config.faces.element.NavigationRuleElement;
import org.seasar.teeda.core.config.faces.element.impl.NavigationCaseElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.NavigationRuleElementImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultNavigationRuleAssemblerTest extends TeedaTestCase {

    public void testSetupChildAssembler() throws Exception {
        List list = new ArrayList();
        NavigationRuleElement rule = new NavigationRuleElementImpl();
        list.add(rule);
        DefaultNavigationRuleAssembler assembler = new DefaultNavigationRuleAssembler(
                list);
        assembler.setupBeforeAssemble();
    }

    public void testAssemble() throws Exception {
        // ## Arrange ##
        NavigationRuleElement rule = new NavigationRuleElementImpl();
        rule.setFromViewId("aaa");
        NavigationCaseElement caseElement = new NavigationCaseElementImpl();
        caseElement.setFromAction("bbb");
        caseElement.setFromOutcome("ccc");
        caseElement.setRedirect("");
        caseElement.setToViewId("ddd");
        rule.addNavigationCase(caseElement);
        List list = new ArrayList();
        list.add(rule);
        DefaultNavigationRuleAssembler assembler = new DefaultNavigationRuleAssembler(
                list);

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        Map map = NavigationResource.getNavigationContexts();
        assertTrue(map.containsKey("aaa"));
        List l = (List) map.get("aaa");
        NavigationContext navContext = (NavigationContext) l.get(0);
        assertEquals("aaa", navContext.getFromViewId());
        assertFalse(navContext.isWildCardMatch());
        List cases = navContext.getNavigationCases();
        assertEquals(1, cases.size());
        NavigationCaseContext caseContext = (NavigationCaseContext) cases
                .get(0);
        assertEquals("bbb", caseContext.getFromAction());
        assertEquals("ccc", caseContext.getFromOutcome());
        assertEquals("ddd", caseContext.getToViewId());
        assertTrue(caseContext.isRedirect());
    }

    public void testAssemble_noViewId() throws Exception {
        // ## Arrange ##
        NavigationRuleElement rule = new NavigationRuleElementImpl();
        NavigationCaseElement caseElement = new NavigationCaseElementImpl();
        caseElement.setFromAction("b");
        caseElement.setFromOutcome("c");
        caseElement.setRedirect("");
        caseElement.setToViewId("d");
        rule.addNavigationCase(caseElement);
        List list = new ArrayList();
        list.add(rule);
        DefaultNavigationRuleAssembler assembler = new DefaultNavigationRuleAssembler(
                list);

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        Map map = NavigationResource.getDefaultMatchNavigationContexts();
        assertTrue(map.containsKey("*"));
        List l = (List) map.get("*");
        NavigationContext navContext = (NavigationContext) l.get(0);
        assertEquals("*", navContext.getFromViewId());
        assertTrue(navContext.isWildCardMatch());
        List cases = navContext.getNavigationCases();
        assertEquals(1, cases.size());
        NavigationCaseContext caseContext = (NavigationCaseContext) cases
                .get(0);
        assertEquals("b", caseContext.getFromAction());
        assertEquals("c", caseContext.getFromOutcome());
        assertEquals("d", caseContext.getToViewId());
        assertTrue(caseContext.isRedirect());
    }

    public void testNavigationContextWrapper() throws Exception {
        // ## Arrange ##
        NavigationRuleElement rule = new NavigationRuleElementImpl();
        rule.setFromViewId("aaa*");
        NavigationCaseElement element = new NavigationCaseElementImpl();
        element.setFromAction("outcome");
        rule.addNavigationCase(element);

        // ## Act ##
        DefaultNavigationRuleAssembler.NavigationContextWrapper wrapper = new DefaultNavigationRuleAssembler.NavigationContextWrapper(
                rule);

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
        DefaultNavigationRuleAssembler.NavigationCaseContextWrapper wrapper = new DefaultNavigationRuleAssembler.NavigationCaseContextWrapper(
                element);

        // ## Assert ##
        assertEquals("action", wrapper.getFromAction());
        assertEquals("outcome", wrapper.getFromOutcome());
    }

}
