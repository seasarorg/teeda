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
package org.seasar.teeda.core.render.html;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class UrlParameterTest extends TestCase {

    public void testGetValue() throws Exception {
        UrlParameter p = new UrlParameter();
        assertEquals("", p.getValue());
        assertEquals(0, p.getValues().length);
    }

    public void testSetGetValue() throws Exception {
        UrlParameter p = new UrlParameter();
        p.setValue("aa");
        assertEquals("aa", p.getValue());
        assertEquals(1, p.getValues().length);
    }

    public void testSetValues() throws Exception {
        UrlParameter p = new UrlParameter();
        p.setValues(new String[] { "a", "b", "c" });
        assertEquals("a", p.getValue());
        assertEquals(3, p.getValues().length);
    }

}
