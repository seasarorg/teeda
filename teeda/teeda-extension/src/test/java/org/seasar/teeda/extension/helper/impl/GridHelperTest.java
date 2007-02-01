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
package org.seasar.teeda.extension.helper.impl;

import javax.faces.event.PhaseId;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.extension.event.ToggleEvent;

/**
 * @author shot
 */
public class GridHelperTest extends TestCase {

    public void test() throws Exception {
        MockUIComponent component = new MockUIComponent();
        ToggleEvent event = new ToggleEvent(component, "hoge");
        assertEquals(PhaseId.INVOKE_APPLICATION, event.getPhaseId());
    }
}
