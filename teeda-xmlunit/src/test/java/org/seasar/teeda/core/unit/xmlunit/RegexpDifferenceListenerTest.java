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
package org.seasar.teeda.core.unit.xmlunit;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;

/**
 * @author manhole
 */
public class RegexpDifferenceListenerTest extends TestCase {

    public void testRegexp1() throws Exception {
        Diff diff = new Diff("<a>1..</a>", "<a>123</a>");
        diff.overrideDifferenceListener(new RegexpDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
        assertEquals(diff.toString(), false, diff.identical());
    }

    public void testRegexp2() throws Exception {
        Diff diff = new Diff("<a>1..</a>", "<a>1234</a>");
        diff.overrideDifferenceListener(new RegexpDifferenceListener());
        assertEquals(diff.toString(), false, diff.similar());
        assertEquals(diff.toString(), false, diff.identical());
    }

    public void testRegexp3() throws Exception {
        Diff diff = new Diff("<a href=\"a.jsp.*\">a</a>",
                "<a href=\"a.jsp?1234=5555\">a</a>");
        diff.overrideDifferenceListener(new RegexpDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
    }

}
