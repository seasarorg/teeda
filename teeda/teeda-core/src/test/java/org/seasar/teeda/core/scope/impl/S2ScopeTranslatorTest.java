/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import junit.framework.TestCase;

import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.teeda.core.scope.Scope;

/**
 * @author shot
 */
public class S2ScopeTranslatorTest extends TestCase {

    public void testToScope() {
        S2ScopeTranslator translator = new S2ScopeTranslator();
        assertEquals(Scope.NONE, translator.toScope(InstanceDefFactory.OUTER));
        assertEquals(Scope.REQUEST, translator
                .toScope(InstanceDefFactory.REQUEST));
        assertEquals(Scope.SESSION, translator
                .toScope(InstanceDefFactory.SESSION));
        assertEquals(Scope.APPLICATION, translator
                .toScope(InstanceDefFactory.APPLICATION));
    }

    public void testToExternalComponentScope() {
        S2ScopeTranslator translator = new S2ScopeTranslator();
        assertEquals(InstanceDefFactory.OUTER, translator
                .toExternalComponentScope(Scope.NONE));
        assertEquals(InstanceDefFactory.REQUEST, translator
                .toExternalComponentScope(Scope.REQUEST));
        assertEquals(InstanceDefFactory.SESSION, translator
                .toExternalComponentScope(Scope.SESSION));
        assertEquals(InstanceDefFactory.APPLICATION, translator
                .toExternalComponentScope(Scope.APPLICATION));
    }
}
