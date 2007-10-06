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
package javax.faces.internal;

import java.util.Map;

import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class WindowIdEncodeUrlCustomizerTest extends TeedaTestCase {

    public void testEncodeActionUrl() throws Exception {
        final MockExternalContext externalContext = getExternalContext();
        final Map requestMap = externalContext.getRequestMap();
        requestMap.put(WindowIdUtil.WID, "123");
        final WindowIdEncodeUrlCustomizer customizer = new WindowIdEncodeUrlCustomizer();
        final String encodeActionUrl = customizer.encodeActionUrl(
                externalContext, "http://foo.com/bar/baz.html");
        assertEquals("http://foo.com/bar/baz.html?wid=123", encodeActionUrl);
    }

    public void testEncodeActionUrl2() throws Exception {
        final MockExternalContext externalContext = getExternalContext();
        final Map requestMap = externalContext.getRequestMap();
        requestMap.put(WindowIdUtil.WID, "123");
        final WindowIdEncodeUrlCustomizer customizer = new WindowIdEncodeUrlCustomizer();
        final String encodeActionUrl = customizer.encodeActionUrl(
                externalContext, "http://foo.com/bar/baz.html?aaa=bbb");
        assertEquals("http://foo.com/bar/baz.html?aaa=bbb&wid=123",
                encodeActionUrl);
    }

    public void testEncodeActionUrl_noWindowId() throws Exception {
        final MockExternalContext externalContext = getExternalContext();
        final WindowIdEncodeUrlCustomizer customizer = new WindowIdEncodeUrlCustomizer();
        final String encodeActionUrl = customizer.encodeActionUrl(
                externalContext, "http://foo.com/bar/baz.html?aaa=bbb");
        assertEquals("http://foo.com/bar/baz.html?aaa=bbb", encodeActionUrl);
    }

}
