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

import java.lang.reflect.Method;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

import com.gargoylesoftware.htmlunit.html.HtmlInput;

/**
 * @author shot
 */
public class ConditionTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(ConditionTest.class);
	}

	public void testConditionRestoreWhenValidationError() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition.html");
		tester.dumpHtml();

		tester.assertElementPresentById("isAaa");
		tester.assertElementNotPresentById("isNotAaa");
		tester.setTextById("hoge", "1");

		tester.submitById("doHoge-1");

		tester.dumpHtml();
		// ## Assert ##
		tester.assertElementPresentById("isAaa");
		tester.assertElementNotPresentById("isNotAaa");
	}

	public void testConditionRestoreWhenValidationErrorWithSpan()
			throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/conditionSpan.html");
		tester.dumpHtml();

		tester.assertElementPresentById("isAaa");
		tester.assertElementNotPresentById("isNotAaa");
		tester.setTextById("hoge", "1");

		tester.submitById("doHoge-1");

		tester.dumpHtml();
		// ## Assert ##
		tester.assertElementPresentById("isAaa");
		tester.assertElementNotPresentById("isNotAaa");
	}

	public void testConditionNotMatch_TEEDA307() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/conditionNotMatch.html");
		tester.dumpHtml();

		tester.assertElementPresentById("isAaa");
		tester.assertElementPresentById("isNotAaa");
		tester.setTextById("hoge", "1");

		tester.submitById("doHoge-1");

		tester.dumpHtml();
		// ## Assert ##
		tester.assertElementPresentById("isAaa");
		tester.assertElementPresentById("isNotAaa");
	}

	public void testConditionForEach_TEEDA241() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition3.html");
		tester.dumpHtml();

		tester.submitById("doCheck");

		tester.dumpHtml();
		// ## Assert ##
		String src = tester.getPageSource();
		String s1 = "メッセージあり";
		String s2 = "メッセージなし";
		int pos = src.indexOf(s1);
		assertTrue(pos >= 0);
		pos = src.indexOf(s2, pos);
		assertTrue(pos >= 0);
		pos = src.indexOf(s1, pos);
		assertTrue(pos >= 0);
	}

	public void testCondition2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition2.html");
		tester.dumpHtml();

		tester.submitById("doHoge");

		tester.dumpHtml();

		// ## Assert ##
		tester.assertElementPresentById("aaa-1");
		tester.assertTextPresent("true");

		tester.setTextById("hoge", "111");
		tester.submitById("doHoge");

		tester.dumpHtml();

		// ## Assert ##
		tester.assertElementPresentById("aaa-2");
		tester.assertTextPresent("false");

		tester.setTextById("hoge", "a");
		tester.submitById("doHoge");

		tester.assertElementPresentById("allMessages");
		tester.assertElementPresentById("aaa-2");
		tester.assertTextPresent("false");

	}

	public void testConditionInput() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/conditionInput.html");
		tester.dumpHtml();

		tester.setTextById("bbb", "hogehoge");

		tester.submitById("doExec");

		assertTrue(tester.getCurrentUri().indexOf(
				"view/condition/conditionResult.html") > 0);
		tester.assertTextEqualsById("bbb", "hogehoge");
	}

	public void testConditionOmittag_TEEDA409() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/conditionOmittag.html");
		tester.dumpHtml();

		tester.assertElementNotPresentById("isAaa");
		tester.assertElementPresentById("bbb-1");
		tester.assertElementNotPresentById("isNotAaa");
		tester.assertElementNotPresentById("bbb-2");

		tester.setTextById("hoge", "111");
		tester.submitById("doFalse");

		tester.dumpHtml();
		// ## Assert ##
		tester.assertElementNotPresentById("isAaa");
		tester.assertElementNotPresentById("bbb-1");
		tester.assertElementNotPresentById("isNotAaa");
		tester.assertElementPresentById("bbb-2");
	}

	public void testConditionForEach_TEEDA420() {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition4.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] table = new String[][] { { "0", "foo" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitByName("form:aaaItems:0:doFoo");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("selected", "0");
		table = new String[][] { { "0", "foo" }, { "1", "bar" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitByName("form:aaaItems:0:doFoo");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("selected", "0");
		table = new String[][] { { "0", "foo" }, { "1", "bar" }, { "2", "foo" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitByName("form:aaaItems:2:doFoo");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("selected", "2");
		table = new String[][] { { "0", "foo" }, { "1", "bar" },
				{ "2", "foo" }, { "3", "bar" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitByName("form:aaaItems:0:doFoo");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("selected", "0");
		table = new String[][] { { "0", "foo" }, { "1", "bar" },
				{ "2", "foo" }, { "3", "bar" }, { "4", "foo" } };
		tester.assertTableEqualsById("aaaItems", table);
	}

	public void testConditionForEachValidator_TEEDA420() {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition5.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] table = new String[0][0];
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "値を入力してください(bbb)(line : 1)" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.setTextByName("form:aaaItems:0:bbb", "a");
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "a" }, { "1", "" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "a" }, { "1", "" }, { "2", "" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "a" }, { "1", "" },
				{ "2", "値を入力してください(bbb)(line : 3)" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.setTextByName("form:aaaItems:2:bbb", "c");
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "a" }, { "1", "" }, { "2", "c" },
				{ "3", "" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "a" }, { "1", "" }, { "2", "c" },
				{ "3", "" }, { "4", "" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.setTextByName("form:aaaItems:0:bbb", "");
		tester.setTextByName("form:aaaItems:2:bbb", "");
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "値を入力してください(bbb)(line : 1)" },
				{ "1", "" }, { "2", "値を入力してください(bbb)(line : 3)" }, { "3", "" },
				{ "4", "値を入力してください(bbb)(line : 5)" } };
		tester.assertTableEqualsById("aaaItems", table);

		// ## Act ##
		tester.setTextByName("form:aaaItems:0:bbb", "x");
		tester.setTextByName("form:aaaItems:2:bbb", "y");
		tester.setTextByName("form:aaaItems:4:bbb", "z");
		tester.submitById("doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "0", "x" }, { "1", "" }, { "2", "y" },
				{ "3", "" }, { "4", "z" }, { "5", "" } };
		tester.assertTableEqualsById("aaaItems", table);
	}

	public void testCondition_TEEDA437() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();
		Method getElementById = TeedaWebTester.class.getDeclaredMethod(
				"getElementById", new Class[] { String.class });
		getElementById.setAccessible(true);

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition6.html");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("submitted", "");
		tester.assertCheckboxNotSelectedByName("form:disp");
		tester.assertElementNotPresentById("bar");

		// ## Act ##
		tester.checkCheckboxByName("form:disp");
		tester.submitById("doFoo");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("submitted", "foo");
		tester.assertCheckboxSelectedByName("form:disp");
		tester.assertElementPresentById("doBar");

		HtmlInput checkbox = (HtmlInput) getElementById.invoke(tester,
				new Object[] { "disp" });
		HtmlInput doBarButton = (HtmlInput) getElementById.invoke(tester,
				new Object[] { "doBar" });

		// ## Act ##
		tester.submitById("jumpHello_hello");
		tester.dumpHtml();

		// back to confirm6.html
		// ## Act ##
		checkbox.setChecked(false);
		doBarButton.click();
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("submitted", "bar");
		tester.assertCheckboxNotSelectedByName("form:disp");
		tester.assertElementNotPresentById("doBar");
	}

	public void testCondition_TEEDA443() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/condition/condition.html");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertElementPresentById("isAaa");
		tester.assertElementNotPresentById("isNotAaa");

		// ## Act ##
		tester.clickLinkById("goFalse");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertElementNotPresentById("isAaa");
		tester.assertElementPresentById("isNotAaa");

		// ## Act ##
		tester.clickLinkById("goTrue");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertElementPresentById("isAaa");
		tester.assertElementNotPresentById("isNotAaa");
	}

}
