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
 * @author manhole
 */
public class ForeachTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(ForeachTest.class);
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

}
