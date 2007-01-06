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
package org.seasar.teeda.extension.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKit;

import org.seasar.teeda.extension.component.UIText;
import org.seasar.teeda.extension.html.processor.ViewProcessor;
import org.seasar.teeda.extension.render.html.HtmlTextRenderer;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 *
 */
public class TextTagTest extends TeedaExtensionTestCase {

    protected void setUp() throws Exception {
        RenderKit renderKit = getRenderKit();
        renderKit.addRenderer(UIText.COMPONENT_FAMILY,
                UIText.DEFAULT_RENDERER_TYPE, new HtmlTextRenderer());
        Application app = getApplication();
        app.addComponent(UIViewRoot.COMPONENT_TYPE, UIViewRoot.class.getName());
        app.addComponent(UIText.COMPONENT_TYPE, UIText.class.getName());
    }

    public void testSetProperties() throws Exception {
        ViewProcessor root = new ViewProcessor();
        String value = "hoge";
        root.addText(value);
        root.endElement();
        root.process(getPageContext(), null);
        assertEquals("1", value, getResponseText());
    }
}