/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component.html;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class THtmlInputTextTest extends TeedaTestCase {

    public void testSaveAndRestore() throws Exception {
        THtmlInputText t = new THtmlInputText();
        t.setErrorStyleClass("aaa");
        MockFacesContext context = getFacesContext();
        Object saveState = t.saveState(context);
        THtmlInputText t2 = new THtmlInputText();
        assertEquals("onTeedaError", t2.getErrorStyleClass());
        t2.restoreState(context, saveState);
        assertEquals("aaa", t2.getErrorStyleClass());
    }
}
