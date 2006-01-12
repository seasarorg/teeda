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
package org.seasar.teeda.core.render.html;

import javax.faces.component.html.HtmlOutputText;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public class HtmlOutputTextRendererTest extends TeedaTestCase {

    public void testEncodeEnd() throws Exception {
        HtmlOutputTextRenderer renderer = new HtmlOutputTextRenderer();
        MockFacesContext context = getFacesContext();
        // TODO
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        renderer.encodeEnd(context, htmlOutputText);
    }

    public void testEncodeEnd_null1() throws Exception {
        HtmlOutputTextRenderer renderer = new HtmlOutputTextRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testEncodeEnd_null2() throws Exception {
        HtmlOutputTextRenderer renderer = new HtmlOutputTextRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

}
