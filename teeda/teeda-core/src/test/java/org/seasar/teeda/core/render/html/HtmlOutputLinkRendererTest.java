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

    private HtmlOutputLinkRenderer renderer;

    private MockHtmlOutputLink htmlOutputLink;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlOutputLinkRenderer();
        htmlOutputLink = new MockHtmlOutputLink();
        htmlOutputLink.setRenderer(renderer);
    }

    public void testEncode() throws Exception {
        // ## Arrange ##
        htmlOutputLink.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputLink);

        // ## Assert ##
        assertEquals("<a href=\"a\"></a>", getResponseText());
    }

    public void testEncode_CallsEncodeResourceUrl() throws Exception {
        // ## Arrange ##
        htmlOutputLink.setValue("/abc.html");

        final String[] param = { null };
        MockFacesContext context = getFacesContext();
        context.setExternalContext(new MockExternalContextImpl() {
            public String encodeResourceURL(String url) {
                param[0] = url;
                return url;
            }
        });

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputLink);

        // ## Assert ##
        assertEquals("/abc.html", param[0]);
        assertEquals("<a href=\"/abc.html\"></a>", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlOutputLink.setRendered(false);
        htmlOutputLink.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputLink);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithChild() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText child = new MockHtmlOutputText();
        child.setRenderer(htmlOutputTextRenderer);
        child.setValue("Y");
        htmlOutputLink.getChildren().add(child);

        htmlOutputLink.setValue("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlOutputLink);

        // ## Assert ##
        assertEquals("<a href=\"a\">Y</a>", getResponseText());
    }

    public void testEncode_WithAccesskey() throws Exception {
        htmlOutputLink.setValue("url");
        htmlOutputLink.setAccesskey("aa");

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url\" accesskey=\"aa\"></a>", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlOutputLink.setId("someId");
        htmlOutputLink.setValue("url");

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a id=\"someId\" href=\"url\"></a>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        htmlOutputLink.setValue("url");
        htmlOutputLink.getAttributes().put("aa", "bb");

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url\" aa=\"bb\"></a>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        htmlOutputLink.setValue("url");
        htmlOutputLink.getAttributes().put("a.a", "bb");

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url\"></a>", getResponseText());
    }

    public void testEncode_HrefIsJapanese() throws Exception {
        // japanese "a"
        htmlOutputLink.setValue("/" + new Character((char) 12354) + ".html");

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"/%E3%81%82.html\"></a>", getResponseText());
    }

    public void testEncode_WithParam1() throws Exception {
        htmlOutputLink.setValue("url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink.getChildren().add(param);

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url.html?a=b\"></a>", getResponseText());
    }

    public void testEncode_WithParam2() throws Exception {
        htmlOutputLink.setValue("/a/b/url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b/c");
        htmlOutputLink.getChildren().add(param);

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"/a/b/url.html?a=b%2Fc\"></a>",
                getResponseText());
    }

    public void testEncodeBegin_BaseHrefHasQueryString() throws Exception {
        htmlOutputLink.setValue("url.html?1=2");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink.getChildren().add(param);

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url.html?1=2&amp;a=b\"></a>", getResponseText());
    }

    public void testEncode_WithJapaneseParamValue() throws Exception {
        htmlOutputLink.setValue("url");
        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue(new Character((char) 12354)); // japanese "a"
        htmlOutputLink.getChildren().add(param);

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url?a=%E3%81%82\"></a>", getResponseText());
    }

    public void testEncode_WithParams() throws Exception {
        htmlOutputLink.setValue("url");
        {
            UIParameter param = new UIParameter();
            param.setName("a");
            param.setValue("1");
            htmlOutputLink.getChildren().add(param);
        }
        {
            UIParameter param = new UIParameter();
            param.setName("b");
            param.setValue("2");
            htmlOutputLink.getChildren().add(param);
        }

        encodeByRenderer(renderer, htmlOutputLink);

        assertEquals("<a href=\"url?a=1&amp;b=2\"></a>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlOutputLink.setId("a");
        htmlOutputLink.setValue("b");
        htmlOutputLink.setAccesskey("c");
        htmlOutputLink.setCharset("d");
        htmlOutputLink.setCoords("e");
        htmlOutputLink.setDir("f");
        htmlOutputLink.setHreflang("g");
        htmlOutputLink.setLang("h");
        htmlOutputLink.setOnblur("i");
        htmlOutputLink.setOnclick("j");
        htmlOutputLink.setOndblclick("k");
        htmlOutputLink.setOnfocus("l");
        htmlOutputLink.setOnkeydown("m");
        htmlOutputLink.setOnkeypress("n");
        htmlOutputLink.setOnkeyup("o");
        htmlOutputLink.setOnmousedown("p");
        htmlOutputLink.setOnmousemove("q");
        htmlOutputLink.setOnmouseout("r");
        htmlOutputLink.setOnmouseover("s");
        htmlOutputLink.setOnmouseup("t");
        htmlOutputLink.setRel("u");
        htmlOutputLink.setRev("v");
        htmlOutputLink.setShape("w");
        htmlOutputLink.setStyle("u");
        htmlOutputLink.setStyleClass("x");
        htmlOutputLink.setTabindex("y");
        htmlOutputLink.setTarget("z");
        htmlOutputLink.setTitle("A");
        htmlOutputLink.setType("B");

        encodeByRenderer(renderer, htmlOutputLink);

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
        assertEquals(true, renderer.getRendersChildren());
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
