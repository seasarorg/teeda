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

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.application.NavigationHandlerImplTest;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationResource;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.taglib.html.FormTag;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.html.impl.page.Foo2Page;
import org.seasar.teeda.extension.html.impl.page.FooPage;
import org.seasar.teeda.extension.mock.MockTaglibManager;

/**
 * @author shot
 */
public class HtmlNavigationHandlerTest extends TeedaTestCase {

    public void testHandleNavigation_redirect() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("form");
        tagElement.setTagClass(FormTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);

        NamingConventionImpl convention = new NamingConventionImpl();
        String rootPath = "/"
                + ClassUtil.getPackageName(getClass()).replace('.', '/');
        convention.setViewRootPath(rootPath);
        convention.setViewExtension(".html");
        PageDescCacheImpl pageDescCache = new PageDescCacheImpl();
        pageDescCache.setNamingConvention(convention);
        pageDescCache.setContainer(getContainer());
        HtmlSuffixImpl htmlSuffix = new HtmlSuffixImpl();
        pageDescCache.setHtmlSuffix(htmlSuffix);
        register(FooPage.class, "fooPage");
        register(Foo2Page.class, "foo2Page");
        String path = rootPath + "/foo.html";
        String path2 = rootPath + "/foo2.html";

        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId(path);
        context.setViewRoot(root);
        NavigationContext navContext = NavigationHandlerImplTest
                .createNavigationContext(path, null, "foo2", path2, true);
        NavigationResource.addNavigationContext(navContext);

        // ## Act ##
        SessionPagePersistence spp = new SessionPagePersistence();
        spp.setPageDescCache(pageDescCache);
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        handler.setPagePersistence(spp);
        handler.setHtmlSuffix(htmlSuffix);
        FooPage fooPage = (FooPage) getComponent(FooPage.class);
        fooPage.setAaa("123");
        handler.handleNavigation(context, null, "foo2");

        Map subappScope = SubApplicationScope
                .getOrCreateContext(getFacesContext());
        // ## Assert ##
        assertNotNull(subappScope.get(PagePersistence.SUBAPPLICATION_SCOPE_KEY));
    }

    public void testCalcPathFromOutcome() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        handler.setNamingConvention(new NamingConventionImpl());
        handler.setHtmlSuffix(new HtmlSuffixImpl());
        handler.setServletContext(getServletContext());
        assertEquals("/view/add/addResult.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/add/addInput.html", "addResult"));
        assertEquals("/view/hello/hello.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/add/addResult.html", "hello_Hello"));
    }

    public void testCalcPathFromOutcome2() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        handler.setNamingConvention(new NamingConventionImpl());
        handler.setHtmlSuffix(new HtmlSuffixImpl());
        assertNull(handler.calcPathFromOutcome(getFacesContext(),
                "/view/add/addInput.html", null));
    }

    public void testCalcPathFromOutcome3() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        handler.setNamingConvention(new NamingConventionImpl());
        HtmlSuffixImpl htmlSuffix = new HtmlSuffixImpl();
        htmlSuffix.setupSuffix(getFacesContext(), "/view/add/addInput_i.html");
        handler.setHtmlSuffix(htmlSuffix);
        handler.setServletContext(getServletContext());
        assertEquals("/view/add/addResult_i.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/add/addInput_i.html", "addResult"));
    }

    public void testCalcPathFromOutcome4() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        final NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("");
        handler.setNamingConvention(nc);
        handler.setHtmlSuffix(new HtmlSuffixImpl());
        handler.setServletContext(getServletContext());
        assertEquals("/add/addResult.html", handler.calcPathFromOutcome(
                getFacesContext(), "/add/addInput.html", "addResult"));
        assertEquals("/hello/hello.html", handler.calcPathFromOutcome(
                getFacesContext(), "/add/addResult.html", "hello_Hello"));
    }

    public void testCalcPathFromOutcome5() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        final NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("");
        handler.setNamingConvention(nc);
        handler.setHtmlSuffix(new HtmlSuffixImpl());
        handler.setServletContext(getServletContext());
        assertEquals("/add/addResult.html", handler.calcPathFromOutcome(
                getFacesContext(), "/add/addInput.html", "add_addResult"));
        assertEquals("/hello/hello2/hello.html", handler.calcPathFromOutcome(
                getFacesContext(), "/add/addResult.html", "hello_hello2_Hello"));
    }

    /**
     * https://www.seasar.org/issues/browse/TEEDA-483
     */
    public void testCalcPathFromOutcome6() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        final NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("/view/hoge");
        handler.setNamingConvention(nc);
        handler.setHtmlSuffix(new HtmlSuffixImpl());
        handler.setServletContext(getServletContext());
        assertEquals("/view/hoge/add/addResult.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/hoge/add/addInput.html", "add_addResult"));
        assertEquals("/view/hoge/bbb/addResult.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/hoge/add/addInput.html", "bbb_addResult"));
        assertEquals("/view/hoge/hello/hello2/hello.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/hoge/add/addResult.html", "hello_hello2_Hello"));
    }

    /**
     * https://www.seasar.org/issues/browse/TEEDA-483
     */
    public void testCalcPathFromOutcome7() throws Exception {
        HtmlNavigationHandler handler = new HtmlNavigationHandler();
        final NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("/view/hoge");
        handler.setNamingConvention(nc);
        handler.setHtmlSuffix(new HtmlSuffixImpl());
        handler.setServletContext(getServletContext());
        assertEquals("/view/hoge/addResult.html", handler.calcPathFromOutcome(
                getFacesContext(), "/view/hoge/addInput.html", "addResult"));
    }

}
