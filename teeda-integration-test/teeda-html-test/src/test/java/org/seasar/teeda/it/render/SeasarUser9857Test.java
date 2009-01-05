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

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

/**
 * @author shot
 */
public class SeasarUser9857Test extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(SeasarUser9857Test.class);
	}

	public void testSeasarUser9857_itemsShouldBePassedToNextPage()
			throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/move/movetest.html");
		tester.dumpHtml();

		tester.setTextByName("form:statement", "aaa");

		tester.submitById("doMove");

		// ## Assert ##
		tester.dumpHtml();
		assertTrue(tester.getCurrentUri().indexOf(
				"view/move/resultMovetest.html") > 0);
		// ForEachが描画されていないと下記テキストは出力されない.
		tester.assertTextPresent("hogehoge");
		tester.assertTextPresent("foofoo");
	}

}
