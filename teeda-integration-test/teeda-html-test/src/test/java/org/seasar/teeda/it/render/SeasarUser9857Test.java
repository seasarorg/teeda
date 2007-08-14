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
