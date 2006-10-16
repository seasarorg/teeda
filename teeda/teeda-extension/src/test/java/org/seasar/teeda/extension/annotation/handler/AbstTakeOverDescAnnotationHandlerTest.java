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
package org.seasar.teeda.extension.annotation.handler;

import junit.framework.TestCase;

import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.impl.TakeOverTypeDescFactory;

/**
 * @author higa
 *
 */
public class AbstTakeOverDescAnnotationHandlerTest extends TestCase {

    public void testCreateTakeOverDesc() throws Exception {
        TakeOverDesc desc = AbstractTakeOverDescAnnotationHandler
                .createTakeOverDesc("never", null);
        assertEquals(TakeOverTypeDescFactory.NEVER, desc.getTakeOverTypeDesc());
        String[] props = desc.getProperties();
        assertEquals(0, props.length);

        desc = AbstractTakeOverDescAnnotationHandler.createTakeOverDesc(
                "include", "aaa, bbb");
        assertEquals(TakeOverTypeDescFactory.INCLUDE, desc
                .getTakeOverTypeDesc());
        props = desc.getProperties();
        assertEquals(2, props.length);
        assertEquals("aaa", props[0]);
        assertEquals("bbb", props[1]);
    }
}