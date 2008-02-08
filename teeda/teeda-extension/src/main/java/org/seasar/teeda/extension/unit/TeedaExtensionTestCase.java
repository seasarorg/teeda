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
package org.seasar.teeda.extension.unit;

import java.io.File;
import java.util.Map;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementNodeDecision;
import org.seasar.teeda.extension.html.HtmlParser;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.BodyElementNodeDecision;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.HtmlNodeHandler;
import org.seasar.teeda.extension.html.impl.HtmlParserImpl;
import org.seasar.teeda.extension.html.impl.InputTypeCheckboxElementNodeDecision;
import org.seasar.teeda.extension.html.impl.InputTypeRadioElementNodeDecision;
import org.seasar.teeda.extension.html.impl.PageDescImpl;
import org.seasar.teeda.extension.jsp.PageContextImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;

/**
 * @author higa
 *
 */
public abstract class TeedaExtensionTestCase extends TeedaTestCase {

    protected PageContextImpl pageContext;

    protected MockTaglibManager taglibManager;

    protected HtmlParserImpl parser;

    protected PageContextImpl getPageContext() {
        return pageContext;
    }

    protected void setUpContainer() throws Throwable {
        super.setUpContainer();
        pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null);
        taglibManager = createTaglibManager();
        parser = new HtmlParserImpl();
        HtmlNodeHandler htmlNodeHandler = new HtmlNodeHandler();
        ElementNodeDecision[] decisions = { new BodyElementNodeDecision(),
                new InputTypeRadioElementNodeDecision(),
                new InputTypeCheckboxElementNodeDecision() };
        htmlNodeHandler.setElementNodeDecisions(decisions);
        getContainer().register(htmlNodeHandler);
    }

    protected void tearDownContainer() throws Throwable {
        super.tearDownContainer();
        pageContext = null;
        taglibManager = null;
    }

    protected MockTaglibManager createTaglibManager() {
        return new MockTaglibManager();
    }

    protected MockTaglibManager getTaglibManager() {
        return taglibManager;
    }

    protected void registerTagElement(String uri, String tagName, Class tagClass) {
        TaglibElement taglibElement;
        if (!taglibManager.hasTaglibElement(uri)) {
            taglibElement = new TaglibElementImpl();
            taglibElement.setUri(uri);
            taglibManager.addTaglibElement(taglibElement);
        } else {
            taglibElement = taglibManager.getTaglibElement(uri);
        }
        TagElement tagElement = new TagElementImpl();
        tagElement.setName(tagName);
        tagElement.setTagClass(tagClass);
        taglibElement.addTagElement(tagElement);
    }

    protected ElementNode createElementNode(String tagName, Map props) {
        return createElementNode(null, null, tagName, props);
    }

    protected ElementNode createElementNode(String namespaceURI,
            String localName, String tagName, Map props) {
        return new ElementNodeImpl(namespaceURI, localName, tagName, props);
    }

    protected PageDesc createPageDesc(Class pageClass, String pageName) {
        return createPageDesc(pageClass, pageName, null);
    }

    protected PageDesc createPageDesc(Class pageClass, String pageName,
            File file) {
        getContainer().register(pageClass, pageName);
        PageDesc pd = new PageDescImpl(pageClass, pageName, file);
        return pd;
    }

    protected ActionDesc createActionDesc(Class actionClass, String actionName) {
        return createActionDesc(actionClass, actionName, null);
    }

    protected ActionDesc createActionDesc(Class actionClass, String actionName,
            File file) {
        getContainer().register(actionClass, actionName);
        ActionDesc ad = new ActionDescImpl(actionClass, actionName, file);
        return ad;
    }

    protected HtmlParser getHtmlParser() {
        return parser;
    }

}