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
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextWrapper;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlOutputLinkRendererTest extends RendererTest {

    private final boolean[] calls_ = { false };

    private MockFacesContext context_;

    private HtmlOutputLinkRenderer renderer_;

    private MockHtmlOutputLink htmlOutputLink_;

    protected void setUp() throws Exception {
        super.setUp();
        context_ = super.getFacesContext();
        MockExternalContext externalContext = (MockExternalContext) context_
                .getExternalContext();
        context_.setExternalContext(new MockExternalContextWrapper(
                externalContext) {
            public String encodeResourceURL(String url) {
                calls_[0] = true;
                return url;
            }
        });
        renderer_ = createHtmlOutputLinkRenderer();
        htmlOutputLink_ = new MockHtmlOutputLink();
        htmlOutputLink_.setRenderer(renderer_);
    }

    protected MockFacesContext getFacesContext() {
        return context_;
    }

    public void testEncodeBegin() throws Exception {
        htmlOutputLink_.setValue("/abc.html");

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"/abc.html\"", getResponseText());
    }

    public void testEncodeBeginToEnd() throws Exception {
        htmlOutputLink_.setValue("a");

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);
        renderer_.encodeEnd(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"a\"></a>", getResponseText());
    }

    public void testEncodeBeginToEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlOutputLink_.setRendered(false);
        htmlOutputLink_.setValue("abc");

        // ## Act ##
        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);
        renderer_.encodeEnd(getFacesContext(), htmlOutputLink_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_WithAccesskey() throws Exception {
        htmlOutputLink_.setValue("url");
        htmlOutputLink_.setAccesskey("aa");

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url\" accesskey=\"aa\"", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        htmlOutputLink_.setId("someId");
        htmlOutputLink_.setValue("url");
        htmlOutputLink_.setAccesskey("aa");

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a id=\"someId\" href=\"url\" accesskey=\"aa\"",
                getResponseText());
    }

    public void testEncodeBegin_HrefIsJapanese() throws Exception {
        // japanese "a"
        htmlOutputLink_.setValue("/" + new Character((char) 12354) + ".html");
        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"/%E3%81%82.html\"", getResponseText());
    }

    public void testEncodeBegin_WithParam1() throws Exception {
        htmlOutputLink_.setValue("url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink_.getChildren().add(param);

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url.html?a=b\"", getResponseText());
    }

    public void testEncodeBegin_WithParam2() throws Exception {
        htmlOutputLink_.setValue("/a/b/url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b/c");
        htmlOutputLink_.getChildren().add(param);

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"/a/b/url.html?a=b%2Fc\"", getResponseText());
    }

    public void testEncodeBegin_BaseHrefHasQueryString() throws Exception {
        htmlOutputLink_.setValue("url.html?1=2");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink_.getChildren().add(param);

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url.html?1=2&a=b\"", getResponseText());
    }

    public void testEncodeBegin_WithJapaneseParamValue() throws Exception {
        htmlOutputLink_.setValue("url");
        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue(new Character((char) 12354)); // japanese "a"
        htmlOutputLink_.getChildren().add(param);

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url?a=%E3%81%82\"", getResponseText());
    }

    public void testEncodeBegin_WithParams() throws Exception {
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

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);

        assertEquals("<a href=\"url?a=1&b=2\"", getResponseText());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
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

        renderer_.encodeBegin(getFacesContext(), htmlOutputLink_);
        renderer_.encodeEnd(getFacesContext(), htmlOutputLink_);

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
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlOutputLinkRenderer createHtmlOutputLinkRenderer() {
        return (HtmlOutputLinkRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputLinkRenderer();
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
