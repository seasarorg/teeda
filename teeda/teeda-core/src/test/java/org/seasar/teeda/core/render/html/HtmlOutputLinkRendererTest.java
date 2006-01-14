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

import java.net.URLEncoder;

import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockExternalContextWrapper;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlOutputLinkRendererTest extends RendererTest {

    private final boolean[] calls_ = { false };

    protected void setUpAfterContainerInit() throws Throwable {
        super.setUpAfterContainerInit();
        MockFacesContext context = getFacesContext();
        context.setExternalContext(new MockExternalContextWrapper(
                getExternalContext()) {
            public String encodeResourceURL(String url) {
                calls_[0] = true;
                return url;
            }
        });
    }

    public void testEncodeBegin() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("/abc.html");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"%2Fabc.html\"", getResponseText());
    }

    public void testEncodeBeginAndEnd() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("a");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);
        renderer.encodeEnd(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"a\"></a>", getResponseText());
    }

    public void testEncodeBegin_WithAccesskey() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url");
        htmlOutputLink.setAccesskey("aa");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"url\" accesskey=\"aa\"", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setId("someId");
        htmlOutputLink.setValue("url");
        htmlOutputLink.setAccesskey("aa");
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a id=\"someId\" href=\"url\" accesskey=\"aa\"",
                getResponseText());
    }

    public void testA() throws Exception {
        System.out.println((int) 'い');
        System.out.println(URLEncoder.encode("あい", "UTF-8"));
    }

    public void testEncodeBegin_WithParam() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url.html");

        UIParameter param = new UIParameter();
        param.setName("a");
        param.setValue("b");
        htmlOutputLink.getChildren().add(param);

        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"url.html%3Fa%3Db", getResponseText());
    }

    public void testEncodeBegin_WithParams() throws Exception {
        HtmlOutputLink htmlOutputLink = new HtmlOutputLink();
        htmlOutputLink.setValue("url");
        {
            UIParameter param = new UIParameter();
            param.setName("a");
            param.setValue(Character.valueOf((char) 12354));
            htmlOutputLink.getChildren().add(param);
        }
        {
            UIParameter param = new UIParameter();
            param.setName("bb");
            param.setValue("bar");
            htmlOutputLink.getChildren().add(param);
        }
        HtmlOutputLinkRenderer renderer = createHtmlOutputLinkRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLink);

        assertEquals(true, calls_[0]);
        assertEquals("<a href=\"url?" + "a=%E3%81%82" + "&" + "bb=bar\"",
                getResponseText());
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
