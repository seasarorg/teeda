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
    {
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
    }

    protected MockFacesContext getFacesContext() {
        return context_;
    }

    public void testEncodeBegin() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("/abc.html");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"/abc.html\"", getResponseText());
    }

    public void testEncodeBeginAndEnd() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("a");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);
        renderer.encodeEnd(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"a\"></a>", getResponseText());
    }

    public void testEncodeBegin_WithAccesskey() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url");
        htmlOutputLink.setAccesskey("aa");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"url\" accesskey=\"aa\"", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setId("someId");
        htmlOutputLink.setValue("url");
        htmlOutputLink.setAccesskey("aa");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a id=\"someId\" href=\"url\" accesskey=\"aa\"",
                getResponseText());
    }

    public void testEncodeBegin_HrefIsJapanese() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        // japanese "a"
        htmlOutputLink.setValue("/" + new Character((char) 12354) + ".html");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();
        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"/%E3%81%82.html\"", getResponseText());
    }

    public void testEncodeBegin_WithParam1() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink.getChildren().add(param);

        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();
        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"url.html?a=b\"", getResponseText());
    }

    public void testEncodeBegin_WithParam2() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("/a/b/url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b/c");
        htmlOutputLink.getChildren().add(param);

        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();
        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"/a/b/url.html?a=b%2Fc\"", getResponseText());
    }

    public void testEncodeBegin_BaseHrefHasQueryString() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url.html?1=2");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink.getChildren().add(param);

        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"url.html?1=2&a=b\"", getResponseText());
    }

    public void testEncodeBegin_WithJapaneseParamValue() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url");
        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue(new Character((char) 12354)); // japanese "a"
        htmlOutputLink.getChildren().add(param);
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"url?a=%E3%81%82\"", getResponseText());
    }

    public void testEncodeBegin_WithParams() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
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
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals("<a href=\"url?a=1&b=2\"", getResponseText());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
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
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);
        renderer.encodeEnd(getFacesContext(), htmlOutputLink);

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

    private HtmlOutputLinkRenderer createHtmlOutputLinkRenderer() {
        return (HtmlOutputLinkRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputLinkRenderer();
    }

}
