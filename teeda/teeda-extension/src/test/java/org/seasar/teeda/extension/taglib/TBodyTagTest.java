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
package org.seasar.teeda.extension.taglib;

import org.seasar.teeda.extension.component.UIBody;

import junit.framework.TestCase;

/**
 * @author shot
 * 
 */
public class TBodyTagTest extends TestCase {

    public void test() throws Exception {
        TBodyTag tag = new TBodyTag();
        tag.setOnkeyup("a");
        tag.setOnkeydown("b");
        tag.setOnkeypress("c");
        tag.setOnmouseout("d");
        tag.setOnmousemove("e");
        tag.setOnmouseover("f");
        tag.setOnmouseup("g");
        tag.setOndblclick("h");
        tag.setOnclick("i");
        tag.setOnunload("j");
        tag.setOnload("k");
        tag.setBgcolor("l");
        tag.setStyle("m");
        tag.setTitle("n");
        tag.setDir("o");
        tag.setLang("p");
        tag.setStyleClass("q");
        final UIBody body = new UIBody();
        tag.setProperties(body);
        assertEquals("a", body.getOnkeyup());
        tag.release();
        assertNull(tag.getOnkeyup());
    }
}
