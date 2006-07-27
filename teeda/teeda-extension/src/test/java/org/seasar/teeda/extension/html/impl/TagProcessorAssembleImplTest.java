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

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.CommandButtonTag;
import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.core.taglib.html.OutputTextTag;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.ElementProcessorFactory;
import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorAssembler;
import org.seasar.teeda.extension.html.TextProcessor;
import org.seasar.teeda.extension.html.factory.CommandButtonFactory;
import org.seasar.teeda.extension.html.factory.InputTextFactory;
import org.seasar.teeda.extension.html.factory.OutputTextFactory;
import org.seasar.teeda.extension.html.factory.SelectManyCheckboxFactory;
import org.seasar.teeda.extension.html.factory.SelectOneMenuFactory;
import org.seasar.teeda.extension.html.factory.SelectOneRadioFactory;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TSelectManyCheckboxTag;
import org.seasar.teeda.extension.taglib.TSelectOneMenuTag;
import org.seasar.teeda.extension.taglib.TSelectOneRadioTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 *
 */
public class TagProcessorAssembleImplTest extends TeedaExtensionTestCase {

    public void testAssembleElementNodeAsText() throws Exception {
        String path = convertPath("emptyHtmlTag.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
    }

    public void testAssembleElementNodeAsText2() throws Exception {
        String path = convertPath("allText.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body>Hello</body></html>", textProcessor
                .getValue());
    }

    public void testAssembleElementNodeAsText3() throws Exception {
        String path = convertPath("allText2.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"xxx\">Hello</body></html>",
                textProcessor.getValue());
    }

    public void testAssembleElementNodeAsText4() throws Exception {
        String path = convertPath("allText3.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"xxx\" /></html>", textProcessor
                .getValue());
    }

    public void testAssembleElementNodeAsText5() throws Exception {
        String path = convertPath("allText4.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body><span id=\"xxx\" />Hello</body></html>",
                textProcessor.getValue());
    }

    public void testAssembleElementNodeAsText6() throws Exception {
        String path = convertPath("allText5.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"aaa\">Hello</body></html>",
                textProcessor.getValue());
    }

    public void testAssembleElementNodeAsText7() throws Exception {
        String path = convertPath("allText5.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, null, null);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"aaa\">Hello</body></html>",
                textProcessor.getValue());
    }

    public void testAssembleElementNode() throws Exception {
        String path = convertPath("element.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "inputText",
                InputTextTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        InputTextFactory factory = new InputTextFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { factory });
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body>", textProcessor.getValue());
        ElementProcessor elementProcessor = (ElementProcessor) viewRoot
                .getChild(1);
        assertEquals("4", 1, elementProcessor.getChildSize());
        textProcessor = (TextProcessor) elementProcessor.getChild(0);
        assertEquals("5", "<hoge id=\"xxx\" />Hello", textProcessor.getValue());
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("6", "</body></html>", textProcessor.getValue());
    }

    public void testAssembleElementNode2() throws Exception {
        String path = convertPath("element2.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "commandButton",
                CommandButtonTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { factory });
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body>", textProcessor.getValue());
        assertTrue("4", viewRoot.getChild(1) instanceof ElementProcessor);
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("5", "</body></html>", textProcessor.getValue());
    }

    public void testOutputText() throws Exception {
        String path = convertPath("outputText.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "outputText",
                OutputTextTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        OutputTextFactory factory = new OutputTextFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { factory });
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue(root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals(3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("<html><body>", textProcessor.getValue());
        assertTrue(viewRoot.getChild(1) instanceof ElementProcessor);
        ElementProcessor ep = (ElementProcessor) viewRoot.getChild(1);
        assertEquals(0, ep.getChildSize());
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("</body></html>", textProcessor.getValue());
    }

    public void testSelectOneMenu() throws Exception {
        String path = convertPath("selectOneMenu.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectOneMenu", TSelectOneMenuTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        SelectOneMenuFactory factory = new SelectOneMenuFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { factory });
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue(root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals(3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("<html><body>", textProcessor.getValue());
        assertTrue(viewRoot.getChild(1) instanceof ElementProcessor);
        ElementProcessor ep = (ElementProcessor) viewRoot.getChild(1);
        assertEquals("#{fooPage.bbb}", ep.getProperty("value"));
        assertEquals("#{fooPage.bbbItems}", ep.getProperty("items"));
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("</body></html>", textProcessor.getValue());
    }

    public void testSelectOneRadio() throws Exception {
        String path = convertPath("selectOneRadio.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectOneRadio", TSelectOneRadioTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        SelectOneRadioFactory factory = new SelectOneRadioFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { factory });
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue(root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals(3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("<html><body>", textProcessor.getValue());
        assertTrue(viewRoot.getChild(1) instanceof ElementProcessor);
        ElementProcessor ep = (ElementProcessor) viewRoot.getChild(1);
        assertEquals("#{fooPage.bbb}", ep.getProperty("value"));
        assertEquals("#{fooPage.bbbItems}", ep.getProperty("items"));
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("</body></html>", textProcessor.getValue());
    }

    public void testSelectManyCheckbox() throws Exception {
        String path = convertPath("selectManyCheckbox.html");
        HtmlDescCacheImpl cache = createHtmlDescCacheImpl();
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDescImpl actionDesc = new ActionDescImpl(FooAction.class,
                "fooAction");
        registerTaglibElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "selectManyCheckbox", TSelectManyCheckboxTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        SelectManyCheckboxFactory factory = new SelectManyCheckboxFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.setFactories(new ElementProcessorFactory[] { factory });
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc, actionDesc);
        assertTrue(root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals(3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("<html><body>", textProcessor.getValue());
        assertTrue(viewRoot.getChild(1) instanceof ElementProcessor);
        ElementProcessor ep = (ElementProcessor) viewRoot.getChild(1);
        assertEquals("#{fooPage.ccc}", ep.getProperty("value"));
        assertEquals("#{fooPage.cccItems}", ep.getProperty("items"));
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("</body></html>", textProcessor.getValue());
    }
    
    protected HtmlDescCacheImpl createHtmlDescCacheImpl() {
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlParserImpl htmlParser = new HtmlParserImpl();
        cache.setHtmlParser(htmlParser);
        return cache;
    }
}