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
package org.seasar.teeda.extension.component.html;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class THtmlElementTest extends TeedaTestCase {

    public void testSimple() throws Exception {
        THtmlElement element = new THtmlElement();
        element.setTagName("aaa");
        element.setValueBindingAttribute("bbb", "BBB");
        MockFacesContext context = getFacesContext();
        Object saveState = element.saveState(context);
        THtmlElement element2 = new THtmlElement();
        element2.restoreState(context, saveState);
        assertEquals("aaa", element2.getTagName());
        assertEquals("bbb", element2.getBindingPropertyNames()[0]);
    }

}
