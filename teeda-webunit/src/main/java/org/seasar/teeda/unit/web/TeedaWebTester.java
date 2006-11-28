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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;
import net.sourceforge.jwebunit.IJWebUnitDialog;
import net.sourceforge.jwebunit.WebTester;
import net.sourceforge.jwebunit.html.Table;

import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * @author manhole
 * @author shot
 * 
 * TODO : WebTesterを継承するのをはずして、必要な機能だけを以下のRuleで提供する。<br/>
 * <p>
 * -getXxxById()/setXxxById() : idベースで対応するコンポーネントを探しにいく
 * </p>
 * <p>
 * -getXxxByName()/setXxxByName() : nameベースで対応するコンポーネントを探しにいく
 * </p>
 * 
 */
public class TeedaWebTester {

    private WebTester tester;

    public TeedaWebTester() {
        this(new WebTesterWrapper());
    }

    public TeedaWebTester(final WebTester tester) {
        this.tester = tester;
        tester.initializeDialog();
    }

    public void beginAt(final String relativeUrl) {
        tester.beginAt(relativeUrl);
    }

    public void beginAt(final String baseUrl, final String relativeUrl) {
        tester.getTestContext().setBaseUrl(baseUrl);
        beginAt(relativeUrl);
    }

    public void setBaseUrl(final String baseUrl) {
        tester.getTestContext().setBaseUrl(baseUrl);
    }

    public void setLocale(final Locale locale) {
        tester.getTestContext().setLocale(locale);
    }

    public void dumpHtml() {
        tester.dumpHtml();
    }

    // should know current uri
    public String getCurrentUri() {
        final TeedaHtmlUnitDialog dialog = getTeedaHtmlUnitDialog();
        final HtmlPage currentPage = dialog.getCurrentPage();
        final URL url = currentPage.getWebResponse().getUrl();
        return url.toString();
    }

    public void submitById(final String id) {
        try {
            final HtmlElement element = getElementById(id);
            if ((element instanceof HtmlButtonInput)
                || (element instanceof HtmlSubmitInput)) {
                final HtmlInput input = (HtmlInput) element;
                input.click();
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void submitByName(final String name) {
        tester.submit(name);
    }

    public void submit() {
        tester.submit();
    }

    // Assertions
    public void assertFormElementPresentById(final String formId) {
        final HtmlElement element = getElementById(formId);
        Assert.assertTrue(element instanceof HtmlForm);
        final String actual = element.asText();
        Assert.assertNotNull(actual);
    }

    public void assertTextEqualsById(final String id, final String expected) {
        final HtmlElement element = getElementById(id);
        final String actual = element.asText();
        Assert.assertEquals(expected, actual);
    }

    public void assertTextPresent(final String text) {
        tester.assertTextPresent(text);
    }

    public void assertTitleEquals(final String title) {
        tester.assertTitleEquals(title);
    }

    public void assertTitleMatch(final String regexp) {
        tester.assertTitleMatch(regexp);
    }

    public void assertTextEqualsByName(final String name, final String expected) {
        tester.assertTextFieldEquals(name, expected);
    }

    public void assertTextNotPresent(final String text) {
        tester.assertTextNotPresent(text);
    }

    public void assertTextInElementById(final String id, final String text) {
        tester.assertTextInElement(id, text);
    }

    public void assertAttributeEqualsById(final String id,
        final String attributeName, final String attributeValue) {
        final HtmlElement element = getElementById(id);
        final String actual = element.getAttributeValue(attributeName);
        Assert.assertEquals(attributeValue, actual);
    }

    public void assertAttributeExistsById(final String id,
        final String attributeName) {
        if (hasAttribute(id, attributeName)) {
            return;
        }
        Assert
            .fail("attribute [" + attributeName + "] is not exists. id:" + id);
    }

    public void assertAttributeNotExistsById(final String id,
        final String attributeName) {
        if (!hasAttribute(id, attributeName)) {
            return;
        }
        Assert.fail("attribute [" + attributeName + "] is exists. id:" + id);
    }

    private boolean hasAttribute(final String id, final String attributeName) {
        final HtmlElement element = getElementById(id);
        final boolean attributeDefined = element
            .isAttributeDefined(attributeName);
        return attributeDefined;
    }

    public void assertElementPresentById(final String id) {
        tester.assertElementPresent(id);
    }

    public void assertElementNotPresentById(final String id) {
        tester.assertElementNotPresent(id);
    }

    public void assertMatchInElementById(final String id, final String regexp) {
        tester.assertMatchInElement(id, regexp);
    }

    public void assertNoMatchInElementById(final String id, final String regexp) {
        tester.assertNoMatchInElement(id, regexp);
    }

    public void assertTableEqualsById(final String id,
        final String[][] expectedTables) {
        // TODO 実際にはsummary属性も使われてしまっている
        tester.assertTableEquals(id, expectedTables);
    }

    public void assertTableEqualsById(final String id,
        final Table expectedTables) {
        // TODO 実際にはsummary属性も使われてしまっている
        tester.assertTableEquals(id, expectedTables);
    }

    public void assertTableMatchById(final String id,
        final String[][] expectedTable) {
        // TODO 実際にはsummary属性も使われてしまっている
        tester.assertTableMatch(id, expectedTable);
    }

    public void assertRadioOptionSelectedByName(final String name,
        final String radioOption) {
        tester.assertRadioOptionSelected(name, radioOption);
    }

    public void assertRadioOptionSelectedById(final String id,
        final String radioOption) {
        final HtmlElement element = getElementById(id);
        if (element instanceof HtmlRadioButtonInput) {
            final HtmlRadioButtonInput radio = (HtmlRadioButtonInput) element;
            final String value = radio.getValueAttribute();
            Assert.assertEquals(radioOption, value);
        } else {
            throw new RuntimeException("not type");
        }
    }

    public void assertSelectedOptionValueEqualsByName(final String name,
        final String value) {
        tester.assertSelectedOptionValueEquals(name, value);
    }

    public void assertSelectedOptionValueEqualsById(final String id,
        final String value) {
        final HtmlElement element = getElementById(id);
        final String[] values = new String[] { value };
        if (element instanceof HtmlSelect) {
            final HtmlSelect select = (HtmlSelect) element;
            final List selected = select.getSelectedOptions();
            Assert.assertEquals(values.length, selected.size());
            for (int i = 0; i < values.length; i++) {
                final HtmlOption option = (HtmlOption) selected.get(i);
                Assert.assertEquals(values[i], option.getValueAttribute());
            }
        } else {
            throw new RuntimeException("not type");
        }
    }

    public void assertCheckboxSelectedByName(final String name) {
        tester.assertCheckboxSelected(name);
    }

    public void assertCheckboxNotSelectedByName(final String name) {
        tester.assertCheckboxNotSelected(name);
    }

    // setter/getters
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

    public void checkCheckboxByName(final String name) {
        tester.checkCheckbox(name);
    }

    public void checkCheckboxByName(final String name, final String value) {
        tester.checkCheckbox(name, value);
    }

    public void uncheckCheckboxByName(final String name) {
        tester.uncheckCheckbox(name);
    }

    public void uncheckCheckboxByName(final String name, final String value) {
        tester.uncheckCheckbox(name, value);
    }

    public void selectOptionValueByName(final String name, final String value) {
        tester.selectOptionByValue(name, value);
    }

    public void selectOptionValueById(final String id, final String value) {
        throw new PleaseRegisterToJIRAException();
    }

    public void setTextByName(final String name, final String value) {
        tester.setTextField(name, value);
    }

    public Table getTableById(final String id) {
        // TODO 実際にはsummary属性も使われてしまっている
        return tester.getTable(id);
    }

    public void clickRadioOptionByName(final String radioGroupName,
        final String radioOption) {
        tester.clickRadioOption(radioGroupName, radioOption);
    }

    public void clickRadioOptionById(final String radioGroupId,
        final String radioOption) {
        final HtmlElement element = getElementById(radioGroupId);
        if (element instanceof HtmlRadioButtonInput) {
            final HtmlRadioButtonInput rb = (HtmlRadioButtonInput) element;
            if (!rb.isChecked()) {
                try {
                    rb.click();
                } catch (final IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("checkCheckbox failed :" + e);
                }
            }
        } else {
            Assert.fail("element is not HtmlRadioButtonInput");
        }
    }

    // jwebunit側を置き換えるのが面倒なので後回し。
    // 
    //    /**
    //     * レスポンスコードが200番台以外の場合に例外を投げるかどうかを設定します。
    //     * デフォルト(jWebUnitのデフォルト)はtrueです。
    //     * 
    //     * @param enabled trueの場合は例外を投げます。 
    //     */
    //    public void setThrowExceptionOnFailingStatusCode(final boolean enabled) {
    //        final TeedaHtmlUnitDialog dialog = getTeedaHtmlUnitDialog();
    //        dialog.setThrowExceptionOnFailingStatusCode(enabled);
    //    }
    //
    //    private WebClient getWebClient() {
    //        final TeedaHtmlUnitDialog dialog = getTeedaHtmlUnitDialog();
    //        final WebClient webClient = dialog.getWebClient();
    //        return webClient;
    //    }

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
        return (TeedaHtmlUnitDialog) tester.getDialog();
    }

    private static class WebTesterWrapper extends WebTester {

        public IJWebUnitDialog initializeDialog() {
            return new TeedaHtmlUnitDialog();
        }

    }

    private static class PleaseRegisterToJIRAException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private static final String url = "https://www.seasar.org/issues/browse/TEEDA";

        public PleaseRegisterToJIRAException() {
            super("this method is not implemented yet. go to " + url);
        }

        public String getUrl() {
            return url;
        }

    }

}
