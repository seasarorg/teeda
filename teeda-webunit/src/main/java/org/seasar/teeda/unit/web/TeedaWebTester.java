/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.unit.web;

import junit.framework.Assert;
import net.sourceforge.jwebunit.IJWebUnitDialog;
import net.sourceforge.jwebunit.WebTester;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * @author manhole
 */
public class TeedaWebTester extends WebTester {

	public IJWebUnitDialog initializeDialog() {
		return new TeedaHtmlUnitDialog();
	}

	public void assertFormElementPresentById(String formId) {
		final HtmlElement element = getElementById(formId);
		final String actual = element.asText();
		Assert.assertNotNull(actual);
	}

	public void assertTextEquals(final String id, final String text) {
		final HtmlElement element = getElementById(id);
		final String actual = element.asText();
		Assert.assertEquals(text, actual);
	}

	public void assertAttributeEquals(final String id,
			final String attributeName, final String attributeValue) {
		final HtmlElement element = getElementById(id);
		final String actual = element.getAttributeValue(attributeName);
		Assert.assertEquals(attributeValue, actual);
	}

	/*
	 * inputへテキストをセットする。
	 */
	public void setTextById(final String id, final String value) {
		final HtmlElement element = getElementById(id);
		if (element instanceof HtmlInput) {
			final HtmlInput input = (HtmlInput) element;
			input.setValueAttribute(value);
		} else if (element instanceof HtmlTextArea) {
			final HtmlTextArea textArea = (HtmlTextArea) element;
			textArea.setText(value);
		} else {
			Assert.fail("element [" + id
					+ "] is not HtmlElement nor HtmlTextArea: " + element);
		}
	}

	protected HtmlInput getHtmlInputByIdNoException(final String id) {
		final HtmlElement element = getElementByIdNoException(id);
		if (element instanceof HtmlInput) {
			System.out.println(element);
			return (HtmlInput) element;
		}
		return null;
	}

	protected HtmlInput getHtmlInputById(final String id) {
		final HtmlElement element = getElementById(id);
		if (element instanceof HtmlInput) {
			return (HtmlInput) element;
		}
		Assert.fail("element [" + id + "] is not HtmlInput: " + element);
		throw new RuntimeException();
	}

	/*
	 * elementが存在しない場合はfailする。
	 */
	private HtmlElement getElementById(final String id) {
		final HtmlElement element = getElementByIdNoException(id);
		Assert.assertNotNull(element);
		return element;
	}

	private HtmlElement getElementByIdNoException(final String id) {
		final TeedaHtmlUnitDialog dialog = getTeedaHtmlUnitDialog();
		final HtmlElement element = dialog.getElementById(id);
		return element;
	}

	private TeedaHtmlUnitDialog getTeedaHtmlUnitDialog() {
		return (TeedaHtmlUnitDialog) getDialog();
	}

}
