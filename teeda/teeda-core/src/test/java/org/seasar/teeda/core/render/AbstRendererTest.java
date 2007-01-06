/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render;

import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author higa
 */
public class AbstRendererTest extends TeedaTestCase {

    private AbstractRenderer renderer = new AbstractRenderer() {
    };

    public void testRenderIncludeJavaScript() throws Exception {
        ResponseWriter writer = getFacesContext().getResponseWriter();
        renderer.renderIncludeJavaScript(writer, "hoge.js");
        String s = getResponseText();
        System.out.println(s);
        assertEquals(
                "<script language=\"JavaScript\" type=\"text/javascript\" src=\"hoge.js\"></script>",
                s);
    }

    public void testRenderStyleSheet() throws Exception {
        ResponseWriter writer = getFacesContext().getResponseWriter();
        renderer.renderStyleSheet(writer, "hoge.css");
        String s = getResponseText();
        System.out.println(s);
        assertEquals(
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"hoge.css\" />",
                s);
    }
}