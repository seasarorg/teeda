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
package org.seasar.teeda.extension.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.component.TForEach;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;
import org.seasar.teeda.extension.util.KumuDisabledScriptLoader.JavaScriptProviderImpl;

/**
 * @author shot
 */
public class KumuDisabledScriptLoaderTest extends TeedaTestCase {

    public void test1() throws Exception {
        TForEach forEach1 = new TForEach();
        forEach1.setId("fooItemsItems");

        TForEach forEach2 = new TForEach();
        forEach2.setId("fooItems");
        forEach2.setParent(forEach1);
        forEach1.getChildren().add(forEach2);

        THtmlCommandButton button = new THtmlCommandButton();
        button.setId("doOnceAaa");
        button.setParent(forEach2);
        forEach2.getChildren().add(button);

        KumuDisabledScriptLoader loader = new KumuDisabledScriptLoader();
        loader.loadScript(getFacesContext(), button);
        Set r = VirtualResource.getJsResources(getFacesContext());
        assertNotNull(r);
        Iterator iterator = r.iterator();
        assertEquals("org/seasar/teeda/ajax/js/kumu.js", iterator.next());
        assertEquals("org/seasar/teeda/ajax/js/event.js", iterator.next());
        assertEquals("org/seasar/teeda/ajax/js/disabled.js", iterator.next());

        Map r2 = VirtualResource.getInlineJsResources(getFacesContext());
        assertNotNull(r2);
        assertEquals(1, r2.size());
        String key = (String) r2.keySet().iterator().next();
        assertEquals(KumuDisabledScriptLoader.class.getName(), key);

        JavaScriptProviderImpl value = (JavaScriptProviderImpl) r2.get(key);
        assertTrue(value != null);
        assertEquals(1, value.buttons.size());
        assertEquals("doOnceAaa", value.buttons.get(0));

        String script = value.getScript();
        assertTrue(script.indexOf("time : 50000") > 0);
        assertTrue(script.indexOf("submitMessage") > 0);
        System.out.println(script);
    }

}
