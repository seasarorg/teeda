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

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.html.impl.ElementNodeImpl;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class ElementNodeImplTest extends TestCase {
	
	public void testToString() throws Exception {
		ElementNodeImpl n1 = new ElementNodeImpl("hoge", new HashMap());
        assertEquals("1", "<hoge />", n1.toString());
        
        Map props2 = new HashMap();
        props2.put("id", "aaa");
        ElementNodeImpl n2 = new ElementNodeImpl("hoge", props2);
        assertEquals("2", "<hoge id=\"aaa\" />", n2.toString());
        
        Map props3 = new HashMap();
        props3.put("id", "aaa");
        props3.put("type", "text");
        ElementNodeImpl n3 = new ElementNodeImpl("input", props3);
        System.out.println(n3);
        
        Map props4 = new HashMap();
        props4.put("id", "aaa");
        ElementNodeImpl n4 = new ElementNodeImpl("hoge", props4);
        n4.addText("abc");
        n4.endElement();
        assertEquals("4", "<hoge id=\"aaa\">abc</hoge>", n4.toString());
        
        Map props5 = new HashMap();
        props5.put("id", "aaa");
        ElementNodeImpl n5 = new ElementNodeImpl("hoge", props5);
        n5.addText("abc");
        n5.addText("def");
        n5.endElement();
        assertEquals("5", "<hoge id=\"aaa\">abcdef</hoge>", n5.toString());
        
        Map props6 = new HashMap();
        props6.put("id", "aaa");
        ElementNodeImpl n6 = new ElementNodeImpl("hoge", props6);
        n6.addText("abc");
        Map props62 = new HashMap();
        props62.put("id", "bbb");
        ElementNodeImpl n62 = new ElementNodeImpl("hoge2", props62);
        n6.addElement(n62);
        n6.addText("def");
        n6.endElement();
        assertEquals("6", "<hoge id=\"aaa\">abc<hoge2 id=\"bbb\" />def</hoge>", n6.toString());
	}
}