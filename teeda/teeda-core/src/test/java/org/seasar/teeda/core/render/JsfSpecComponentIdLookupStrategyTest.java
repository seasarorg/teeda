/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.NullFacesContext;

/**
 * @author manhole
 */
public class JsfSpecComponentIdLookupStrategyTest extends TestCase {

    public void test1() throws Exception {
        JsfSpecComponentIdLookupStrategy strategy = new JsfSpecComponentIdLookupStrategy();
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        assertEquals(null, strategy.getId(new NullFacesContext(), component));
    }

    public void test2() throws Exception {
        JsfSpecComponentIdLookupStrategy strategy = new JsfSpecComponentIdLookupStrategy();
        final MockUIComponent component = new MockUIComponent();
        component.setClientId("a");
        assertEquals("a", strategy.getId(new NullFacesContext(), component));
    }

    public void test3() throws Exception {
        JsfSpecComponentIdLookupStrategy strategy = new JsfSpecComponentIdLookupStrategy();
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        component.setClientId("b");
        assertEquals("b", strategy.getId(new NullFacesContext(), component));
    }

}
