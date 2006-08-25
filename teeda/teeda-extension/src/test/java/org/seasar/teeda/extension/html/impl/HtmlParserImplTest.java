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

import java.io.ByteArrayInputStream;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.extension.html.DocumentNode;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;
import org.seasar.teeda.extension.html.TextNode;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author manhole
 */
public class HtmlParserImplTest extends TeedaExtensionTestCase {

    public void testBasic() throws Exception {
        // ## Arrange ##
        HtmlParser parser = getHtmlParser();

        // ## Act ##
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<z><y id=\"y\"><x /></y></z>".getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof DocumentNode);
        DocumentNode docRoot = (DocumentNode) root;
        assertEquals(1, docRoot.getChildSize());
        final ElementNode z = (ElementNode) docRoot.getChild(0);
        assertEquals(1, z.getChildSize());
        final HtmlNode y = z.getChild(0);
        assertEquals(true, y instanceof ElementNode);
        ElementNode yn = (ElementNode) y;
        assertEquals(1, yn.getChildSize());
        HtmlNode x = yn.getChild(0);
        assertEquals(true, x instanceof TextNode);
        assertEquals("<z><y id=\"y\"><x></x></y></z>", root.toString());
    }

    public void testForceElementNode1() throws Exception {
        // ## Arrange ##
        HtmlNodeHandler handler = new HtmlNodeHandler();
        HtmlParser parser = getHtmlParser();
        handler.addForceElementNodeTagName("y");

        // ## Act ##
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<z><y id=\"y\"><x /></y></z>".getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof DocumentNode);
        DocumentNode docRoot = (DocumentNode) root;
        assertEquals(1, docRoot.getChildSize());

        final ElementNode z = (ElementNode) docRoot.getChild(0);
        assertEquals(1, z.getChildSize());

        final HtmlNode y = z.getChild(0);
        assertEquals(true, y instanceof ElementNode);
    }

    public void testForceElementNode2() throws Exception {
        // ## Arrange ##
        HtmlParser parser = getHtmlParser();
        HtmlNodeHandler handler = (HtmlNodeHandler) getContainer()
                .getComponent(HtmlNodeHandler.class);
        handler.addForceElementNodeTagName("y");

        // ## Act ##
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<z><y id=\"y\"><x /></y><y><x /></y></z>".getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof DocumentNode);
        DocumentNode docRoot = (DocumentNode) root;
        assertEquals(1, docRoot.getChildSize());
        ElementNode z = (ElementNode) docRoot.getChild(0);
        assertEquals(2, z.getChildSize());
        {
            final ElementNode y = (ElementNode) z.getChild(0);
            assertEquals(1, y.getChildSize());
            final HtmlNode x = y.getChild(0);
            assertEquals(true, x instanceof ElementNode);
        }
        {
            final HtmlNode y = (HtmlNode) z.getChild(1);
            assertEquals(true, y instanceof TextNode);
        }
    }

    public void testEncodingCustomizeFail() throws Exception {
        // ## Arrange ##
        HtmlParserImpl parser = new HtmlParserImpl();
        parser.setEncoding("no_such_encoding");
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(
                    "<z><y id=\"y\"><x /></y><y><x /></y></z>".getBytes());
            parser.parse(is);
            fail();
        } catch (IORuntimeException expected) {
        }
    }

    public void testWriteXmlDeclarationAndDocType() throws Exception {
        // ## Arrange ##
        HtmlParser parser = getHtmlParser();

        // ## Act ##
        String str = "<?xml version=\"1.0\"?>"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ja\" lang=\"ja\"><z id=\"&nbsp;aaa&nbsp;\"><y id=\"y\">&nbsp;aaa&nbsp;<x /></y></z></html>";
        ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof DocumentNode);
        DocumentNode docType = (DocumentNode) root;
        assertEquals(1, docType.getChildSize());
        final ElementNode html = (ElementNode) docType.getChild(0);
        assertEquals(1, html.getChildSize());
        final HtmlNode z = html.getChild(0);
        assertTrue(z instanceof ElementNode);
        ElementNode zm = (ElementNode) z;
        HtmlNode y = zm.getChild(0);
        assertTrue(y instanceof ElementNode);
        ElementNode ym = (ElementNode) y;
        HtmlNode x = ym.getChild(0);
        assertTrue(x instanceof TextNode);
        assertEquals(
                "<?xml version=\"1.0\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xml:lang=\"ja\" lang=\"ja\" xmlns=\"http://www.w3.org/1999/xhtml\">"
                        + "<z id=\"&nbsp;aaa&nbsp;\"><y id=\"y\">&nbsp;aaa&nbsp;<x></x></y></z></html>",
                root.toString());
    }

    public void testWriteComment() throws Exception {
        // ## Arrange ##
        HtmlParser parser = getHtmlParser();

        // ## Act ##
        String str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ja\" lang=\"ja\">"
                + "<z id=\"&nbsp;aaa&nbsp;\"><y id=\"y\">&nbsp;aaa&nbsp;<!-- hogefoobar --><x /></y></z></html>";
        ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof DocumentNode);
        DocumentNode docType = (DocumentNode) root;
        assertEquals(1, docType.getChildSize());
        final ElementNode html = (ElementNode) docType.getChild(0);
        assertEquals(1, html.getChildSize());
        final HtmlNode z = html.getChild(0);
        assertTrue(z instanceof ElementNode);
        ElementNode zm = (ElementNode) z;
        HtmlNode y = zm.getChild(0);
        assertTrue(y instanceof ElementNode);
        ElementNode ym = (ElementNode) y;
        HtmlNode x = ym.getChild(0);
        assertTrue(x instanceof TextNode);
        System.out.println(root.toString());
        assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xml:lang=\"ja\" lang=\"ja\" xmlns=\"http://www.w3.org/1999/xhtml\">"
                        + "<z id=\"&nbsp;aaa&nbsp;\"><y id=\"y\">&nbsp;aaa&nbsp;<!-- hogefoobar --><x></x></y></z></html>",
                root.toString());
    }

}
