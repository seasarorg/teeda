/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

	public void testSubapplicationScope_do() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester
				.beginAt(getBaseUrl(),
						"view/scope/subapplicationScopeInput.html");
		tester.dumpHtml();

		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "mogemogemoge");

		tester.submitById("doAction");
		tester.dumpHtml();

		assertTrue(tester.getCurrentUri().indexOf(
				"view/scope/subapplicationScopeResult.html") > 0);
		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "");

		tester.executeJavaScript("window.location.reload()", "reload");
		tester.dumpHtml();

		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "");
	}

	public void testSubapplicationScope_link() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester
				.beginAt(getBaseUrl(),
						"view/scope/subapplicationScopeInput.html");
		tester.dumpHtml();

		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "mogemogemoge");

		tester.clickLinkById("goResult-1");
		tester.dumpHtml();

		assertTrue(tester.getCurrentUri().indexOf(
				"view/scope/subapplicationScopeResult.html") > 0);
		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "");

		tester.executeJavaScript("window.location.reload()", "reload");
		tester.dumpHtml();

		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "");
	}

	public void testRedirectScope_linkNever() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester
				.beginAt(getBaseUrl(),
						"view/scope/subapplicationScopeInput.html");
		tester.dumpHtml();

		tester.assertTextEqualsById("message1", "hogehogehoge");
		tester.assertTextEqualsById("message2", "mogemogemoge");

		tester.clickLinkById("goResult-2");
		tester.dumpHtml();

		assertTrue(tester.getCurrentUri().indexOf(
				"view/scope/subapplicationScopeResult.html") > 0);
		tester.assertTextEqualsById("message1", "");
		tester.assertTextEqualsById("message2", "");

		tester.executeJavaScript("window.location.reload()", "reload");
		tester.dumpHtml();

		tester.assertTextEqualsById("message1", "");
		tester.assertTextEqualsById("message2", "");
	}

	public void testDoFinish() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/scope/doFinishInput.html");
		tester.dumpHtml();

		tester.setTextByName("form:message", "hogehoge");

		tester.submitById("doFinishExecute");

		// ## Assert ##
		tester.dumpHtml();
		assertTrue(tester.getCurrentUri().indexOf(
				"view/scope/doFinishResult.html") > 0);
		tester.assertTextEqualsById("message", "");
	}

	public void testNoProperty_TEEDA431() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/scope/hasProperty.html");
		tester.dumpHtml();

		tester.setTextByName("Form:data1", "foo");
		tester.setTextByName("Form:data2", "bar");
		tester.submitById("doOtherPage");
		tester.dumpHtml();

		tester.submitById("doBack");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("data1", "");
		tester.assertTextEqualsById("data2", "bar");
	}

	public void testPageScope_TEEDA448() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/scope/pageScope.html");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("message1", "init1");
		tester.assertAttributeEqualsById("message2", "value", "init2");
		tester.assertAttributeEqualsById("message3", "value", "init3");

		// ## Act ##
		tester.setTextByName("form:message2", "a");
		tester.setTextByName("form:message3", "b");
		tester.submitById("doPageScopeExecute");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("message1", "init1");
		tester.assertAttributeEqualsById("message2", "value", "a");
		tester.assertAttributeEqualsById("message3", "value", "b");

		// ## Act ##
		tester.submitById("doPageScopeClear");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("message1", "");
		tester.assertAttributeEqualsById("message2", "value", "");
		tester.assertAttributeEqualsById("message3", "value", "");
	}

	public void testSubapplicationScope_TEEDA450() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester
				.beginAt(getBaseUrl(),
						"view/scope/subapplicationScopeInput.html");
		tester.dumpHtml();

		// ## Act ##
		// to subapplicationScopeResult.html
		tester.submitById("doAction");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("message3", "init");

		// ## Act ##
		// postback ~ self
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("message3", "init");
	}

}
