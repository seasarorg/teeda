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

}
