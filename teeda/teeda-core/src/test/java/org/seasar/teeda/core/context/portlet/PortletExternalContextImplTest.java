/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.context.portlet;

import junit.framework.TestCase;

import org.seasar.framework.mock.portlet.MockPortletContext;
import org.seasar.framework.mock.portlet.MockPortletContextImpl;
import org.seasar.framework.mock.portlet.MockPortletRenderResponseImpl;
import org.seasar.framework.mock.portlet.MockPortletRequestImpl;

/**
 * @author manhole
 */
public class PortletExternalContextImplTest extends TestCase {

    public void testEncodeResourceURL_http() throws Exception {
        // ## Arrange ##
        final boolean[] calls = new boolean[] { false };
        final MockPortletContext portletContext = new MockPortletContextImpl(
                "/foo");
        final MockPortletRenderResponseImpl portletRenderResponse = new MockPortletRenderResponseImpl() {
            public String encodeURL(String arg0) {
                calls[0] = true;
                return super.encodeURL(arg0);
            }
        };
        final MockPortletRequestImpl portletRequest = new MockPortletRequestImpl(
                portletContext);
        final PortletExternalContextImpl externalContext = new PortletExternalContextImpl(
                portletContext, portletRequest, portletRenderResponse);

        // ## Act ##
        externalContext.encodeResourceURL("http://aaaaa.bbb/");

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testEncodeResourceURL_mailto() throws Exception {
        // ## Arrange ##
        final boolean[] calls = new boolean[] { false };
        final MockPortletContext portletContext = new MockPortletContextImpl(
                "/foo");
        final MockPortletRenderResponseImpl portletRenderResponse = new MockPortletRenderResponseImpl() {
            public String encodeURL(String arg0) {
                calls[0] = true;
                return super.encodeURL(arg0);
            }
        };
        final MockPortletRequestImpl portletRequest = new MockPortletRequestImpl(
                portletContext);
        final PortletExternalContextImpl externalContext = new PortletExternalContextImpl(
                portletContext, portletRequest, portletRenderResponse);

        // ## Act ##
        externalContext.encodeResourceURL("mailto:aaa@bbbb");

        // ## Assert ##
        assertEquals(false, calls[0]);
    }

}
