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

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

public class SelectManyCheckboxTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(SelectManyCheckboxTest.class);
	}

	public void testLayout() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(),
				"view/checkbox/selectManyCheckboxWithLayout.html");
		tester.dumpHtml();
		String[][] table = new String[][] { { "unchecked 0", "checked 1", },
				{ "unchecked 2", "checked 3" }, { "unchecked 4", "checked 5" },
				{ "unchecked 6", "checked 7" }, { "unchecked 8", "checked 9" },
				{ "unchecked 10", "checked 11" },
				{ "unchecked 12", "checked 13" },
				{ "unchecked 14", "checked 15" },
				{ "unchecked 16", "checked 17" },
				{ "unchecked 18", "checked 19" } };
		tester.assertTableEqualsById("aaa", table);

		// --------
		tester.setTextByName("form:aaaCol", "3");
		tester.submitById("doAction");
		tester.dumpHtml();
		table = new String[][] { { "unchecked 0", "checked 1", "unchecked 2" },
				{ "checked 3", "unchecked 4", "checked 5" },
				{ "unchecked 6", "checked 7", "unchecked 8" },
				{ "checked 9", "unchecked 10", "checked 11" },
				{ "unchecked 12", "checked 13", "unchecked 14" },
				{ "checked 15", "unchecked 16", "checked 17" },
				{ "unchecked 18", "checked 19" } };
		tester.assertTableEqualsById("aaa", table);

		// --------
		tester.setTextByName("form:aaaCol", "4");
		tester.submitById("doAction");
		tester.dumpHtml();
		table = new String[][] {
				{ "unchecked 0", "checked 1", "unchecked 2", "checked 3" },
				{ "unchecked 4", "checked 5", "unchecked 6", "checked 7" },
				{ "unchecked 8", "checked 9", "unchecked 10", "checked 11" },
				{ "unchecked 12", "checked 13", "unchecked 14", "checked 15" },
				{ "unchecked 16", "checked 17", "unchecked 18", "checked 19" } };
		tester.assertTableEqualsById("aaa", table);
	}

	public void testMapItems_TEEDA446() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(),
				"view/checkbox/selectManyCheckboxMapItems.html");
		tester.dumpHtml();

		// ## Assert ##
		String[][] table = new String[][] { { "unchecked AAAA" },
				{ "unchecked BBBB" }, { "checked CCCC" } };
		tester.assertTableEqualsById("aaa", table);

		// ## Act ##
		tester.checkCheckboxByName("selectManyCheckboxForm:aaa", "0");
		tester.submitById("doAction");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "checked AAAA" }, { "unchecked BBBB" },
				{ "checked CCCC" } };
		tester.assertTableEqualsById("aaa", table);

		// ## Act ##
		tester.checkCheckboxByName("selectManyCheckboxForm:aaa", "1");
		tester.submitById("doAction");
		tester.dumpHtml();

		// ## Assert ##
		table = new String[][] { { "checked AAAA" }, { "checked BBBB", },
				{ "checked CCCC" } };
		tester.assertTableEqualsById("aaa", table);
	}

}
