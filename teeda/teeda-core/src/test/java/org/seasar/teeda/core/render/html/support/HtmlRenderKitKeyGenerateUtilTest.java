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
package org.seasar.teeda.core.render.html.support;

import junit.framework.TestCase;

import org.seasar.teeda.core.render.IllegalRendererKeyException;
import org.seasar.teeda.core.render.html.support.HtmlRenderKitKeyGenerateUtil;

/**
 *
 * @author shot
 *
 */
public class HtmlRenderKitKeyGenerateUtilTest extends TestCase {

    public void testGetGeneratedKey() throws Exception {
        assertEquals("a.b", HtmlRenderKitKeyGenerateUtil.getGeneratedKey("a",
                "b"));
        try {
            HtmlRenderKitKeyGenerateUtil.getGeneratedKey(null, "b");
            fail();
        } catch (IllegalRendererKeyException expected) {
        }
        try {
            HtmlRenderKitKeyGenerateUtil.getGeneratedKey("", "b");
            fail();
        } catch (IllegalRendererKeyException expected) {
        }
        try {
            HtmlRenderKitKeyGenerateUtil.getGeneratedKey("a", null);
            fail();
        } catch (IllegalRendererKeyException expected) {
        }
        try {
            HtmlRenderKitKeyGenerateUtil.getGeneratedKey("a", "");
            fail();
        } catch (IllegalRendererKeyException expected) {
        }
        try {
            HtmlRenderKitKeyGenerateUtil.getGeneratedKey("", "");
            fail();
        } catch (IllegalRendererKeyException expected) {
        }
        try {
            HtmlRenderKitKeyGenerateUtil.getGeneratedKey(null, null);
            fail();
        } catch (IllegalRendererKeyException expected) {
        }
    }
}
