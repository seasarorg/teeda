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

import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.impl.TeedaStateManagerImpl;
import org.seasar.teeda.core.application.impl.TreeStructureManagerImpl;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;
import org.seasar.teeda.core.render.html.HtmlFormRenderer;
import org.seasar.teeda.core.taglib.html.FormTag;
import org.seasar.teeda.extension.component.UIText;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
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
        formRenderer.setComponentIdLookupStrategy(new DefaultComponentIdLookupStrategy());
        renderKit.addRenderer(HtmlForm.COMPONENT_FAMILY,
                "javax.faces.Form", formRenderer);
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
        
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        String rootPath = "/" + ClassUtil.getPackageName(getClass()).replace('.', '/');
        naming.setHtmlRootPath(rootPath);
        PageDescCacheImpl pageDescCache = new PageDescCacheImpl();
        pageDescCache.setServletContext(getServletContext());
        pageDescCache.setHtmlAutoNaming(naming);
        pageDescCache.setContainer(getContainer());
        register(FooPage.class, "fooPage");
        String path = rootPath + "/foo.html";
        
        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setServletContext(getServletContext());
        actionDescCache.setHtmlAutoNaming(naming);
        actionDescCache.setContainer(getContainer());
        register(FooAction.class, "fooAction");
        
        HtmlDescCacheImpl htmlDescCache = new HtmlDescCacheImpl();
        htmlDescCache.setServletContext(getServletContext());
        
        FormFactory formFactory = new FormFactory();
        formFactory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.addFactory(formFactory);
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
        getFacesContext().getViewRoot().setViewId(path);
        viewHandler.restoreView(getFacesContext(), path);
        viewHandler.renderView(path, getRequest(), getResponse());
        
        assertEquals("1", "<html><body><form id=\"fooForm\" name=\"fooForm\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" action=\"/org/seasar/teeda/extension/html/impl/foo.html\"><input type=\"hidden\" name=\"fooForm/org/seasar/teeda/extension/html/impl/foo.html\" value=\"fooForm\" /></form></body></html>", getResponseText());
    }
}