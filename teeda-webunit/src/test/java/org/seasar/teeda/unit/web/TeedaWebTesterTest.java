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
package org.seasar.teeda.unit.web;

import java.net.URL;

import junit.framework.AssertionFailedError;
import net.sourceforge.jwebunit.html.Table;

import org.seasar.framework.util.ResourceUtil;

/**
 * @author manhole
 * @author yone
 * @author shot
 */
public class TeedaWebTesterTest extends TeedaWebTestCase {

	private TeedaWebTester tester;

	private String baseUrl;

	protected void setUp() throws Exception {
		super.setUp();
		tester = new TeedaWebTester();
		baseUrl = ResourceUtil.getBuildDir(getClass()).toURL().toString();
		tester.setBaseUrl(baseUrl);
	}

	public void testHello() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("hello.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTitleEquals("Hello page.");
		tester.assertTitleNotEquals("aaa");

		// これは完全一致で無くてもOK
		tester.assertTextInElementById("aaa", "eeda");

		// これは完全一致ならOK
		boolean ok = true;
		try {
			tester.assertTextEqualsById("aaa", "eeda");
			ok = false;
		} catch (AssertionFailedError e) {
			ok = true;
			System.out.println(e.getMessage());
		}
		assertEquals(true, ok);
		tester.assertTextEqualsById("aaa", "Teeda!");
	}

	public void testGetCurrentUri() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("hello.html");

		// ## Act ##
		tester.beginAt(relativeUrl);

		assertTrue(tester.getCurrentUri().startsWith("file:/"));
		assertTrue(tester.getCurrentUri().endsWith("hello.html"));
	}

	/*
	 * htmlの先頭へBOMを付けるとUTF-8と認識するようなコードがWebResponseImplに あるが、ここで試した感じでは効いていない。
	 * 
	 * EF BB BFが、3F 3F 3Fになってしまっているようだ。
	 * 
	 * Webサーバがレスポンスにcharsetを含めていれば問題ないため、 ここでは深追いしない。
	 */
	public void __testHelloJa() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("helloJa.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTitleEquals("Hello page.");

		// これは完全一致で無くてもOK
		tester.assertTextInElementById("aaa", "eeda");

		// これは完全一致ならOK
		boolean ok = true;
		try {
			tester.assertTextEqualsById("aaa", "eeda");
			ok = false;
		} catch (AssertionFailedError e) {
			ok = true;
			System.out.println(e.getMessage());
		}
		assertEquals(true, ok);
		tester.assertTextEqualsById("aaa", "Teeda!");
	}

	public void testAttribute() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("attribute.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		tester.assertAttributeEqualsById("aaa", "x", "xxx");
		tester.assertAttributeEqualsById("aaa", "y", "z");
		boolean ok = true;
		try {
			tester.assertAttributeEqualsById("aaa", "y", "123");
			ok = false;
		} catch (AssertionFailedError e) {
		}
		assertEquals(true, ok);
		try {
			tester.assertAttributeEqualsById("aaa", "ggg", "123");
			ok = false;
		} catch (AssertionFailedError e) {
		}
		assertEquals(true, ok);
		try {
			tester.assertAttributeEqualsById("unknown", "ggg", "123");
			ok = false;
		} catch (AssertionFailedError e) {
		}
		assertEquals(true, ok);
	}

	public void testTable() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("table1.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		// jwebunitのAPIでは、summary属性からもtableを取得できる
		final Table tableById = tester.getTableById("aaa");
		final Table tableBySummary = tester.getTableById("bbb");
		tableById.assertEquals(tableBySummary);

		// assertEquals(null, tester.getTableById("bbb"));
		// tester.getTable("aaa").assertEquals(tester.getTableById("aaa"));
	}

	public void testSetTextById() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("inputText.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		tester.assertAttributeEqualsById("aaaId", "value", "aaaValue");
		tester.setTextById("aaaId", "foo");
		tester.assertAttributeEqualsById("aaaId", "value", "foo");
	}

	public void testLink() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("link.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();
		tester.clickLinkById("goNext");

		// ## Assert ##
		tester.dumpHtml();
		tester.assertTextEqualsById("result", "linkResult");
	}

	public void testSetTextByIdForTextarea() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("inputTextarea.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("aaaId", "aaaValue");
		tester.setTextById("aaaId", "foo");
		tester.assertTextEqualsById("aaaId", "foo");
	}

	public void testForm() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("form.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		tester.assertFormElementPresentById("aForm");
	}

	public void testAssertAttributeExistById() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("inputText.html");

		// ## Act ##
		tester.beginAt(relativeUrl);

		// ## Assert ##
		tester.assertAttributeExistsById("aaaId", "name");
		tester.assertAttributeExistsById("aaaId", "type");
		tester.assertAttributeNotExistsById("aaaId", "badname");
	}

	public void testAssertSelectManyCheckboxByFormIndex() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("SelectManyCheckbox.html");

		// ## Act ##
		tester.beginAt(relativeUrl);

		// ## Assert ##
		tester.assertCheckboxNotSelectedByName(1, "aaa", "1");
		tester.assertCheckboxSelectedByName(1, "aaa", "2");
		tester.assertCheckboxNotSelectedByName(1, "aaa", "3");
	}

	public void testAssertSelectManyCheckboxByFormName() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("SelectManyCheckbox.html");

		// ## Act ##
		tester.beginAt(relativeUrl);

		// ## Assert ##
		tester.assertCheckboxNotSelectedByName("f", "aaa", "1");
		tester.assertCheckboxSelectedByName("f", "aaa", "2");
		tester.assertCheckboxNotSelectedByName("f", "aaa", "3");
	}

	public void testAssertselectOptionsByValues() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("SelectManyListbox.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.setWorkingForm(1);
		tester.unselectOptions("aaa", new String[] { "Two" });
		tester.selectOptionValueByName("aaa", "1");
		tester.selectOptionsByValues("bbb", new String[] { "10", "20", "30" });

		// ## Assert ##
		tester.assertSelectedOptionValueEqualsByName("aaa", "1");
		tester.assertSelectedOptionValuesEqualsByName("bbb", new String[] {
				"10", "20", "30" });
	}

	public void testUnselectOptions() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("SelectManyListbox.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.setWorkingForm(1);
		tester.selectOptionValueByName("aaa", "1");
		tester.selectOptionsByValues("bbb", new String[] { "10", "20", "30" });

		tester.unselectOptions("aaa", new String[] { "One", "Two" });
		tester.unselectOptions("bbb", new String[] { "One", "Two", "Three" });

		// ## Assert ##
		tester.assertSelectedOptionValuesEqualsByName("aaa", new String[] {});

		tester.assertSelectedOptionValuesEqualsByName("bbb", new String[] {});
	}

	public void testJs() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("js.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		Object scripts = tester.executeJavaScript("hogehoge", "call hogehoge");
		assertNotNull(scripts);
		System.out.println(scripts);
	}

	private String getFileAsRelativeUrl(final String file) {
		final URL url = getFileAsUrl(file);
		final String fullUrl = url.toString();
		String relativeUrl = null;
		if (fullUrl.startsWith(baseUrl)) {
			relativeUrl = fullUrl.substring(baseUrl.length());
		}
		System.out.println(relativeUrl);
		return relativeUrl;
	}

	public void testGetPageSource() throws Exception {
		// ## Arrange ##
		final String relativeUrl = getFileAsRelativeUrl("hello.html");

		// ## Act ##
		tester.beginAt(relativeUrl);
		tester.dumpHtml();

		// ## Assert ##
		assertNotNull(tester.getPageSource());
	}

}
