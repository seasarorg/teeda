/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.mock.MockUIInput;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class HtmlOutputLabelRendererTest extends RendererTest {

    private HtmlOutputLabelRenderer renderer;

    private MockHtmlOutputLabel htmlOutputLabel;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlOutputLabelRenderer();
        htmlOutputLabel = new MockHtmlOutputLabel();
        htmlOutputLabel.setRenderer(renderer);
    }

    public void testEncodeBegin() throws Exception {
        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label>", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        encodeByRenderer(renderer, htmlOutputLabel);

        assertEquals("<label></label>", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        htmlOutputLabel.setRendered(false);
        encodeByRenderer(renderer, htmlOutputLabel);

        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_WithValue() throws Exception {
        htmlOutputLabel.setValue("aaa");

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label>aaa", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        htmlOutputLabel.setId("someId");

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label id=\"someId\">", getResponseText());
    }

    public void testEncodeBegin_WithUnknownAttribute1() throws Exception {
        htmlOutputLabel.getAttributes().put("aaa", "bbb");

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label aaa=\"bbb\">", getResponseText());
    }

    public void testEncodeBegin_WithUnknownAttribute2() throws Exception {
        htmlOutputLabel.getAttributes().put("a.a", "bbb");

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label>", getResponseText());
    }

    public void testEncodeBegin_WithFor() throws Exception {
        htmlOutputLabel.setFor("bb");

        try {
            // if forComponent doesn't exist, we throw Exception.
            renderer.encodeBegin(getFacesContext(), htmlOutputLabel);
            fail();
        } catch (final IllegalStateException ise) {
            ExceptionAssert.assertMessageExist(ise);
        }
    }

    public void testEncodeBegin_WithForComponent() throws Exception {
        htmlOutputLabel.setFor("forComponentId");

        final UIInput forComponent = new MockUIInput();
        forComponent.setId("forComponentId");

        final UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("parentForm");
        parent.getChildren().add(htmlOutputLabel);
        parent.getChildren().add(forComponent);

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label for=\"forComponentId\">", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // ## Arrange ##
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

        final UIComponent child = new MockUIComponentBase();
        child.setId("c");
        htmlOutputLabel.getChildren().add(child);

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputLabel);

        // ## Assert ##
        final Diff diff = new Diff("<label" + " id=\"v\"" + " accesskey=\"a\""
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

    public void testGetConvertedValue() throws Exception {
        final Converter converter = new MockConverter() {
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return value + ".testGetConvertedValue";
            }
        };
        htmlOutputLabel.setConverter(converter);
        final Object convertedValue = renderer.getConvertedValue(
                getFacesContext(), htmlOutputLabel, "c");

        assertEquals("c.testGetConvertedValue", convertedValue);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlOutputLabelRenderer createHtmlOutputLabelRenderer() {
        return (HtmlOutputLabelRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        final HtmlOutputLabelRenderer renderer = new HtmlOutputLabelRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlOutputLabel extends HtmlOutputLabel {

        private Renderer renderer_ = null;

        public void setRenderer(final Renderer renderer) {
            renderer_ = renderer;
        }

        protected Renderer getRenderer(final FacesContext context) {
            if (renderer_ != null) {
                return renderer_;
            }
            return super.getRenderer(context);
        }
    }

}
