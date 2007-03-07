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
package org.seasar.teeda.unit.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sourceforge.jwebunit.htmlunit.HtmlUnitDialog;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.NoSuchFieldRuntimeException;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.MethodUtil;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 * @author yone
 */
class TeedaHtmlUnitDialog extends HtmlUnitDialog {

    private boolean throwExceptionOnFailingStatusCode;

    //
    //    public void beginAt(final String initialURL, final TestContext context)
    //        throws TestingEngineResponseException {
    //        super.beginAt(initialURL, context);
    //        final WebClient webClient = getWebClient();
    //        webClient
    //            .setThrowExceptionOnFailingStatusCode(throwExceptionOnFailingStatusCode);
    //    }

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
        } catch (final UnsupportedEncodingException e) {
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

    /*
     * スーパークラスの"wc"フィールドがprivateであるため。
     */
    WebClient getWebClient() {
        final Field field = getWebClientField();
        final WebClient webClient = (WebClient) FieldUtil.get(field, this);
        if (webClient == null) {
            throw new NullPointerException("webClient");
        }
        return webClient;
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

    Field getWebClientField() {
        final String fieldName = "wc";
        final Class clazz = HtmlUnitDialog.class;
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (final NoSuchFieldException e) {
            throw new NoSuchFieldRuntimeException(clazz, fieldName, e);
        }
    }

    private Method getHtmlUnitDialogMethod(final String methodName,
        final Class[] argTypes) {
        final Class clazz = HtmlUnitDialog.class;
        try {
            final Method method = clazz.getDeclaredMethod(methodName, argTypes);
            method.setAccessible(true);
            return method;
        } catch (final NoSuchMethodException e) {
            throw new NoSuchMethodRuntimeException(clazz, methodName, argTypes,
                e);
        }
    }

    public boolean isThrowExceptionOnFailingStatusCode() {
        return throwExceptionOnFailingStatusCode;
    }

    public void setThrowExceptionOnFailingStatusCode(
        boolean throwExceptionOnFailingStatusCode) {
        this.throwExceptionOnFailingStatusCode = throwExceptionOnFailingStatusCode;
    }

    // workingFormをindex指定で設定
    public void setWorkingForm(int index) {
    	HtmlForm form = (HtmlForm) getCurrentPage().getForms().get(index);
    	String name = form.getNameAttribute();
    	super.setWorkingForm(name, index);
    }
    
    // workingFormをformのname指定で設定
    public void setWorkingForm(final String formName) {
    	int formSize = getCurrentPage().getForms().size();
    	super.setWorkingForm(formName, formSize);
    }
    
}
