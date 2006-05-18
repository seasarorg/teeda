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
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;
import org.seasar.teeda.extension.html.impl.HtmlParserImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.TextNodeImpl;

/**
 * @author higa
 *
 */
public class HtmlNodeHandlerTest extends S2FrameworkTestCase {
	
    public void testParse() throws Exception {
        String path = convertPath("HtmlNodeHandler.xhtml");
        HtmlParser parser = new HtmlParserImpl();
        HtmlNode node = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals("1", "<html><body>Hello</body></html>", node.toString());
        ElementNodeImpl n = (ElementNodeImpl) node;
        assertEquals("2", 1, n.getChildSize());
        TextNodeImpl t = (TextNodeImpl) n.getChild(0);
        assertEquals("3", "<body>Hello</body>", t.getValue());
    }
    
    public void testParseUsingId() throws Exception {
        String path = convertPath("HtmlNodeHandler2.xhtml");
        HtmlParser parser = new HtmlParserImpl();
        HtmlNode node = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals("1", "<html><body>Hello<span id=\"aaa\" />World</body></html>", node.toString());
        ElementNodeImpl n = (ElementNodeImpl) node;
        assertEquals("2", 3, n.getChildSize());
        TextNodeImpl t = (TextNodeImpl) n.getChild(0);
        assertEquals("3", "<body>Hello", t.getValue());
        ElementNodeImpl n2 = (ElementNodeImpl) n.getChild(1);
        assertEquals("4", "<span id=\"aaa\" />", n2.toString());
        TextNodeImpl t2 = (TextNodeImpl) n.getChild(2);
        assertEquals("5", "World</body>", t2.getValue());
    }
    
    public void testParseUsingIdNest() throws Exception {
        String path = convertPath("HtmlNodeHandler3.xhtml");
        HtmlParser parser = new HtmlParserImpl();
        HtmlNode node = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals("1", "<html><div id=\"aaa\">Hello<span id=\"bbb\" />World</div></html>", node.toString());
        ElementNodeImpl n = (ElementNodeImpl) node;
        assertEquals("2", 1, n.getChildSize());
        ElementNodeImpl n2 = (ElementNodeImpl) n.getChild(0);
        assertEquals("3", 3, n2.getChildSize());
        TextNodeImpl t = (TextNodeImpl) n2.getChild(0);
        assertEquals("4", "Hello", t.getValue());
        ElementNodeImpl n3 = (ElementNodeImpl) n2.getChild(1);
        assertEquals("5", "<span id=\"bbb\" />", n3.toString());
        TextNodeImpl t2 = (TextNodeImpl) n2.getChild(2);
        assertEquals("6", "World", t2.getValue());
    }
}