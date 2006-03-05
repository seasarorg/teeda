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
package org.seasar.teeda.it;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;

/**
 * @author manhole
 */
public class AbstractTestCaseTest extends AbstractTestCase {

    public void testDiff1() throws Exception {
        Diff diff = diff("<a> <b></b> </a>", "<a><b></b></a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff2() throws Exception {
        Diff diff = diff("<a> <b></b> </a>", "<a><b></b></a>");
        diff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
    }

}
