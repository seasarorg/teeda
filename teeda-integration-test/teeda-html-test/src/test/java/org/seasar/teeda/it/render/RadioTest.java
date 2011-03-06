/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import net.sourceforge.jwebunit.html.Table;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

/**
 * @author shot
 */
public class RadioTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(RadioTest.class);
	}

	public void testSelectValueAndSubmit() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/radio/selectOneRadio.html");
		tester.dumpHtml();

		tester.assertRadioOptionSelectedByName("form:aaa", "2");
		tester.clickRadioOptionByName("form:aaa", "1");
		tester.submitByName("form:doAction");

		// ## Assert ##
		tester.assertTextEqualsById("aaa-display", "1");
	}

	public void testSelectValueAndSubmitWithLayout2() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester
				.beginAt(getBaseUrl(),
						"view/radio/selectOneRadioWithLayout.html");
		tester.dumpHtml();

		tester.assertRadioOptionSelectedByName("form:aaa", "2");
		tester.clickRadioOptionByName("form:aaa", "1");
		tester.submitByName("form:doAction");

		Table table = tester.getTableById("aaa");
		int rowCount = table.getRowCount();
		System.out.println(table);
		System.out.println(rowCount);
		assertTrue(rowCount == 2);

		// ## Assert ##
		tester.assertTextEqualsById("aaa-display", "1");
	}

}
