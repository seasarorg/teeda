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
package org.seasar.teeda.extension.unit;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.jsp.PageContextImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;

/**
 * @author higa
 *
 */
public abstract class TeedaExtensionTestCase extends TeedaTestCase {

    private PageContextImpl pageContext;

    private MockTaglibManager taglibManager;

    private Map taglibMap;

    protected PageContextImpl getPageContext() {
        return pageContext;
    }

    protected void setUpContainer() throws Throwable {
        super.setUpContainer();
        pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), getRequest(), getResponse(), null,
                "UTF-8");
        taglibManager = createTaglibManager();
        taglibMap = new HashMap();
    }

    protected void tearDownContainer() throws Throwable {
        super.tearDownContainer();
        pageContext = null;
        taglibManager = null;
        taglibMap = null;
    }

    protected MockTaglibManager createTaglibManager() {
        return new MockTaglibManager();
    }

    protected MockTaglibManager getTaglibManager() {
        return taglibManager;
    }

    protected void registerTaglibElement(String tagLibUri, String tagName,
            Class tagClass) {
        TaglibElement taglib = null;
        if (taglibMap.containsKey(tagLibUri)) {
            taglib = (TaglibElement) taglibMap.get(tagLibUri);
        } else {
            taglib = new TaglibElementImpl();
            taglib.setUri(tagLibUri);
            taglibMap.put(tagLibUri, taglib);
        }
        TagElement tagElement = new TagElementImpl();
        tagElement.setName(tagName);
        tagElement.setTagClass(tagClass);
        taglib.addTagElement(tagElement);
        taglibManager.addTaglibElement(taglib);
    }

}