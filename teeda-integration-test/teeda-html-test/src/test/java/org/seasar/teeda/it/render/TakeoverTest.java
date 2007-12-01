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
package org.seasar.teeda.it.render;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

/**
 * @author manhole
 */
public class TakeoverTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(TakeoverTest.class);
	}

	public void testTakeover1() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/takeover1.html");
		tester.dumpHtml();

		tester.assertTextEqualsById("aaaa", "a1");
		tester.assertTextEqualsById("bbbb", "b1");
		tester.assertTextEqualsById("cccc", "c1");

		tester.submitById("goTakeover2-1");
		tester.dumpHtml();

		// ## Assert ##
		assertTrue(tester.getCurrentUri().contains("takeover2.html"));
		tester.assertTextEqualsById("aaaa", "a1");
		tester.assertTextEqualsById("bbbb", "");
		tester.assertTextEqualsById("cccc", "c1");
	}

	public void testTakeover2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/takeover1.html");
		tester.dumpHtml();

		tester.assertTextEqualsById("aaaa", "a1");
		tester.assertTextEqualsById("bbbb", "b1");
		tester.assertTextEqualsById("cccc", "c1");

		tester.submitById("doSomething1-1");
		tester.dumpHtml();

		// ## Assert ##
		assertTrue(tester.getCurrentUri().contains("takeover2.html"));
		tester.assertTextEqualsById("aaaa", "a1");
		tester.assertTextEqualsById("bbbb", "b1");
		tester.assertTextEqualsById("cccc", "");
	}

	public void testTakeover3() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/takeover1.html");
		tester.dumpHtml();

		tester.assertTextEqualsById("aaaa", "a1");
		tester.assertTextEqualsById("bbbb", "b1");
		tester.assertTextEqualsById("cccc", "c1");

		tester.submitById("doSomething2-1");
		tester.dumpHtml();

		// ## Assert ##
		assertTrue(tester.getCurrentUri().contains("takeover2.html"));
		tester.assertTextEqualsById("aaaa", "");
		tester.assertTextEqualsById("bbbb", "");
		tester.assertTextEqualsById("cccc", "c1");
	}

}
