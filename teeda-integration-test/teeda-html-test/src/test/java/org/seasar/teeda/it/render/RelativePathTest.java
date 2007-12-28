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
