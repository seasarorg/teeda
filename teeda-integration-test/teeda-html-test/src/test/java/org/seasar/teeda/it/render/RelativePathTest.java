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
 * @author koichik
 */
public class RelativePathTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(RelativePathTest.class);
	}

	public void test() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/relativepath/layoutTest.html");
		tester.clickLinkById("link1");
		tester.dumpHtml();

		// ## Assert ##
		// 自分と同じフォルダ
		tester.assertAttributeEqualsById("blue", "href",
				"/view/relativepath/blue.css");
		tester.assertAttributeEqualsById("img1", "src",
				"/view/relativepath/seasar_banner.gif");

		// 自分と同じフォルダを上位階層から指定
		tester.assertAttributeEqualsById("red", "href",
				"/view/relativepath/red.css");
		tester.assertAttributeEqualsById("img2", "src",
				"/view/relativepath/seasar_banner.gif");

		// 子供のフォルダ
		tester.assertAttributeEqualsById("yellow", "href",
				"/view/relativepath/child/yellow.css");
		tester.assertAttributeEqualsById("img3", "src",
				"/view/relativepath/child/seasar_banner.gif");

		tester.assertAttributeEqualsById("global", "href", "/css/global.css");
	}

	public void testChild() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/relativepath/layoutTest.html");
		tester.clickLinkById("link2");
		tester.dumpHtml();

		// ## Assert ##
		// 絶対パス
		tester.assertAttributeEqualsById("red", "href",
				"/view/relativepath/red.css");
		tester.assertAttributeEqualsById("img1", "src",
				"/view/relativepath/seasar_banner.gif");

		// 自分のフォルダ
		tester.assertAttributeEqualsById("yellow", "href",
				"/view/relativepath/child/yellow.css");
		tester.assertAttributeEqualsById("img2", "src",
				"/view/relativepath/child/seasar_banner.gif");

		// 親のフォルダ
		tester.assertAttributeEqualsById("blue", "href",
				"/view/relativepath/blue.css");
		tester.assertAttributeEqualsById("img3", "src",
				"/view/relativepath/seasar_banner.gif");

		tester.assertAttributeEqualsById("global", "href", "/css/global.css");
	}

}
