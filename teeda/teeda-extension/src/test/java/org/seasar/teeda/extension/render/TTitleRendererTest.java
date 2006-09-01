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
package org.seasar.teeda.extension.render;

import javax.faces.render.RendererTest;

import org.seasar.teeda.extension.component.UITitle;

/**
 * @author shot
 */
public class TTitleRendererTest extends RendererTest {

    public void test_renderTitle() throws Exception {
        TTitleRenderer renderer = new TTitleRenderer();
        UITitle title = new UITitle();
        title.setId("aaa");
        title.setValue("hoge");
        title.setDir("left");
        title.setLang("ja");
        encodeByRenderer(renderer, title);
        assertEquals("<title id=\"aaa\" dir=\"left\" lang=\"ja\">hoge</title>",
                getResponseText());
    }

}