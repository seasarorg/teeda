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
package org.seasar.teeda.extension.render.html;

import javax.faces.render.AbstractRendererTest;

import org.seasar.teeda.extension.component.html.THtmlHead;
import org.seasar.teeda.extension.util.VirtualResource;

/**
 * @author higa
 */
public class THtmlHeadRendererTest extends AbstractRendererTest {

    private THtmlHeadRenderer renderer;

    private THtmlHead component;

    public void setUp() throws Exception {
        super.setUp();
        renderer = new THtmlHeadRenderer();
        component = new THtmlHead();
    }

    public void testEncode() throws Exception {
        VirtualResource.addJSResource(getFacesContext(), "hoge.js");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\r\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/mock-context/teedaExtension/hoge.js\"></script>\r\n</head>\r\n",
                getResponseText());
    }
}