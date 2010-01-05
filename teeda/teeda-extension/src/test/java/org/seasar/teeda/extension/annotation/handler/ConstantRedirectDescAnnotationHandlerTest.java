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
package org.seasar.teeda.extension.annotation.handler;

import java.util.Map;

import javax.faces.internal.ValidatorResource;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.html.RedirectDesc;

/**
 * @author koichik
 * 
 */
public class ConstantRedirectDescAnnotationHandlerTest extends TeedaTestCase {

    protected void tearDown() {
        ValidatorResource.removeAll();
    }

    public void testGetTakeOverDescs() throws Exception {
        ConstantRedirectDescAnnotationHandler handler = new ConstantRedirectDescAnnotationHandler();
        getContainer().register(HogeBean.class, "hogeBean");
        Map map = handler.getRedirectDescs("hogeBean");
        assertTrue(map.containsKey("initialize"));
        assertTrue(map.containsKey("prerender"));
        assertTrue(map.containsKey("doHoge"));
        assertTrue(map.containsKey("doHoge2"));
        assertTrue(map.containsKey("jumpHoge3"));
        assertFalse(map.containsKey("notAllowedPrefixMethod"));
        assertFalse(map.containsKey("xxx"));

        RedirectDesc rd = (RedirectDesc) map.get("initialize");
        assertEquals(RedirectDesc.HTTP, rd.getProtocol());

        rd = (RedirectDesc) map.get("prerender");
        assertEquals(RedirectDesc.HTTPS, rd.getProtocol());

        rd = (RedirectDesc) map.get("doHoge");
        assertEquals(RedirectDesc.HTTP, rd.getProtocol());

        rd = (RedirectDesc) map.get("doHoge2");
        assertEquals(RedirectDesc.HTTPS, rd.getProtocol());

        rd = (RedirectDesc) map.get("jumpHoge3");
        assertEquals(RedirectDesc.HTTP, rd.getProtocol());
    }

    public static class HogeBean {

        public static final String initialize_REDIRECT = "protocol=http";

        public static final String prerender_REDIRECT = "protocol=https";

        public static final String doHoge_REDIRECT = "protocol=http";

        public static final String doHoge2_REDIRECT = "protocol=https";

        public static final String jumpHoge3_REDIRECT = "protocol=http";

        public static final String notAllowedPrefixMethod_REDIRECT = "scp";

        public String initialize() {
            return null;
        }

        public String prerender() {
            return null;
        }

        public String doHoge() {
            return null;
        }

        public String doHoge2() {
            return null;
        }
    }

}
