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

import javax.faces.component.UIColumn;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTeedaTest;

import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlDataTableRendererTeedaTest extends RendererTeedaTest {

    private HtmlDataTableRenderer renderer_;

    private MockHtmlDataTable htmlDataTable_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlDataTableRenderer();
        htmlDataTable_ = new MockHtmlDataTable();
        htmlDataTable_.setRenderer(renderer_);
    }

    // TODO
    public void testEncodeChildren1() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setValue(new String[] { "a", "b", "c" });
        htmlDataTable_.setVar("fooVar");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(htmlOutputTextRenderer);
            ValueBinding vb = new ValueBindingImpl(getApplication(),
                    "#{fooVar}", new CommonsELParser());
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>Z</td><td>a</td></tr>"
                + "<tr><td>Z</td><td>b</td></tr>"
                + "<tr><td>Z</td><td>c</td></tr>" + "</tbody>",
                getResponseText());
    }

    // TODO test

    private HtmlDataTableRenderer createHtmlDataTableRenderer() {
        return (HtmlDataTableRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlDataTableRenderer();
    }

}
