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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import net.sourceforge.jwebunit.htmlunit.HtmlUnitDialog;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.MethodUtil;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
class TeedaHtmlUnitDialog extends HtmlUnitDialog {

	/*
	 * WebTester#dumpHtml()がJWebUnitの実装では文字化けしてしまうため、 当クラスでオーバーライドする。
	 */
	public String getPageSource() {
		final HtmlPage page = getCurrentPage();
		final WebResponse webResponse = page.getWebResponse();
		final String pageEncoding = page.getPageEncoding();
		try {
			final byte[] responseBody = webResponse.getResponseBody();
			final String body = new String(responseBody, pageEncoding);
			return body;
		} catch (UnsupportedEncodingException e) {
			throw new IORuntimeException(e);
		}
	}

	/*
	 * スーパークラスのgetElementメソッドがprivateであるため。
	 */
	public HtmlElement getElementById(final String id) {
		final Method method = getElementMethod();
		final HtmlElement page = (HtmlElement) MethodUtil.invoke(method, this,
				new String[] { id });
		return page;
	}

	/*
	 * スーパークラスの同メソッドがprivateであるため。
	 */
	public HtmlPage getCurrentPage() {
		final Method method = getCurrentPageMethod();
		final HtmlPage page = (HtmlPage) MethodUtil.invoke(method, this, null);
		return page;
	}

	Method getCurrentPageMethod() {
		final String methodName = "getCurrentPage";
		final Class[] argTypes = new Class[] {};
		return getHtmlUnitDialogMethod(methodName, argTypes);
	}

	Method getElementMethod() {
		final String methodName = "getElement";
		final Class[] argTypes = new Class[] { String.class };
		return getHtmlUnitDialogMethod(methodName, argTypes);
	}

	private Method getHtmlUnitDialogMethod(final String methodName,
			final Class[] argTypes) {
		final Class clazz = HtmlUnitDialog.class;
		try {
			final Method method = clazz.getDeclaredMethod(methodName, argTypes);
			method.setAccessible(true);
			return method;
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodRuntimeException(clazz, methodName, argTypes,
					e);
		}
	}

}
