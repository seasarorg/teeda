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
import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.html.Table;

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

	public void testSeasarUser7342_2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachSeasarUser7342.html");
		tester.dumpHtml();
		tester.setTextById("foo-text", "hogehoge");
		tester.setTextById("bar-text", "foofoo");
		tester.submitById("doForeachConfirm");

		// --------
		assertTrue(tester.getCurrentUri().indexOf(
				"foreachConfirmSeasarUser7342.html") > 0);

		tester.dumpHtml();

		tester.assertTextEqualsById("foo-text", "hogehoge");
		tester.assertTextEqualsById("bar-text", "foofoo");

		tester.submitById("doForeach");

		assertTrue(tester.getCurrentUri().indexOf("foreachSeasarUser7342.html") > 0);

		tester.assertTextEqualsById("foo-span2", "hogehoge");
		tester.assertTextEqualsById("bar-span2", "foofoo");

	}

	/**
	 * @see https://www.seasar.org/issues/browse/TEEDA-345
	 */
	public void testForeachSimple_TEEDA345() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/simpleForeach.html");
		tester.dumpHtml();
	}

	public void testForeachDto_TEEDA324() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachDto.html");
		tester.dumpHtml();
		tester.setTextByName("form:aaaItems:0:foo", "x1");
		tester.setTextByName("form:aaaItems:1:foo", "x2");
		tester.setTextByName("form:aaaItems:2:bar", "y3");
		tester.submitById("doSubmit");

		// --------
		assertTrue(tester.getCurrentUri().indexOf("foreachDto.html") > 0);

		tester.dumpHtml();
		final String[][] tableExpected = new String[][] {
				{ "x1", "b1", "x1b1" }, { "x2", "b2", "x2b2" },
				{ "a3", "y3", "a3y3" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachDto_noItemsSave_TEEDA395() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/noItemsSave.html");
		tester.dumpHtml();
		tester.setTextByName("form:fooItems:0:bbb", "b1x");
		tester.setTextByName("form:fooItems:0:ccc", "c1x");
		tester.setTextByName("form:fooItems:1:bbb", "b2x");
		tester.setTextByName("form:fooItems:1:ccc", "c2x");
		tester.setTextByName("form:fooItems:2:bbb", "b3x");
		tester.setTextByName("form:fooItems:2:ccc", "c3x");
		tester.submitById("doTest");

		// ## Assert ##
		tester.dumpHtml();
		final String[][] tableExpected = new String[][] {
				{ "a1", "b1x", "c1x" }, { "a2", "b2x", "c2x" },
				{ "a3", "b3x", "c3x" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachDto_noItemsSaveList() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/noItemsSaveList.html");
		tester.dumpHtml();
		String[][] tableExpected = new String[][] { { "a1", "b1", "c1" },
				{ "a2", "b2", "c2" }, { "a3", "b3", "c3" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
		tester.submitById("doTest");

		// ## Assert ##
		tester.dumpHtml();
		tableExpected = new String[][] { { "a2", "b2", "c2" },
				{ "a3", "b3", "c3" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachNest_TEEDA386() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachNest.html");
		tester.dumpHtml();
		String[][] tableExpected = new String[][] { { "00", "01" },
				{ "10", "11" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		tester.setTextByName("form:aaaItemsItems:0:aaaItems:0:foo", "a");
		tester.setTextByName("form:aaaItemsItems:0:aaaItems:1:foo", "b");
		tester.setTextByName("form:aaaItemsItems:1:aaaItems:0:foo", "c");
		tester.setTextByName("form:aaaItemsItems:1:aaaItems:1:foo", "d");
		tester.submitById("doSubmit");

		// --------
		tester.dumpHtml();
		tableExpected = new String[][] { { "a", "b" }, { "c", "d" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachListUpdate_TEEDA391() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachNestList.html");
		tester.dumpHtml();
		tester.setTextByName("form:aaaItemsItems:0:aaaItems:0:foo", "a");
		tester.setTextByName("form:aaaItemsItems:0:aaaItems:1:foo", "b");
		tester.setTextByName("form:aaaItemsItems:1:aaaItems:0:foo", "c");
		tester.setTextByName("form:aaaItemsItems:1:aaaItems:1:foo", "d");
		tester.submitById("doSubmit");

		// --------
		tester.dumpHtml();
		final String[][] tableExpected = new String[][] { { "a", "b" },
				{ "c", "d" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachInputRadio_TEEDA414() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachInputRadio.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] tableExpected = new String[][] {
				{ "unchecked", "One", "unchecked", "A" },
				{ "unchecked", "Two", "unchecked", "B" },
				{ "unchecked", "Three", "unchecked", "C" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		tester.clickRadioOptionByName("form:aaa", "1");
		tester.clickRadioOptionByName("form:bbb", "b");
		tester.submitByName("form:doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		tableExpected = new String[][] {
				{ "checked", "One", "unchecked", "A" },
				{ "unchecked", "Two", "checked", "B" },
				{ "unchecked", "Three", "unchecked", "C" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		tester.clickRadioOptionByName("form:aaa", "2");
		tester.clickRadioOptionByName("form:bbb", "c");
		tester.submitByName("form:doSubmit");
		tester.dumpHtml();

		// ## Assert ##
		tableExpected = new String[][] {
				{ "unchecked", "One", "unchecked", "A" },
				{ "checked", "Two", "unchecked", "B" },
				{ "unchecked", "Three", "checked", "C" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachDelete() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachDelete.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] tableExpected = new String[][] { { "111", "delete" },
				{ "222", "delete" }, { "333", "delete" }, { "444", "delete" },
				{ "555", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:2:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "111", "delete" },
				{ "222", "delete" }, { "444", "delete" }, { "555", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:3:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "111", "delete" },
				{ "222", "delete" }, { "444", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:0:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "222", "delete" },
				{ "444", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:1:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "222", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:0:doDelete");
		tester.dumpHtml();

		tableExpected = new String[0][];
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachDeleteList() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachDeleteList.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] tableExpected = new String[][] { { "111", "delete" },
				{ "222", "delete" }, { "333", "delete" }, { "444", "delete" },
				{ "555", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:2:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "111", "delete" },
				{ "222", "delete" }, { "444", "delete" }, { "555", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:3:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "111", "delete" },
				{ "222", "delete" }, { "444", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:0:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "222", "delete" },
				{ "444", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:1:doDelete");
		tester.dumpHtml();

		tableExpected = new String[][] { { "222", "delete" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("foreachDeleteForm:aaaItems:0:doDelete");
		tester.dumpHtml();

		tableExpected = new String[0][];
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeachNestButton_TEEDA417() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachNestButton.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] tableExpected = new String[][] {
				{ "0:0", "0:1", "0:2", "0:3" }, { "1:0", "1:1", "1:2", "1:3" },
				{ "2:0", "2:1", "2:2", "2:3" }, };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		// ## Assert ##
		tester.submitByName("form:aaaItemsItems:0:aaaItems:0:doAction");
		tester.dumpHtml();

		tester.assertTextEqualsById("selectedIndex", "0:0");

		// ## Act ##
		// ## Assert ##
		tester.submitByName("form:aaaItemsItems:1:aaaItems:0:doAction");
		tester.dumpHtml();

		tester.assertTextEqualsById("selectedIndex", "1:0");

		// ## Act ##
		// ## Assert ##
		tester.submitByName("form:aaaItemsItems:0:aaaItems:1:doAction");
		tester.dumpHtml();

		tester.assertTextEqualsById("selectedIndex", "0:1");

		// ## Act ##
		// ## Assert ##
		tester.submitByName("form:aaaItemsItems:1:aaaItems:1:doAction");
		tester.dumpHtml();

		tester.assertTextEqualsById("selectedIndex", "1:1");

		// ## Act ##
		// ## Assert ##
		tester.submitByName("form:aaaItemsItems:2:aaaItems:3:doAction");
		tester.dumpHtml();

		tester.assertTextEqualsById("selectedIndex", "2:3");
	}

	public void testForeachNestAppend_TEEDA430() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachNestAppend.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] tableExpected = new String[][] { { "1", "2", "3" }, { "1" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);

		// ## Act ##
		tester.submitById("doAppendInsert");
		tester.dumpHtml();

		// ## Assert ##
		tableExpected = new String[][] { { "1", "2", "10", "3" }, { "1" } };
		tester.assertTableEqualsById("foreachTable", tableExpected);
	}

	public void testForeach_TEEDA438() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreachRowspan.html");
		tester.dumpHtml();

		// ## Assert ##
		Table tableExpected = new Table(new Object[][] {
				{ new Cell("A", 1, 3), "A1" }, { "A2" }, { "A3" },
				{ "B", "B1" }, { new Cell("C", 1, 2), "C1" }, { "C2" } });
		tester.assertTableEqualsById("productItems", tableExpected);
		tester.assertAttributeEqualsById("productItems", "border", "2");
	}

	public void testGenericStyleClass_TEEDA458() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/foreach/foreach.html");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertAttributeEqualsById("row", "class", "odd");
	}

}
