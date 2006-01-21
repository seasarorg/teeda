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

    private HtmlOutputLabelRenderer renderer_;

    private MockHtmlOutputLabel htmlOutputLabel_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlOutputLabelRenderer();
        htmlOutputLabel_ = new MockHtmlOutputLabel();
        htmlOutputLabel_.setRenderer(renderer_);
    }

    public void testEncodeBegin() throws Exception {
        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);

        assertEquals("<label>", getResponseText());
    }

    public void testEncodeBeginToEnd() throws Exception {
        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);
        renderer_.encodeEnd(getFacesContext(), htmlOutputLabel_);

        assertEquals("<label></label>", getResponseText());
    }

    public void testEncodeBeginToEnd_RenderFalse() throws Exception {
        htmlOutputLabel_.setRendered(false);
        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);
        renderer_.encodeEnd(getFacesContext(), htmlOutputLabel_);

        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_WithValue() throws Exception {
        htmlOutputLabel_.setValue("aaa");

        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);

        assertEquals("<label>aaa", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        htmlOutputLabel_.setId("someId");

        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);

        assertEquals("<label id=\"someId\">", getResponseText());
    }

    public void testEncodeBegin_WithFor() throws Exception {
        htmlOutputLabel_.setFor("bb");

        try {
            // if forComponent doesn't exist, we throw Exception.
            renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);
            fail();
        } catch (IllegalStateException ise) {
            AssertUtil.assertExceptionMessageExist(ise);
        }
    }

    public void testEncodeBegin_WithForComponent() throws Exception {
        htmlOutputLabel_.setFor("forComponentId");

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
        parent.getChildren().add(htmlOutputLabel_);
        parent.getChildren().add(forComponent);

        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);

        assertEquals("<label for=\"parentForm:forComponentId\">",
                getResponseText());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        // ## Arrange ##
        htmlOutputLabel_.setAccesskey("a");
        htmlOutputLabel_.setDir("b");
        htmlOutputLabel_.setFor("c");
        htmlOutputLabel_.setLang("d");
        htmlOutputLabel_.setOnblur("e");
        htmlOutputLabel_.setOnclick("f");
        htmlOutputLabel_.setOndblclick("g");
        htmlOutputLabel_.setOnfocus("h");
        htmlOutputLabel_.setOnkeydown("i");
        htmlOutputLabel_.setOnkeypress("j");
        htmlOutputLabel_.setOnkeyup("k");
        htmlOutputLabel_.setOnmousedown("l");
        htmlOutputLabel_.setOnmousemove("m");
        htmlOutputLabel_.setOnmouseout("n");
        htmlOutputLabel_.setOnmouseover("o");
        htmlOutputLabel_.setOnmouseup("p");
        htmlOutputLabel_.setStyle("q");
        htmlOutputLabel_.setStyleClass("r");
        htmlOutputLabel_.setTabindex("s");
        htmlOutputLabel_.setTitle("t");

        htmlOutputLabel_.setValue("u");
        htmlOutputLabel_.setId("v");

        UIComponent child = new MockUIComponentBase();
        child.setId("c");
        htmlOutputLabel_.getChildren().add(child);

        // ## Act ##
        renderer_.encodeBegin(getFacesContext(), htmlOutputLabel_);
        renderer_.encodeEnd(getFacesContext(), htmlOutputLabel_);

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

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
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
