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
package org.seasar.teeda.core.context.creator.servlet;

import javax.faces.context.ExternalContext;
import javax.faces.internal.EncodeUrlCustomizer;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class ServletExternalContextCreatorTest extends TeedaTestCase {

    public void testCreate() throws Exception {
        ServletExternalContextCreator creator = new ServletExternalContextCreator();
        ExternalContext ctx = creator.create(getServletContext(), getRequest(),
                getResponse());
        assertNotNull(ctx);
    }

    public void testCreateCustomize() throws Exception {
        register(new EncodeUrlCustomizer() {

            private static final long serialVersionUID = 1L;

            public String encodeActionUrl(ExternalContext externalContext,
                    String url) {
                return "aaa";
            }

            public String encodeNamespace(String name) {
                return null;
            }

            public String encodeResourceUrl(ExternalContext externalContext,
                    String url) {
                return null;
            }

        });
        ServletExternalContextCreator creator = new ServletExternalContextCreator();
        ExternalContext ctx = creator.create(getServletContext(), getRequest(),
                getResponse());
        assertNotNull(ctx);
        assertEquals("aaa", ctx.encodeActionURL(""));
    }
}
