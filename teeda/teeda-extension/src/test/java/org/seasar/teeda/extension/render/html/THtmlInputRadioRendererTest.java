/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlSelectOneRadio;

/**
 * @author manhole
 */
public class THtmlInputRadioRendererTest extends RendererTest {

    private THtmlInputRadioRenderer renderer;

    private MockHtmlSelectOneRadio component;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlInputRadioRenderer) createRenderer();
        component = new MockHtmlSelectOneRadio();
        component.setRenderer(renderer);
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##
        // ## Act ##
        encodeByRenderer(renderer, component);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        component.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, component);

        // ## Assert ##
        assertEquals(null, component.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        component.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer.decode(context, component);

        // ## Assert ##
        assertEquals("12345", component.getSubmittedValue());
    }

    protected Renderer createRenderer() {
        THtmlInputRadioRenderer renderer = new THtmlInputRadioRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
