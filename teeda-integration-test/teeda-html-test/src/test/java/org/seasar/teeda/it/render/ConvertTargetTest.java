/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
 * @author yone
 */
public class ConvertTargetTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(ConvertTargetTest.class);
	}

	public void testInputText() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/converter/inputText2.html");
		tester.dumpHtml();
		tester.assertTitleMatch("inputText2");

		// ## Assert ##
		tester.assertTextEqualsById("aaa", "");
		doAssert(tester);
	}

	private void doAssert(TeedaWebTester tester) {
		tester.setTextById("aaa", "2008/12/31");
		tester.submitById("doSomething");
		tester.dumpHtml();

		tester.assertTextEqualsById("aaa", "2008/12/31");
		

		tester.setTextById("aaa", "AAA");
		tester.submitById("doSomething");
		tester.dumpHtml();

		tester.assertMatchInElementById("aaaMessage", "aaa");
		
		tester.setTextById("aaa", "hoge");
		tester.submitById("doBbb");
		tester.dumpHtml();
		
		tester.assertElementNotPresentById("aaaMessages");
	}

}
