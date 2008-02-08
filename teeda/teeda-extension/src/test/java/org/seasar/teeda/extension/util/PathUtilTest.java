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
package org.seasar.teeda.extension.util;

import junit.framework.TestCase;

/**
 * @author koichik
 * 
 */
public class PathUtilTest extends TestCase {

    public void testNormalizePath() throws Exception {
        assertEquals("/a", PathUtil.normalizePath("/a"));
        assertEquals("/a/b", PathUtil.normalizePath("/a/b"));
        assertEquals("/a/b/c", PathUtil.normalizePath("/a/b/c"));
        assertEquals("/a/b/d", PathUtil.normalizePath("/a/b/c/../d"));
        assertEquals("/a/d", PathUtil.normalizePath("/a/b/c/../../d"));
        assertEquals("/a/d/e", PathUtil.normalizePath("/a/b/c/../../d/e"));
    }
}
