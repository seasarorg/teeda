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

import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlOutputLinkRendererTest extends RendererTest {

    private HtmlOutputLinkRenderer renderer_;

    private MockHtmlOutputLink htmlOutputLink_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlOutputLinkRenderer();
        htmlOutputLink_ = new MockHtmlOutputLink();
        htmlOutputLink_.setRenderer(renderer_);
    }

    public void testEncode() throws Exception {
        // ## Arrange ##
        htmlOutputLink_.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        // ## Assert ##
        assertEquals("<a href=\"a\"></a>", getResponseText());
    }

    public void testEncode_CallsEncodeResourceUrl() throws Exception {
        // ## Arrange ##
        htmlOutputLink_.setValue("/abc.html");

        final String[] param = { null };
        MockFacesContext context = getFacesContext();
        context.setExternalContext(new MockExternalContextImpl() {
            public String encodeResourceURL(String url) {
                param[0] = url;
                return url;
            }
        });

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlOutputLink_);

        // ## Assert ##
        assertEquals("/abc.html", param[0]);
        assertEquals("<a href=\"/abc.html\"></a>", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlOutputLink_.setRendered(false);
        htmlOutputLink_.setValue("abc");

        // ## Act ##
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlOutputLink_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithChild() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText child = new MockHtmlOutputText();
        child.setRenderer(htmlOutputTextRenderer);
        child.setValue("Y");
        htmlOutputLink_.getChildren().add(child);

        htmlOutputLink_.setValue("a");

        // ## Act ##
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlOutputLink_);

        // ## Assert ##
        assertEquals("<a href=\"a\">Y</a>", getResponseText());
    }

    public void testEncode_WithAccesskey() throws Exception {
        htmlOutputLink_.setValue("url");
        htmlOutputLink_.setAccesskey("aa");

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url\" accesskey=\"aa\"></a>", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlOutputLink_.setId("someId");
        htmlOutputLink_.setValue("url");
        htmlOutputLink_.setAccesskey("aa");

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a id=\"someId\" href=\"url\" accesskey=\"aa\"></a>",
                getResponseText());
    }

    public void testEncode_HrefIsJapanese() throws Exception {
        // japanese "a"
        htmlOutputLink_.setValue("/" + new Character((char) 12354) + ".html");

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"/%E3%81%82.html\"></a>", getResponseText());
    }

    public void testEncode_WithParam1() throws Exception {
        htmlOutputLink_.setValue("url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink_.getChildren().add(param);

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url.html?a=b\"></a>", getResponseText());
    }

    public void testEncode_WithParam2() throws Exception {
        htmlOutputLink_.setValue("/a/b/url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b/c");
        htmlOutputLink_.getChildren().add(param);

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"/a/b/url.html?a=b%2Fc\"></a>",
                getResponseText());
    }

    public void testEncodeBegin_BaseHrefHasQueryString() throws Exception {
        htmlOutputLink_.setValue("url.html?1=2");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink_.getChildren().add(param);

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url.html?1=2&amp;a=b\"></a>", getResponseText());
    }

    public void testEncode_WithJapaneseParamValue() throws Exception {
        htmlOutputLink_.setValue("url");
        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue(new Character((char) 12354)); // japanese "a"
        htmlOutputLink_.getChildren().add(param);

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url?a=%E3%81%82\"></a>", getResponseText());
    }

    public void testEncode_WithParams() throws Exception {
        htmlOutputLink_.setValue("url");
        {
            UIParameter param = new UIParameter();
            param.setName("a");
            param.setValue("1");
            htmlOutputLink_.getChildren().add(param);
        }
        {
            UIParameter param = new UIParameter();
            param.setName("b");
            param.setValue("2");
            htmlOutputLink_.getChildren().add(param);
        }

        encodeByRenderer(renderer_, getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url?a=1&amp;b=2\"></a>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlOutputLink_.setId("a");
        htmlOutputLink_.setValue("b");
        htmlOutputLink_.setAccesskey("c");
        htmlOutputLink_.setCharset("d");
        htmlOutputLink_.setCoords("e");
        htmlOutputLink_.setDir("f");
        htmlOutputLink_.setHreflang("g");
        htmlOutputLink_.setLang("h");
        htmlOutputLink_.setOnblur("i");
        htmlOutputLink_.setOnclick("j");
        htmlOutputLink_.setOndblclick("k");
        htmlOutputLink_.setOnfocus("l");
        htmlOutputLink_.setOnkeydown("m");
        htmlOutputLink_.setOnkeypress("n");
        htmlOutputLink_.setOnkeyup("o");
        htmlOutputLink_.setOnmousedown("p");
        htmlOutputLink_.setOnmousemove("q");
        htmlOutputLink_.setOnmouseout("r");
        htmlOutputLink_.setOnmouseover("s");
        htmlOutputLink_.setOnmouseup("t");
        htmlOutputLink_.setRel("u");
        htmlOutputLink_.setRev("v");
        htmlOutputLink_.setShape("w");
        htmlOutputLink_.setStyle("u");
        htmlOutputLink_.setStyleClass("x");
        htmlOutputLink_.setTabindex("y");
        htmlOutputLink_.setTarget("z");
        htmlOutputLink_.setTitle("A");
        htmlOutputLink_.setType("B");

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlOutputLink_);

        Diff diff = new Diff("<a" + " id=\"a\"" + " href=\"b\""
                + " accesskey=\"c\"" + " charset=\"d\"" + " coords=\"e\""
                + " dir=\"f\"" + " hreflang=\"g\"" + " lang=\"h\""
                + " onblur=\"i\"" + " onclick=\"j\"" + " ondblclick=\"k\""
                + " onfocus=\"l\"" + " onkeydown=\"m\"" + " onkeypress=\"n\""
                + " onkeyup=\"o\"" + " onmousedown=\"p\""
                + " onmousemove=\"q\"" + " onmouseout=\"r\""
                + " onmouseover=\"s\"" + " onmouseup=\"t\"" + " rel=\"u\""
                + " rev=\"v\"" + " shape=\"w\"" + " style=\"u\""
                + " class=\"x\"" + " tabindex=\"y\"" + " target=\"z\""
                + " title=\"A\"" + " type=\"B\"></a>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer_.getRendersChildren());
    }

    private HtmlOutputLinkRenderer createHtmlOutputLinkRenderer() {
        return (HtmlOutputLinkRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlOutputLinkRenderer renderer = new HtmlOutputLinkRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlOutputLink extends HtmlOutputLink {

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
