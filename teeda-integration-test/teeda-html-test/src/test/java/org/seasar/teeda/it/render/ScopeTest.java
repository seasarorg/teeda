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
 * @author shot
 */
public class ScopeTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(ScopeTest.class);
	}

	public void testPageScope() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/scope/pageScopeInput.html");
		tester.dumpHtml();

		tester.setTextByName("form:message", "hogehoge");

		tester.submitById("doPageScopeExecute");

		// ## Assert ##
		tester.dumpHtml();
		assertTrue(tester.getCurrentUri().indexOf(
				"view/scope/pageScopeResult.html") > 0);
		tester.assertTextEqualsById("message", "");
	}

	public void testRedirectScope() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/scope/redirectScopeInput.html");
		tester.dumpHtml();

		tester.setTextByName("form:message", "hogehoge");
		tester.setTextByName("form:hoge", "foofoo");

		tester.submitById("doRedirectScopeExecute");

		// ## Assert ##
		tester.dumpHtml();
		assertTrue(tester.getCurrentUri().indexOf(
				"view/scope/redirectScopeResult1.html") > 0);
		tester.assertTextEqualsById("message", "hogehoge");
		tester.assertTextEqualsById("hoge", "foofoo");

		tester.executeJavaScript("window.location.reload()", "reload");

		tester.dumpHtml();
		tester.assertTextEqualsById("message", "");
		tester.assertTextEqualsById("hoge", "foofoo");
	}

}
