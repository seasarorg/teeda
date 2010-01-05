/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
public class ReloadTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(ReloadTest.class);
	}

	public void testRender() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/reload/regist.html");
		tester.dumpHtml();
		tester.setTextById("value1", "12345");
		tester.submitById("doRegist");

		// --------
		String currentUri = tester.getCurrentUri();
		assertTrue(currentUri.indexOf("view/reload/registConfirm.html") >= 0);

		tester.executeJavaScript("location.reload()", "reload test");
		tester.assertTextEqualsById("value1", "12345");
	}

}
