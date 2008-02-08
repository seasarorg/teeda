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
 * 
 * https://www.seasar.org/issues/browse/TEEDA-360
 */
public class Teeda360Test extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(Teeda360Test.class);
	}

	public void testRender() throws Exception {
		try {
			// ## Arrange ##
			TeedaWebTester tester = new TeedaWebTester();

			// ## Act ##
			tester.beginAt(getBaseUrl(), "view/condition/aaa.html");
			tester.dumpHtml();

			// ## Assert ##
			tester.assertTextNotPresent("isNotDayReport");

			tester.setTextById("inYearMonthDayFrom", "aaa");
			tester.setTextById("inYearMonthDayTo", "aaa");

			tester.submitById("doExecute");

			tester.assertTextEqualsById("inYearMonthDayFrom", "aaa");
			tester.assertTextEqualsById("inYearMonthDayTo", "aaa");
			tester.assertTextNotPresent("isNotDayReport");
		} catch (Throwable e) {
			fail();
		}
	}

}
