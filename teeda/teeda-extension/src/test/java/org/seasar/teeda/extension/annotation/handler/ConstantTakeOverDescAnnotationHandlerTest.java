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
package org.seasar.teeda.extension.annotation.handler;

import java.util.Map;

import javax.faces.internal.ValidatorResource;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.impl.TakeOverTypeDescFactory;

public class ConstantTakeOverDescAnnotationHandlerTest extends TeedaTestCase {

    protected void tearDown() {
        ValidatorResource.removeAll();
    }

    public void testGetTakeOverDescs() throws Exception {
        ConstantTakeOverDescAnnotationHandler handler = new ConstantTakeOverDescAnnotationHandler();
        getContainer().register(HogeBean.class, "hogeBean");
        Map map = handler.getTakeOverDescs("hogeBean");
        assertTrue(map.containsKey("doHoge"));
        assertTrue(map.containsKey("doHoge2"));
        assertTrue(map.containsKey("jumpHoge3"));
        assertFalse(map.containsKey("notAllowedPrefixMethod"));
        assertFalse(map.containsKey("xxx"));
        TakeOverDesc tod = (TakeOverDesc) map.get("doHoge");
        assertEquals(TakeOverTypeDescFactory.INCLUDE, tod.getTakeOverTypeDesc());
        String[] props = tod.getProperties();
        assertEquals(2, props.length);
        assertEquals("aaa", props[0]);
        assertEquals("bbb", props[1]);

        tod = (TakeOverDesc) map.get("doHoge2");
        assertEquals(TakeOverTypeDescFactory.NEVER, tod.getTakeOverTypeDesc());
        props = tod.getProperties();
        assertEquals(0, props.length);

        tod = (TakeOverDesc) map.get("jumpHoge3");
        assertEquals(TakeOverTypeDescFactory.NEVER, tod.getTakeOverTypeDesc());
        props = tod.getProperties();
        assertEquals(0, props.length);
    }

    public static class HogeBean {

        public static final String doHoge_TAKE_OVER = "properties='aaa, bbb'";

        public static final String doHoge2_TAKE_OVER = "type=never";

        public static final String jumpHoge3_TAKE_OVER = "type=never";

        public static final String notAllowedPrefixMethod_TAKE_OVER = "type=never";

        public String doHoge() {
            return null;
        }

        public String doHoge2() {
            return null;
        }
    }
}