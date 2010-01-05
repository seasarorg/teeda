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
package org.seasar.teeda.extension.html.impl;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.impl.page.FooAction;

/**
 * @author higa
 *
 */
public class ActionDescCacheImplTest extends S2FrameworkTestCase {

    public void testCreateAndGetPageDesc() throws Exception {
        NamingConventionImpl convention = new NamingConventionImpl();
        String rootPath = "/"
                + ClassUtil.getPackageName(getClass()).replace('.', '/');
        convention.setViewRootPath(rootPath);
        convention.setViewExtension(".html");
        HtmlSuffixImpl htmlSuffix = new HtmlSuffixImpl();
        ActionDescCacheImpl cache = new ActionDescCacheImpl();
        cache.setNamingConvention(convention);
        cache.setContainer(getContainer());
        cache.setHtmlSuffix(htmlSuffix);
        register(FooAction.class, "fooAction");

        String path = rootPath + "/foo.html";
        ActionDesc actionDesc = cache.createActionDesc(path);
        assertNotNull("1", actionDesc);
        assertSame("2", actionDesc, cache.getActionDesc(path));
        assertNull("3", cache.createActionDesc(rootPath + "xxx.html"));
    }
}