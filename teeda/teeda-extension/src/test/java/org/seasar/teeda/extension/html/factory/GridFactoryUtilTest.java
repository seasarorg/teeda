/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.factory;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class GridFactoryUtilTest extends TestCase {

    public void testIsNoScroll() throws Exception {
        assertEquals(true, GridFactoryUtil.isNoScroll("aaaGrid"));
        assertEquals(false, GridFactoryUtil.isNoScroll("aaaGridX"));
        assertEquals(false, GridFactoryUtil.isNoScroll("aaaGridY"));
        assertEquals(false, GridFactoryUtil.isNoScroll("aaaGridXY"));
    }

    public void testIsScrollHorizontal() throws Exception {
        assertEquals(false, GridFactoryUtil.isScrollHorizontal("aaaGrid"));
        assertEquals(true, GridFactoryUtil.isScrollHorizontal("aaaGridX"));
        assertEquals(false, GridFactoryUtil.isScrollHorizontal("aaaGridY"));
        assertEquals(true, GridFactoryUtil.isScrollHorizontal("aaaGridXY"));
    }

    public void testIsScrollVertical() throws Exception {
        assertEquals(false, GridFactoryUtil.isScrollVertical("aaaGrid"));
        assertEquals(false, GridFactoryUtil.isScrollVertical("aaaGridX"));
        assertEquals(true, GridFactoryUtil.isScrollVertical("aaaGridY"));
        assertEquals(true, GridFactoryUtil.isScrollVertical("aaaGridXY"));
    }

}
