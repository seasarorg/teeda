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
public class TextTrimmingDifferenceListenerTest extends TestCase {

    public void testIgnoreSpace1() throws Exception {
        Diff myDiff = new Diff("<a> 1</a>", "<a> 1 </a>");
        myDiff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(myDiff.toString(), true, myDiff.similar());
        assertEquals(myDiff.toString(), true, myDiff.identical());
    }

    public void testIgnoreSpace2() throws Exception {
        Diff myDiff = new Diff("<a> </a>", "<a>  </a>");
        myDiff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(myDiff.toString(), true, myDiff.similar());
        assertEquals(myDiff.toString(), true, myDiff.identical());
    }

    public void testIgnoreSpace3() throws Exception {
        Diff myDiff = new Diff("<a></a>", "<a> </a>");
        myDiff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(myDiff.toString(), true, myDiff.similar());
        assertEquals(myDiff.toString(), true, myDiff.identical());
    }

    public void testIgnoreSpace4() throws Exception {
        Diff myDiff = new Diff("<a><b/></a>", "<a> <b/></a>");
        myDiff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(myDiff.toString(), true, myDiff.similar());
        assertEquals(myDiff.toString(), true, myDiff.identical());
    }

    public void testIgnoreSpace4_2() throws Exception {
        Diff myDiff = new Diff("<a><b/></a>", "<a> <c/></a>");
        myDiff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(myDiff.toString(), false, myDiff.similar());
        assertEquals(myDiff.toString(), false, myDiff.identical());
    }

    public void testIgnoreSpace5() throws Exception {
        Diff myDiff = new Diff("<a><b/><c/></a>", "<a> <c/><d/></a>");
        myDiff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(myDiff.toString(), false, myDiff.similar());
        assertEquals(myDiff.toString(), false, myDiff.identical());
    }

    public void testIgnoreSpace6() throws Exception {
        Diff diff = new Diff("<a> <b></b></a>", "<a><b></b></a>");
        diff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
        assertEquals(diff.toString(), true, diff.identical());
    }

}
