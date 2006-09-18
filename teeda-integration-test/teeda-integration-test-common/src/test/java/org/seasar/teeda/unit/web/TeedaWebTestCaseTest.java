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
package org.seasar.teeda.unit.web;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.unit.web.TeedaWebTestCase;

/**
 * @author manhole
 */
public class TeedaWebTestCaseTest extends TeedaWebTestCase {

    public void testDiff1() throws Exception {
        final Diff diff = diff("<a> <b></b> </a>", "<a><b></b></a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff2() throws Exception {
        final Diff diff = diff("<a> <b></b> </a>", "<a><b></b></a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff3() throws Exception {
        final Diff diff = diff("<a href=\"a.jsp\">a</a>",
            "<a href=\"a.jsp;jsessionid=aaaa\">a</a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff4() throws Exception {
        final Diff diff = diff("<a href=\"a.jsp?1=2\">a</a>",
            "<a href=\"a.jsp;jsessionid=aaaa?1=2\">a</a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff5() throws Exception {
        final Diff diff = diff("<a href=\"a.jsp?1=2\">a</a>",
            "<a href=\"a.jsp?1=2;jsessionid=aaaa\">a</a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff6() throws Exception {
        final Diff diff = diff("<a href=\"a.jsp.*\">a</a>",
            "<a href=\"a.jsp?1234=5555\">a</a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

}
