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
package org.seasar.teeda.core.scope.impl;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ScopeManagerImplTest extends TeedaTestCase {

    public void testGetScope() {
        ScopeManagerImpl manager = new ScopeManagerImpl();
        assertEquals(Scope.APPLICATION, manager.getScope(Scope.APPLICATION
                .getScopeKey()));
        assertEquals(Scope.SESSION, manager.getScope(Scope.SESSION
                .getScopeKey()));
        assertEquals(Scope.REQUEST, manager.getScope(Scope.REQUEST
                .getScopeKey()));
        assertEquals(Scope.NONE, manager.getScope(Scope.NONE.getScopeKey()));
        HogeScope hoge = new HogeScope();
        manager.addScope(hoge, "aaa");
        assertEquals(hoge, manager.getScope(hoge.getScopeKey()));
    }

    public void testIsDefaultScope() {
        ScopeManagerImpl manager = new ScopeManagerImpl();
        assertTrue(manager.isDefaultScope(Scope.APPLICATION));
        assertTrue(manager.isDefaultScope(Scope.SESSION));
        assertTrue(manager.isDefaultScope(Scope.REQUEST));
        assertTrue(manager.isDefaultScope(Scope.NONE));

        assertFalse(manager.isDefaultScope(new Scope() {
            public String getScopeKey() {
                return "aaa";
            }

        }));
    }

    private static class HogeScope implements Scope {
        public String getScopeKey() {
            return "hoge";
        }

    }
}
