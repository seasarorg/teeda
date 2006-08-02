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
package org.seasar.teeda.extension.html.impl;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class HtmlPathCacheImplTest extends TestCase {

    private HtmlPathCacheImpl pathCache = new HtmlPathCacheImpl();

    public void testGetName() throws Exception {
        assertEquals("aaa", pathCache.getName("/view/hoge/aaa.html"));
        try {
            pathCache.getName("aaa.html");
            fail();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}