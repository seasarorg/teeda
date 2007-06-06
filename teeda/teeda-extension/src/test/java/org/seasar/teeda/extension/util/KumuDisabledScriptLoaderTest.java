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
package org.seasar.teeda.extension.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;

/**
 * @author shot
 */
public class KumuDisabledScriptLoaderTest extends TeedaTestCase {

    public void test1() throws Exception {
        KumuDisabledScriptLoader loader = new KumuDisabledScriptLoader();
        THtmlCommandButton button = new THtmlCommandButton();
        button.setId("doOnceAaa");
        loader.loadScript(getFacesContext(), button);
        Set r = VirtualResource.getJsResources(getFacesContext());
        assertNotNull(r);
        Iterator iterator = r.iterator();
        assertEquals("org/seasar/teeda/ajax/js/kumu.js", iterator.next());
        assertEquals("org/seasar/teeda/ajax/js/event.js", iterator.next());
        assertEquals("org/seasar/teeda/ajax/js/disabled.js", iterator.next());

        Map r2 = VirtualResource.getInlineJsResources(getFacesContext());
        assertNotNull(r2);
        String key = (String) r2.keySet().iterator().next();
        String value = (String) r2.get(key);
        assertEquals(button.getClass().getName() + "." + button.getId(), key);
        assertTrue(value != null);
        assertTrue(value.indexOf("time : 50000") > 0);
        assertTrue(value.indexOf("submitMessage") > 0);
    }
}
