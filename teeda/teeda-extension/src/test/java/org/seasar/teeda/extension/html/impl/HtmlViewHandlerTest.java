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
package org.seasar.teeda.extension.html.impl;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.render.RenderKit;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.impl.TeedaStateManagerImpl;
import org.seasar.teeda.core.application.impl.TreeStructureManagerImpl;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;
import org.seasar.teeda.core.render.html.HtmlFormRenderer;
import org.seasar.teeda.core.render.html.support.RenderAttributesImpl;
import org.seasar.teeda.core.taglib.html.FormTag;
import org.seasar.teeda.extension.component.UIText;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ElementProcessorFactory;
import org.seasar.teeda.extension.html.factory.FormFactory;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.render.html.HtmlTextRenderer;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 *
 */
public class HtmlViewHandlerTest extends TeedaExtensionTestCase {

    protected void setUp() throws Exception {
        RenderKit renderKit = getRenderKit();
        renderKit.addRenderer(UIText.COMPONENT_FAMILY,
                UIText.DEFAULT_RENDERER_TYPE, new HtmlTextRenderer());
        HtmlFormRenderer formRenderer = new HtmlFormRenderer();
        formRenderer
                .setComponentIdLookupStrategy(new DefaultComponentIdLookupStrategy());
        final RenderAttributesImpl renderAttributesImpl = new RenderAttributesImpl();
        renderAttributesImpl.initialize();
        formRenderer.setRenderAttributes(renderAttributesImpl);
        renderKit.addRenderer(HtmlForm.COMPONENT_FAMILY, "javax.faces.Form",
                formRenderer);
        Application app = getApplication();
        app.addComponent(UIViewRoot.COMPONENT_TYPE, UIViewRoot.class.getName());
        app.addComponent(UIText.COMPONENT_TYPE, UIText.class.getName());
        app.addComponent(HtmlForm.COMPONENT_TYPE, HtmlForm.class.getName());
    }

    public void testRenderView() throws Exception {
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
        register(FooPage.class, "fooPage");
        String path = rootPath + "/foo.html";

        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(convention);
        actionDescCache.setContainer(getContainer());
        register(FooAction.class, "fooAction");

        HtmlDescCacheImpl htmlDescCache = new HtmlDescCacheImpl();
        htmlDescCache.setServletContext(getServletContext());
        HtmlParserImpl parser = new HtmlParserImpl();
        htmlDescCache.setHtmlParser(parser);

        FormFactory formFactory = new FormFactory();
        formFactory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { formFactory });
        TagProcessorCacheImpl tagProcessorCache = new TagProcessorCacheImpl();
        tagProcessorCache.setHtmlPathCache(new HtmlPathCacheImpl());
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
        viewHandler.setPageDescCache(pageDescCache);
        viewHandler.setActionDescCache(actionDescCache);
        SessionPagePersistence spp = new SessionPagePersistence();
        spp.setPageDescCache(pageDescCache);
        viewHandler.setPagePersistence(spp);
        getFacesContext().getViewRoot().setViewId(path);
        FooPage fooPage = (FooPage) getComponent(FooPage.class);
        fooPage.setAaa("123");
        pageDescCache.createPageDesc(path);
        spp.save(getFacesContext(), path);
        viewHandler.restoreView(getFacesContext(), path);
        viewHandler.renderView(getFacesContext(), path);

        assertEquals(
                "<html><body><form id=\"fooForm\" name=\"fooForm\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" action=\"/org/seasar/teeda/extension/html/impl/foo.html\"><input type=\"hidden\" name=\"fooForm/org/seasar/teeda/extension/html/impl/foo.html\" value=\"fooForm\" /></form></body></html>",
                getResponseText());
        assertTrue(fooPage.isInitialized());
        assertEquals("123", getExternalContext().getRequestMap().get("aaa"));
    }
}