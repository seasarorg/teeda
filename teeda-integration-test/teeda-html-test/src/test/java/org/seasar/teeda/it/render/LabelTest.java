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
 */
public class LabelTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(LabelTest.class);
	}

	public void testRender() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/label/label.html");
		tester.dumpHtml();

		// ## Assert ##
		tester.assertTextEqualsById("aaa", "AAA");
		tester.assertTextEqualsById("bbb", "BBB");
		tester.assertTextEqualsById("ccc", "CCC");

		// labelのボディ部分はレンダされないこと
		tester.assertTextNotPresent("cccBody");
		// 対応するpropertyやPage属性がない場合はbody値がそのまま出力されること
		tester.assertTextPresent("dddBody");

		// TEEDA-440
		tester.assertAttributeEqualsById("table", "title", "TABLE");
		tester.assertAttributeEqualsById("goLabel-2", "title", "LINK");
		tester.assertAttributeEqualsById("goLabel-3", "title", "LINK(dynamic)");
		tester.assertAttributeEqualsById("img", "alt", "IMG");
		tester.assertAttributeEqualsById("goLabel-4", "value", "SUBMIT(id)");
		tester.assertAttributeEqualsById("goLabelspan", "value",
				"SUBMIT(label)");

		// TEEDA-441
		tester.assertTextEqualsById("aaaLabel", "AAA");
		tester.assertTextEqualsById("aaaLabel-2", "AAA");
		tester.assertTextEqualsById("bbbLabel", "BBB");
	}

}
