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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.Test;
import net.sourceforge.jwebunit.junit.WebTester;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

import com.gargoylesoftware.htmlunit.html.HtmlInput;

/**
 * @author shot
 */
public class DoublesubmitTest extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(DoublesubmitTest.class);
	}

	public void testTeeda166() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();

		// ## Act ##
		tester.beginAt(getBaseUrl(), "view/doublesubmit/doublesubmit.html");
		tester.dumpHtml();

		// ## Assert ##
		assertTrue(tester.getPageSource().indexOf("DisabledConf") > 0);
	}

	public void testTeeda393_withRedirect() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();
		Method getElementById = TeedaWebTester.class.getDeclaredMethod(
				"getElementById", new Class[] { String.class });
		getElementById.setAccessible(true);

		// ## Act ##
		// ## Assert ##
		// display input.html
		tester.beginAt(getBaseUrl(), "view/doublesubmit/input.html");
		tester.assertTitleEquals("Input");
		tester.dumpHtml();
		HtmlInput confirmButton = (HtmlInput) getElementById.invoke(tester,
				new Object[] { "doOnceConfirm" });

		// submit 'confirm' button
		tester.setTextById("text", "aaa");
		tester.submitById("doOnceConfirm");
		tester.dumpHtml();
		tester.assertTitleEquals("Confirm");
		tester.assertTextEqualsById("text", "aaa");

		// back to input.html then submit 'confirm' button again
		confirmButton.click();
		tester.dumpHtml();
		tester.assertTitleEquals("Confirm");
		tester.assertTextEqualsById("text", "aaa");
		HtmlInput completeButton = (HtmlInput) getElementById.invoke(tester,
				new Object[] { "doOnceComplete" });

		// submit 'complete' button
		tester.submitById("doOnceComplete");
		tester.dumpHtml();
		tester.assertTitleEquals("Complete");
		tester.assertTextEqualsById("text", "aaa");

		// back to confirm.html then submit 'complete' button again
		completeButton.click();
		tester.dumpHtml();
		tester.assertTitleEquals("Complete");
		tester.assertTextEqualsById("text", "aaa");

		// back and back to input.html then submit 'confirm' button again
		confirmButton.click();
		tester.dumpHtml();
		tester.assertTitleEquals("error result");
	}

	public void testTeeda393_withForward() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();
		Method getElementById = TeedaWebTester.class.getDeclaredMethod(
				"getElementById", new Class[] { String.class });
		getElementById.setAccessible(true);

		// ## Act ##
		// ## Assert ##
		// display forward.html
		tester.beginAt(getBaseUrl(), "view/doublesubmit/forward.html");
		tester.assertTitleEquals("Forward");
		tester.dumpHtml();
		HtmlInput completeButton = (HtmlInput) getElementById.invoke(tester,
				new Object[] { "doOnceComplete" });

		// submit 'complete' button
		tester.setTextById("text", "aaa");
		tester.submitById("doOnceComplete");
		tester.dumpHtml();
		tester.assertTitleEquals("Forward");
		tester.assertTextEqualsById("text", "aaa");

		// back to previous page then submit 'complete' button again
		completeButton.click();
		tester.dumpHtml();
		tester.assertTitleEquals("error result");
	}

	public void testTeeda398() throws Exception {
		// ## Arrange ##
		TeedaWebTester tester = new TeedaWebTester();
		Field testerField = TeedaWebTester.class.getDeclaredField("tester");
		testerField.setAccessible(true);

		// ## Act ##
		// ## Assert ##
		tester.beginAt(getBaseUrl(), "view/doublesubmit/multiForm.html");
		tester.dumpHtml();
		WebTester webTester = (WebTester) testerField.get(tester);
		String token1 = webTester.getElementAttributByXPath(
				"//form[@id='firstForm']/input[@type='hidden']", "value");
		String token2 = webTester.getElementAttributByXPath(
				"//form[@id='secondForm']/input[@type='hidden']", "value");
		assertEquals(token1, token2);
	}

}
