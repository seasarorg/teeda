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

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.unit.AssertUtil;

/**
 * @author manhole
 */
public class HtmlOutputLabelRendererTest extends RendererTest {

    public void testEncodeBegin() throws Exception {
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label>", getResponseText());
    }

    public void testEncodeBeginToEnd() throws Exception {
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);
        renderer.encodeEnd(getFacesContext(), htmlOutputLabel);

        assertEquals("<label></label>", getResponseText());
    }

    public void testEncodeBegin_WithValue() throws Exception {
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        htmlOutputLabel.setValue("aaa");

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label>aaa", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        htmlOutputLabel.setId("someId");

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label id=\"someId\">", getResponseText());
    }

    public void testEncodeBegin_WithFor() throws Exception {
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        htmlOutputLabel.setFor("bb");

        try {
            // if forComponent doesn't exist, we throw Exception.
            renderer.encodeBegin(getFacesContext(), htmlOutputLabel);
            fail();
        } catch (IllegalStateException ise) {
            AssertUtil.assertExceptionMessageExist(ise);
        }
    }

    public void testEncodeBegin_WithForComponent() throws Exception {
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        htmlOutputLabel.setFor("forComponentId");

        UIInput forComponent = new UIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        forComponent.setId("forComponentId");

        UIForm parent = new UIForm() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        parent.setId("parentForm");
        parent.getChildren().add(htmlOutputLabel);
        parent.getChildren().add(forComponent);

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label for=\"parentForm:forComponentId\">",
                getResponseText());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        // ## Arrange ##
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        MockHtmlOutputLabel htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);

        htmlOutputLabel.setAccesskey("a");
        htmlOutputLabel.setDir("b");
        htmlOutputLabel.setFor("c");
        htmlOutputLabel.setLang("d");
        htmlOutputLabel.setOnblur("e");
        htmlOutputLabel.setOnclick("f");
        htmlOutputLabel.setOndblclick("g");
        htmlOutputLabel.setOnfocus("h");
        htmlOutputLabel.setOnkeydown("i");
        htmlOutputLabel.setOnkeypress("j");
        htmlOutputLabel.setOnkeyup("k");
        htmlOutputLabel.setOnmousedown("l");
        htmlOutputLabel.setOnmousemove("m");
        htmlOutputLabel.setOnmouseout("n");
        htmlOutputLabel.setOnmouseover("o");
        htmlOutputLabel.setOnmouseup("p");
        htmlOutputLabel.setStyle("q");
        htmlOutputLabel.setStyleClass("r");
        htmlOutputLabel.setTabindex("s");
        htmlOutputLabel.setTitle("t");

        htmlOutputLabel.setValue("u");
        htmlOutputLabel.setId("v");

        UIComponent child = new MockUIComponentBase();
        child.setId("c");
        htmlOutputLabel.getChildren().add(child);

        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);
        renderer.encodeEnd(getFacesContext(), htmlOutputLabel);

        // ## Assert ##
        Diff diff = new Diff("<label" + " id=\"v\"" + " accesskey=\"a\""
                + " dir=\"b\"" + " for=\"c\"" + " lang=\"d\"" + " onblur=\"e\""
                + " onclick=\"f\"" + " ondblclick=\"g\"" + " onfocus=\"h\""
                + " onkeydown=\"i\"" + " onkeypress=\"j\"" + " onkeyup=\"k\""
                + " onmousedown=\"l\"" + " onmousemove=\"m\""
                + " onmouseout=\"n\"" + " onmouseover=\"o\""
                + " onmouseup=\"p\"" + " style=\"q\"" + " class=\"r\""
                + " tabindex=\"s\"" + " title=\"t\">u</label>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    private HtmlOutputLabelRenderer createHtmlOutputLabelRenderer() {
        return (HtmlOutputLabelRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputLabelRenderer();
    }

    private static class MockHtmlOutputLabel extends HtmlOutputLabel {

        private Renderer renderer_ = null;

        public void setRenderer(Renderer renderer) {
            renderer_ = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer_ != null) {
                return renderer_;
            }
            return super.getRenderer(context);
        }
    }

}
