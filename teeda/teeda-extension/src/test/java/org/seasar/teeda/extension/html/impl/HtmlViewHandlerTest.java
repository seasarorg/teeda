/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKit;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.impl.TeedaStateManagerImpl;
import org.seasar.teeda.core.application.impl.TreeStructureManagerImpl;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;
import org.seasar.teeda.core.taglib.html.FormTag;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.UIText;
import org.seasar.teeda.extension.component.html.THtmlForm;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ElementProcessorFactory;
import org.seasar.teeda.extension.html.HtmlParser;
import org.seasar.teeda.extension.html.factory.FormFactory;
import org.seasar.teeda.extension.html.impl.page.FooAction;
import org.seasar.teeda.extension.html.impl.page.FooPage;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.render.html.HtmlTextRenderer;
import org.seasar.teeda.extension.render.html.THtmlFormRenderer;
import org.seasar.teeda.extension.taglib.TFormTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 */
public class HtmlViewHandlerTest extends TeedaExtensionTestCase {

    protected void setUp() throws Exception {
        RenderKit renderKit = getRenderKit();
        renderKit.addRenderer(UIText.COMPONENT_FAMILY,
                UIText.DEFAULT_RENDERER_TYPE, new HtmlTextRenderer());
        THtmlFormRenderer formRenderer = new THtmlFormRenderer();
        formRenderer
                .setComponentIdLookupStrategy(new DefaultComponentIdLookupStrategy());
        renderKit.addRenderer(THtmlForm.COMPONENT_FAMILY,
                "org.seasar.teeda.extension.HtmlForm", formRenderer);
        Application app = getApplication();
        app.addComponent(UIViewRoot.COMPONENT_TYPE, UIViewRoot.class.getName());
        app.addComponent(UIText.COMPONENT_TYPE, UIText.class.getName());
        app.addComponent(THtmlForm.COMPONENT_TYPE, THtmlForm.class.getName());
    }

    public void testRestoreAndRenderView() throws Exception {
        if (true) {
            return;
        }
        getExternalContext().getRequestParameterMap().put("redirect", "true");
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
        convention.addRootPackageName(ClassUtil.getPackageName(getClass()));
        convention.setViewExtension(".html");
        PageDescCacheImpl pageDescCache = new PageDescCacheImpl();
        pageDescCache.setNamingConvention(convention);
        pageDescCache.setContainer(getContainer());
        HtmlSuffixImpl htmlSuffix = new HtmlSuffixImpl();
        pageDescCache.setHtmlSuffix(htmlSuffix);
        register(FooPage.class, "fooPage");
        String path = rootPath + "/foo.html";

        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(convention);
        actionDescCache.setContainer(getContainer());
        actionDescCache.setHtmlSuffix(htmlSuffix);
        register(FooAction.class, "fooAction");

        HtmlDescCacheImpl htmlDescCache = new HtmlDescCacheImpl();
        htmlDescCache.setServletContext(getServletContext());
        htmlDescCache.setContainer(getContainer());
        HtmlParser parser = getHtmlParser();
        htmlDescCache.setHtmlParser(parser);

        FormFactory formFactory = new FormFactory();
        formFactory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { formFactory });
        TagProcessorCacheImpl tagProcessorCache = new TagProcessorCacheImpl();
        tagProcessorCache.setHtmlDescCache(htmlDescCache);
        tagProcessorCache.setPageDescCache(pageDescCache);
        tagProcessorCache.setActionDescCache(actionDescCache);
        tagProcessorCache.setAssembler(assembler);
        TeedaStateManager stateManager = new TeedaStateManagerImpl();
        stateManager.setTreeStructureManager(new TreeStructureManagerImpl());
        tagProcessorCache.setStateManager(stateManager);
        getApplication().setStateManager(stateManager);

        HtmlViewHandler viewHandler = new HtmlViewHandler();
        viewHandler.setTagProcessorCache(tagProcessorCache);
        viewHandler.setHtmlSuffix(htmlSuffix);
        SessionPagePersistence spp = new SessionPagePersistence();
        spp.setNamingConvention(convention);
        spp.setPageDescCache(pageDescCache);
        spp.setActionDescCache(actionDescCache);
        viewHandler.setPagePersistence(spp);
        HtmlComponentInvokerImpl invoker = new HtmlComponentInvokerImpl();
        invoker.setNamingConvention(convention);
        invoker.setPageDescCache(pageDescCache);
        invoker.setActionDescCache(actionDescCache);
        getFacesContext().getViewRoot().setViewId(path);
        FooPage fooPage = (FooPage) getComponent(FooPage.class);
        fooPage.setAaa("123");
        pageDescCache.createPageDesc(path);
        spp.save(getFacesContext(), path);
        assertFalse(fooPage.isInitialized());
        PostbackUtil.setPostback(getExternalContext().getRequestMap(), true);
        viewHandler.restoreView(getFacesContext(), path);
        assertFalse(fooPage.isPrerendered());
        PostbackUtil.setPostback(getExternalContext().getRequestMap(), false);
        viewHandler.restoreView(getFacesContext(), path);

        viewHandler.renderView(getFacesContext(), path);
        assertEquals(
                "<html><body><form id=\"fooForm\" name=\"fooForm\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" action=\"/org/seasar/teeda/extension/html/impl/foo.html\"><input type=\"hidden\" name=\"fooForm/org/seasar/teeda/extension/html/impl/foo.html\" value=\"fooForm\" /></form></body></html>",
                getResponseText());
        assertEquals("123", getExternalContext().getRequestMap().get("aaa"));
    }

    public void testCreateView() throws Exception {
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(ExtensionConstants.TEEDA_EXTENSION_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("form");
        tagElement.setTagClass(TFormTag.class);
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
        String path = rootPath + "/foo.html";

        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(convention);
        actionDescCache.setContainer(getContainer());
        actionDescCache.setHtmlSuffix(htmlSuffix);
        register(FooAction.class, "fooAction");

        HtmlDescCacheImpl htmlDescCache = new HtmlDescCacheImpl();
        htmlDescCache.setServletContext(getServletContext());
        htmlDescCache.setContainer(getContainer());
        HtmlParser parser = getHtmlParser();
        htmlDescCache.setHtmlParser(parser);

        FormFactory formFactory = new FormFactory();
        formFactory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { formFactory });
        TagProcessorCacheImpl tagProcessorCache = new TagProcessorCacheImpl();
        tagProcessorCache.setHtmlDescCache(htmlDescCache);
        tagProcessorCache.setPageDescCache(pageDescCache);
        tagProcessorCache.setActionDescCache(actionDescCache);
        tagProcessorCache.setAssembler(assembler);
        TeedaStateManager stateManager = new TeedaStateManagerImpl();
        stateManager.setTreeStructureManager(new TreeStructureManagerImpl());
        tagProcessorCache.setStateManager(stateManager);
        getApplication().setStateManager(stateManager);

        HtmlViewHandler viewHandler = new HtmlViewHandler();
        viewHandler.setStateManager(stateManager);
        viewHandler.setTagProcessorCache(tagProcessorCache);
        SessionPagePersistence spp = new SessionPagePersistence();
        spp.setPageDescCache(pageDescCache);
        viewHandler.setPagePersistence(spp);
        getFacesContext().getViewRoot().setViewId(path);

        tagProcessorCache.updateTagProcessor(path);
        UIViewRoot root = viewHandler.createView(getFacesContext(), path);
        assertNotNull(root);

        assertTrue(root.getChildCount() > 0);
    }
}