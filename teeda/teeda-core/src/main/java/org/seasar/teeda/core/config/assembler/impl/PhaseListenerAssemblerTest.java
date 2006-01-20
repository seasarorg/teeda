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

import org.seasar.teeda.core.unit.TeedaTestCase;
import java.util.*;

import javax.faces.event.PhaseListener;

import junitx.framework.ArrayAssert;

/**
 * @author shot
 */
public class PhaseListenerAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for PhaseListenerAssemblerTest.
     * 
     * @param name
     */
    public PhaseListenerAssemblerTest(String name) {
        super(name);
    }

    public void testDoAssemble1() throws Exception {
        // # Arrange #
        getLifecycle().removeAllPhaseListener();
        PhaseListenerAssembler assembler = new PhaseListenerAssembler(null,
                getExternalContext());

        // # Act #
        assembler.assemble();

        // # Assert #
        ArrayAssert.assertEquivalenceArrays(getLifecycle().getPhaseListeners(),
                new PhaseListener[0]);
    }
    
    public void testDoAssemble2() throws Exception {
        // # Arrange #
        getLifecycle().removeAllPhaseListener();
        List list = new ArrayList();
        list.add("org.seasar.teeda.core.mock.MockPhaseListener");
        PhaseListenerAssembler assembler = new PhaseListenerAssembler(list,
                getExternalContext());

        // # Act #
        assembler.assemble();

        // # Assert #
        PhaseListener[] listeners = getLifecycle().getPhaseListeners();
    }
}
