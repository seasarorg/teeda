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

import org.seasar.teeda.extension.component.html.THtmlStyle;

/**
 * @author shot
 */
public class THtmlStyleRendererTest extends RendererTest {

    private THtmlStyleRenderer renderer;

    private THtmlStyle style;

    protected Renderer createRenderer() {
        THtmlStyleRenderer renderer = new THtmlStyleRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlStyleRenderer) createRenderer();
        style = new THtmlStyle();
    }

    public void testEncode_simple() throws Exception {
        encodeByRenderer(renderer, style);
        assertEquals("<style></style>", getResponseText());
    }

    public void testEncode_dir() throws Exception {
        style.setDir("aaa");
        encodeByRenderer(renderer, style);
        assertEquals("<style dir=\"aaa\"></style>", getResponseText());
    }

    public void testEncode_lang() throws Exception {
        style.setLang("ja");
        encodeByRenderer(renderer, style);
        assertEquals("<style lang=\"ja\"></style>", getResponseText());
    }

    public void testEncode_media() throws Exception {
        style.setMedia("media");
        encodeByRenderer(renderer, style);
        assertEquals("<style media=\"media\"></style>", getResponseText());
    }

    public void testEncode_title() throws Exception {
        style.setTitle("T");
        encodeByRenderer(renderer, style);
        assertEquals("<style title=\"T\"></style>", getResponseText());
    }

}
