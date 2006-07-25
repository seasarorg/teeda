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

import junit.framework.TestCase;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.TextNode;

/**
 * @author manhole
 */
public class HtmlParserImplTest extends TestCase {

    public void testBasic() throws Exception {
        // ## Arrange ##
        HtmlParserImpl parser = new HtmlParserImpl();

        // ## Act ##
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<z><y id=\"y\"><x /></y></z>".getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof ElementNode);
        ElementNode z = (ElementNode) root;
        assertEquals(1, z.getChildSize());
        final ElementNode y = (ElementNode) z.getChild(0);

        assertEquals(1, y.getChildSize());
        final HtmlNode x = y.getChild(0);
        assertEquals(true, x instanceof TextNode);

        assertEquals("<z><y id=\"y\"><x></x></y></z>", root.toString());
    }

    public void testForceElementNode1() throws Exception {
        // ## Arrange ##
        HtmlParserImpl parser = new HtmlParserImpl();
        parser.addElementNodeTagName("y");

        // ## Act ##
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<z><y id=\"y\"><x /></y></z>".getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof ElementNode);
        ElementNode z = (ElementNode) root;
        assertEquals(1, z.getChildSize());
        final ElementNode y = (ElementNode) z.getChild(0);

        assertEquals(1, y.getChildSize());
        final HtmlNode x = y.getChild(0);
        assertEquals(true, x instanceof ElementNode);
    }

    public void testForceElementNode2() throws Exception {
        // ## Arrange ##
        HtmlParserImpl parser = new HtmlParserImpl();
        parser.addElementNodeTagName("y");

        // ## Act ##
        ByteArrayInputStream is = new ByteArrayInputStream(
                "<z><y id=\"y\"><x /></y><y><x /></y></z>".getBytes());
        final HtmlNode root = parser.parse(is);

        // ## Assert ##
        assertEquals(true, root instanceof ElementNode);
        ElementNode z = (ElementNode) root;
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
}
