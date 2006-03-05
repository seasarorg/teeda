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
package org.seasar.teeda.core.unit.xmlunit;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;

/**
 * @author manhole
 */
public class IgnoreJsessionidDifferenceListenerTest extends TestCase {

    public void testJsessionid1() throws Exception {
        // ## Arrange ##
        Diff diff = new Diff("<a href=\"bbb\"></a>",
            "<a href=\"bbb;jsessionid=2kn98btcu73r6\"></a>");

        // ## Act ##
        diff
            .overrideDifferenceListener(new IgnoreJsessionidDifferenceListener());

        // ## Assert ##
        assertEquals(diff.toString(), true, diff.similar());
        assertEquals(diff.toString(), false, diff.identical());
    }

    public void testJsessionid2() throws Exception {
        // ## Arrange ##
        Diff diff = new Diff("<a href=\"bbb\" style=\"c\"></a>",
            "<a href=\"bbb;jsessionid=2kn98btcu73r6\" style=\"d\"></a>");

        // ## Act ##
        diff
            .overrideDifferenceListener(new IgnoreJsessionidDifferenceListener());

        // ## Assert ##
        assertEquals(diff.toString(), false, diff.similar());
        assertEquals(diff.toString(), false, diff.identical());
    }

}
