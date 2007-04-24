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
public class ForeachTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(ForeachTest.class);
	}

	public void testSeasarUser7342() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreach.html");
		tester.dumpHtml();
		tester.setTextById("foo-text", "aaa");
		tester.setTextById("bar-text", "bbb");
		tester.submitById("doForeachConfirm");

		// --------
		assertTrue(tester.getCurrentUri().indexOf("foreachConfirm.html") > 0);

		tester.dumpHtml();

		tester.assertTextEqualsById("foo-text", "aaa");
		tester.assertTextEqualsById("bar-text", "bbb");
	}

	public void testSeasarUser7347() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(),
				"view/foreach/foreach.html?foo=AAA&bar=BBB");
		tester.dumpHtml();
		tester.assertTextEqualsById("foo-span", "AAA");
		tester.assertTextEqualsById("bar-span", "BBB");
		tester.assertTextEqualsById("foo-span2", "AAA");
		tester.assertTextEqualsById("bar-span2", "BBB");
	}

	public void testInputArray() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachArray.html");
		tester.dumpHtml();
		final String[][] table = new String[][] { { "1", "aa1", "bb1" },
				{ "2", "aa2", "bb2" }, { "3", "aa3", "bb3" } };

		tester.assertTableEqualsById("foreachTable", table);
		tester.submitById("doSomething");

		// --------

		tester.dumpHtml();
		// inputになっていない"fooNo"項目が再度表示されていること
		tester.assertTableEqualsById("foreachTable", table);
	}

	public void testInputList() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachList.html");
		tester.dumpHtml();
		final String[][] table = new String[][] { { "1", "aa1", "bb1" },
				{ "2", "aa2", "bb2" }, { "3", "aa3", "bb3" } };

		tester.assertTableEqualsById("foreachTable", table);
		tester.submitById("doSomething");

		// --------

		tester.dumpHtml();
		// inputになっていない"fooNo"項目が再度表示されていること
		tester.assertTableEqualsById("foreachTable", table);
	}

	/*
	 * https://www.seasar.org/issues/browse/TEEDA-139
	 * 
	 * 同じitemsを複数回ForEachした際に、復元のために使っているPageのプロパティに
	 * 前にForEachした際の最後の要素の値が残ってしまっているため、 最後の要素の値がレンダされてしまっていた。
	 */
	public void testMulti() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachMulti.html");
		tester.dumpHtml();

		tester.assertTextEqualsByName("form:fooItems-aaa:0:aaa", "aa1");
		tester.assertTextEqualsByName("form:fooItems-aaa:1:aaa", "aa2");
		tester.assertTextEqualsByName("form:fooItems-aaa:2:aaa", "aa3");
		tester.assertTextEqualsByName("form:fooItems-bbb:0:bbb", "bb1");
		tester.assertTextEqualsByName("form:fooItems-bbb:1:bbb", "bb2");
		tester.assertTextEqualsByName("form:fooItems-bbb:2:bbb", "bb3");

		tester.submitById("doSomething");

		tester.dumpHtml();
		/*
		 * "aa1", "aa2", "aa3"が、すべて"aa3"になってしまっていた。
		 */
		tester.assertTextEqualsByName("form:fooItems-aaa:0:aaa", "aa1");
		tester.assertTextEqualsByName("form:fooItems-aaa:1:aaa", "aa2");
		tester.assertTextEqualsByName("form:fooItems-aaa:2:aaa", "aa3");
		tester.assertTextEqualsByName("form:fooItems-bbb:0:bbb", "bb1");
		tester.assertTextEqualsByName("form:fooItems-bbb:1:bbb", "bb2");
		tester.assertTextEqualsByName("form:fooItems-bbb:2:bbb", "bb3");
	}

	/*
	 * https://www.seasar.org/issues/browse/TEEDA-149
	 * 
	 * ForEach内のレコードへ空白を入力できること。
	 */
	public void testSubmitSpace() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachArray.html");
		tester.dumpHtml();
		tester.assertTableEqualsById("foreachTable", new String[][] {
				{ "1", "aa1", "bb1" }, { "2", "aa2", "bb2" },
				{ "3", "aa3", "bb3" } });
		tester.setTextByName("form:fooItems:0:aaa", "");
		tester.setTextByName("form:fooItems:1:aaa", "a");
		tester.setTextByName("form:fooItems:2:bbb", "");
		tester.submitById("doSomething");

		// --------

		tester.dumpHtml();
		// 空白を含め、入力した値が反映されていること
		tester.assertTableEqualsById("foreachTable", new String[][] {
				{ "1", "", "bb1" }, { "2", "a", "bb2" }, { "3", "aa3", "" } });
	}

	/*
	 * https://www.seasar.org/issues/browse/TEEDA-149
	 * 
	 * ForEach内のレコードへ空白を入力できること。
	 */
	public void testSubmitSpace2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachArray2.html");
		tester.dumpHtml();
		tester.assertTableEqualsById("foreachTable", new String[][] {
				{ "1", "aa1", "bb1" }, { "2", "aa2", "bb2" },
				{ "3", "aa3", "bb3" } });
		tester.setTextByName("form:fooItems:0:fooNo", "-321");
		tester.setTextByName("form:fooItems:2:fooNo", "");
		tester.setTextByName("form:fooItems:1:aaa", "a");
		tester.setTextByName("form:fooItems:2:bbb", "");
		tester.submitById("doSomething");

		// --------

		tester.dumpHtml();
		// 空白を含め、入力した値が反映されていること
		// Integer型へ空白更新できていること
		tester.assertTableEqualsById("foreachTable", new String[][] {
				{ "-321", "aa1", "bb1" }, { "2", "a", "bb2" },
				{ "", "aa3", "" } });
	}

	/*
	 * https://www.seasar.org/issues/browse/TEEDA-150
	 * 
	 * ForEachのサイズが0に変更されたら、0サイズでレンダされること。
	 */
	public void testSizeChange() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachSize.html");
		tester.dumpHtml();
		tester.assertTableEqualsById("foreachTable",
				new String[][] { { "0", "aa0", "0" }, { "1", "aa1", "10" },
						{ "2", "aa2", "20" } });
		tester.assertTextEqualsByName("aaaForm:itemSize", "3");
		tester.setTextByName("aaaForm:itemSize", "0");
		tester.submitById("doChangeSize");

		// --------

		tester.dumpHtml();
		tester.assertTextEqualsByName("aaaForm:itemSize", "0");
		tester.assertTableEqualsById("foreachTable", new String[][] {});
	}

	public void testValidationError() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachHoge.html");
		tester.dumpHtml();
		final String[][] table = new String[][] { { "1", "aa1", "bb1" },
				{ "2", "aa2", "bb2" }, { "3", "aa3", "bb3" } };

		// assume form is found.
		tester.assertFormElementPresentById("aForm");

		tester.setTextByName("aForm:num1", "123");
		tester.setTextByName("aForm:num2", "223");

		tester.assertTableEqualsById("foreachTable", table);

		tester.setTextByName("aForm:fooItems:0:aaa", "");
		tester.setTextByName("aForm:fooItems:1:aaa", "a");
		tester.setTextByName("aForm:fooItems:2:bbb", "");

		tester.submitById("goForeachHoge2");

		tester.dumpHtml();
		final String[][] tableExpected = new String[][] { { "1", "", "bb1" },
				{ "2", "a", "bb2" }, { "3", "aa3", "" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Assert ##
		// assume aggregation error message output.
		tester.assertTextPresent("hogefoobar");
	}

}
