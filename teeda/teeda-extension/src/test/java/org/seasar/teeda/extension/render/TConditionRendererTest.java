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
package org.seasar.teeda.extension.render;

import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockHtmlOutputText;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.TCondition;

/**
 * @author shot
 */
public class TConditionRendererTest extends RendererTest {

    public void testDecode_noExpectedChildren() throws Exception {
        Renderer r = createRenderer();
        TCondition c = new TCondition();
        r.decode(getFacesContext(), c);
        assertFalse(c.isSubmitted().booleanValue());
    }

    public void testDecode_existsExpectedChildren() throws Exception {
        Renderer r = createRenderer();
        TCondition c = new TCondition();
        c.setId("hoge");
        HtmlInputHidden hidden = new HtmlInputHidden() {

            public void decode(FacesContext context) {
            }

        };
        hidden.setId("hoge" + ExtensionConstants.TEEDA_HIDDEN_SUFFIX);
        hidden.setSubmittedValue("true");
        c.getChildren().add(hidden);
        r.decode(getFacesContext(), c);
        assertTrue(c.isSubmitted().booleanValue());
    }

    public void testEncode1() throws Exception {
        Renderer r = createRenderer();
        TCondition c = new TCondition();
        c.setId("isAaa");
        c.setRendered(true);
        encodeByRenderer(r, c);
        assertEquals("<div id=\"isAaa\"></div>", getResponseText());
    }

    public void testEncode2() throws Exception {
        Renderer r = createRenderer();
        TCondition c = new TCondition();
        MockHtmlOutputText child = new MockHtmlOutputText();
        child.setId("bbb");
        child.setValue("B");
        HtmlOutputTextRenderer childRenderer = new HtmlOutputTextRenderer();
        child.setRenderer(childRenderer);
        c.setId("isAaa");
        c.setRendered(true);
        c.getChildren().add(child);
        encodeByRenderer(r, c);
        assertEquals("<div id=\"isAaa\"><span id=\"bbb\">B</span></div>",
                getResponseText());
    }

    protected Renderer createRenderer() {
        return new TConditionRenderer();
    }
}
