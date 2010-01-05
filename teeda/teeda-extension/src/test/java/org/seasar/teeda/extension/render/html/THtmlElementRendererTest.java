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
package org.seasar.teeda.extension.render.html;

import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.extension.component.html.THtmlElement;

/**
 * @author shot
 */
public class THtmlElementRendererTest extends RendererTest {

    public void testRender_simple() throws Exception {
        THtmlElement element = new THtmlElement();
        element.setTagName("aaa");
        encodeByRenderer(createTHtmlElementRenderer(), element);
        assertEquals("<aaa></aaa>", getResponseText());
    }

    public void testRender_simpleWithId() throws Exception {
        THtmlElement element = new THtmlElement();
        element.setTagName("aaa");
        element.setId("bbb");
        element.setIdSet(true);
        encodeByRenderer(createTHtmlElementRenderer(), element);
        assertEquals("<aaa id=\"bbb\"></aaa>", getResponseText());
    }

    public void testRender_simpleWithAttribute() throws Exception {
        THtmlElement element = new THtmlElement();
        element.setTagName("aaa");
        element.setId("bbb");
        element.setIdSet(true);
        element.getAttributes().put("ccc", "CCC");
        encodeByRenderer(createTHtmlElementRenderer(), element);
        assertEquals("<aaa id=\"bbb\" ccc=\"CCC\"></aaa>", getResponseText());
    }

    protected THtmlElementRenderer createTHtmlElementRenderer() {
        return (THtmlElementRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new THtmlElementRenderer();
    }

}
