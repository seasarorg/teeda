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
package org.seasar.teeda.extension.component;

import java.util.Map;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.scope.impl.DispatchScopeFactory;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 */
public class ScriptEnhanceUIViewRootTest extends TeedaTestCase {

    public void testAddScript() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript("a", new JavaScriptContext() {
            public String getResult() {
                return "1";
            }

            public boolean hasContext() {
                return true;
            }

        });
        root.addScript("a", new JavaScriptContext());
        assertEquals("1", root.getAllScripts());
    }

    public void testContainsScript() throws Exception {
        TViewRoot root = new TViewRoot();
        assertFalse(root.containsScript("a"));
        root.addScript("a", new JavaScriptContext());
        assertTrue(root.containsScript("a"));
    }

    public void testSaveAndRestore() throws Exception {
        TViewRoot root = new TViewRoot();
        JavaScriptContext c1 = new JavaScriptContext();
        root.addScript("a1", c1);
        JavaScriptContext c2 = new JavaScriptContext();
        root.addScript("a2", c2);
        MockFacesContext facesContext = getFacesContext();
        Object saveState = root.saveState(facesContext);
        root = new TViewRoot();
        root.restoreState(facesContext, saveState);
        saveState = root.saveState(facesContext);
        assertNotNull(saveState);
        Object[] state = (Object[]) saveState;
        assertTrue(state[1] instanceof Map);
    }

}
