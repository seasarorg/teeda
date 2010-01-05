/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

	public void testTakeover_Default() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doDefault");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "a");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "c");
		tester.assertTextEqualsById("redirect2", "d");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "f");

		// ## Act ##
		tester.clickLinkById("reload");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "a");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "f");
	}

	public void testTakeover_Never() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doNever");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "");
		tester.assertTextEqualsById("subapp2", "");
	}

	public void testTakeover_IncludeRedirect1() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doIncludeRedirect1");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "c");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "");
		tester.assertTextEqualsById("subapp2", "");

		// ## Act ##
		tester.clickLinkById("reload");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "");
		tester.assertTextEqualsById("subapp2", "");
	}

	public void testTakeover_ExcludeRedirect2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doExcludeRedirect2");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "a");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "c");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "f");

		// ## Act ##
		tester.clickLinkById("reload");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "a");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "f");
	}

	public void testTakeover_IncludeSubapp1() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doIncludeSubapp1");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "");

		// ## Act ##
		tester.clickLinkById("reload");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "");
	}

	public void testTakeover_ExcludeSubapp2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doExcludeSubapp2");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "a");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "c");
		tester.assertTextEqualsById("redirect2", "d");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "");

		// ## Act ##
		tester.clickLinkById("reload");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "a");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "");
	}

	public void testTakeover_IncludeSubapp1NoProperty() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doIncludeSubapp1NoProperty");
		tester.dumpHtml();

		tester.submitById("doConfirm");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "");
	}

	public void testTakeover_ExcludeSubapp2NoProperty() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/takeover/input.html");
		tester.dumpHtml();

		tester.setTextById("data", "a");
		tester.setTextById("page", "b");
		tester.setTextById("redirect1", "c");
		tester.setTextById("redirect2", "d");
		tester.setTextById("subapp1", "e");
		tester.setTextById("subapp2", "f");

		tester.submitById("doExcludeSubapp2NoProperty");
		tester.dumpHtml();

		tester.submitById("doConfirm");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data", "");
		tester.assertTextEqualsById("page", "");
		tester.assertTextEqualsById("redirect1", "");
		tester.assertTextEqualsById("redirect2", "");
		tester.assertTextEqualsById("subapp1", "e");
		tester.assertTextEqualsById("subapp2", "");
	}

}
