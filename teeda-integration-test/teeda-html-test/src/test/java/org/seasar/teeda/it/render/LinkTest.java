/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

/**
 * @author shot
 */
public class LinkTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(LinkTest.class);
	}

	public void testLinkSimple() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/link/link.html");
		tester.dumpHtml();
		tester.clickLinkById("goLinkResult");

		// ## Assert ##
		assertTrue(tester.getCurrentUri().indexOf("view/link/linkResult.html") >= 0);
		String str = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		tester.assertTextEqualsById("date", str);
		tester.assertTextEqualsById("arg1", "1111");
		tester.assertTextEqualsById("arg2", "2222");
		tester.assertTextEqualsById("arg3", "3333");
	}

	public void testLinkAndBackByJump() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/link/link.html");
		tester.dumpHtml();
		tester.clickLinkById("goLinkResult");

		// ## Assert ##
		assertTrue(tester.getCurrentUri().indexOf("view/link/linkResult.html") >= 0);

		tester.dumpHtml();
		tester.submitById("jumpLink");

		assertTrue(tester.getCurrentUri().indexOf("view/link/link.html") >= 0);
	}

}
