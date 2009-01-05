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
package org.seasar.teeda.core.unit;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class UrlDiffTest extends TestCase {

    public void testIdentical1() throws Exception {
        // ## Arrange ##
        UrlDiff diff = new UrlDiff("a", "a");

        // ## Act & Assert ##
        assertEquals(true, diff.isIdentical());
    }

    public void testIdentical2() throws Exception {
        // ## Arrange ##
        UrlDiff diff = new UrlDiff("a", "b");

        // ## Act & Assert ##
        assertEquals(false, diff.isIdentical());
    }

    public void testIdentical3() throws Exception {
        // ## Arrange ##
        UrlDiff diff = new UrlDiff("a?1=2&3=4", "a?3=4&1=2");

        // ## Act & Assert ##
        assertEquals(true, diff.isIdentical());
    }

}
