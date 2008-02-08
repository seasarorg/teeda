/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public class DefaultComponentIdLookupStrategyTest extends TeedaTestCase {

    public void test1() throws Exception {
        DefaultComponentIdLookupStrategy strategy = new DefaultComponentIdLookupStrategy();
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        assertEquals("a", strategy.getId(new MockFacesContextImpl(), component));
    }

    public void test2() throws Exception {
        DefaultComponentIdLookupStrategy strategy = new DefaultComponentIdLookupStrategy();
        final MockUIComponent component = new MockUIComponent();
        component.setClientId("a");
        assertEquals("a", strategy.getId(new MockFacesContextImpl(), component));
    }

    public void test3() throws Exception {
        DefaultComponentIdLookupStrategy strategy = new DefaultComponentIdLookupStrategy();
        final MockUIComponent component = new MockUIComponent();
        component.setId("a");
        component.setClientId("b");
        assertEquals("a", strategy.getId(new MockFacesContextImpl(), component));
    }

    public void testGetId_withNamespace() throws Exception {
        DefaultComponentIdLookupStrategy strategy = new DefaultComponentIdLookupStrategy();
        MockExternalContext externalContext = new MockExternalContextImpl() {

            public String encodeNamespace(String value) {
                return "encode:" + value;
            }

        };
        MockFacesContext context = getFacesContext();
        context.setExternalContext(externalContext);
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        String id = strategy.getId(context, mock);
        assertEquals("encode:aaa", id);
    }
}
