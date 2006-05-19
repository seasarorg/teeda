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

import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.InputTextTag;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorAssembler;
import org.seasar.teeda.extension.html.TextProcessor;
import org.seasar.teeda.extension.html.factory.InputTextFactory;
import org.seasar.teeda.extension.mock.MockTaglibManager;


/**
 * @author higa
 *
 */
public class TagProcessorAssembleImplTest extends S2FrameworkTestCase {
	
	public void testAssembleElementNodeAsText() throws Exception {
        String path = convertPath("emptyHtmlTag.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
	}
    
    public void testAssembleElementNodeAsText2() throws Exception {
        String path = convertPath("allText.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body>Hello</body></html>", textProcessor.getValue());
    }
    
    public void testAssembleElementNodeAsText3() throws Exception {
        String path = convertPath("allText2.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"xxx\">Hello</body></html>", textProcessor.getValue());
    }
    
    public void testAssembleElementNodeAsText4() throws Exception {
        String path = convertPath("allText3.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"xxx\" /></html>", textProcessor.getValue());
    }
    
    public void testAssembleElementNodeAsText5() throws Exception {
        String path = convertPath("allText4.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body><span id=\"xxx\" />Hello</body></html>", textProcessor.getValue());
    }
    
    public void testAssembleElementNodeAsText6() throws Exception {
        String path = convertPath("allText5.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        TagProcessorAssembler assembler = new TagProcessorAssemblerImpl();
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 1, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body id=\"aaa\">Hello</body></html>", textProcessor.getValue());
    }
    
    public void testAssembleElementNode() throws Exception {
        String path = convertPath("element.html");
        HtmlDescCacheImpl cache = new HtmlDescCacheImpl();
        cache.setServletContext(getServletContext());
        HtmlDesc htmlDesc = cache.createHtmlDesc(path);
        PageDescImpl pageDesc = new PageDescImpl(FooPage.class);
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement inputTextTagElement = new TagElementImpl();
        inputTextTagElement.setName("inputText");
        inputTextTagElement.setTagClass(InputTextTag.class);
        jsfHtml.addTagElement(inputTextTagElement);
        taglibManager.addTaglibElement(jsfHtml);
        InputTextFactory factory = new InputTextFactory();
        factory.setTaglibManager(taglibManager);
        TagProcessorAssemblerImpl assembler = new TagProcessorAssemblerImpl();
        assembler.addFactory(factory);
        TagProcessor root = assembler.assemble(htmlDesc, pageDesc);
        assertTrue("1", root instanceof ElementProcessor);
        ElementProcessor viewRoot = (ElementProcessor) root;
        assertEquals("2", 3, viewRoot.getChildSize());
        TextProcessor textProcessor = (TextProcessor) viewRoot.getChild(0);
        assertEquals("3", "<html><body>", textProcessor.getValue());
        ElementProcessor elementProcessor = (ElementProcessor) viewRoot.getChild(1);
        assertEquals("4", 1, elementProcessor.getChildSize());
        textProcessor = (TextProcessor) elementProcessor.getChild(0);
        assertEquals("5", "<hoge id=\"xxx\" />Hello", textProcessor.getValue());
        textProcessor = (TextProcessor) viewRoot.getChild(2);
        assertEquals("6", "</body></html>", textProcessor.getValue());
    }
}