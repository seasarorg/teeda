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
package org.seasar.teeda.extension.helper.impl;

import junit.framework.TestCase;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.extension.exception.IllegalLayoutPathRuntimeException;
import org.seasar.teeda.extension.helper.impl.web.aaa.CccPage;

/**
 * @author higa
 * 
 */
public class PathHelperImplTest extends TestCase {

    public void testFromViewRootRelativePathToViewId() throws Exception {
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("/view");
        PathHelperImpl pathHelper = new PathHelperImpl();
        pathHelper.setNamingConvention(nc);
        assertEquals("/view/aaa/bbb.html", pathHelper
                .fromViewRootRelativePathToViewId("/aaa/bbb.html"));
        nc.setViewRootPath("/");
        assertEquals("/aaa/bbb.html", pathHelper
                .fromViewRootRelativePathToViewId("/aaa/bbb.html"));
        try {
            pathHelper.fromViewRootRelativePathToViewId("bbb.html");
            fail();
        } catch (IllegalLayoutPathRuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void testFromPageClassToViewRootRelativePath() throws Exception {
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("/view");
        PathHelperImpl pathHelper = new PathHelperImpl();
        pathHelper.setNamingConvention(nc);
        assertEquals("/aaa/ccc.html", pathHelper
                .fromPageClassToViewRootRelativePath(CccPage.class));
        nc.setViewRootPath("/");
        assertEquals("/aaa/ccc.html", pathHelper
                .fromPageClassToViewRootRelativePath(CccPage.class));
    }
}