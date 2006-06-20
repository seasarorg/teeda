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
import javax.faces.component.UIViewRoot;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import junitx.framework.ObjectAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.core.exception.TagNotFoundRuntimeException;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class HtmlCommandLinkRendererTest extends RendererTest {

    private HtmlCommandLinkRenderer renderer_;

    private MockHtmlCommandLink htmlCommandLink_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlCommandLinkRenderer();
        htmlCommandLink_ = new MockHtmlCommandLink();
        htmlCommandLink_.setRenderer(renderer_);
    }

    public void testNoParentForm() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        // ## Assert ##
        try {
            encodeByRenderer(renderer_, context, htmlCommandLink_);
            fail();
        } catch (TagNotFoundRuntimeException e) {
            ExceptionAssert.assertMessageExist(e);
        }
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("a");
        form.getChildren().add(htmlCommandLink_);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals("a", form.getClientId(context));
        assertEquals("<a" + " href=\"#\"" + " onclick=\""
                + "var f = document.forms['a'];"
                + " f['a:__link_clicked__'].value = 'a:_id0';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + " return false;" + "\"></a>", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlCommandLink_.setValue("abc");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink_);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals("<a" + " href=\"#\"" + " onclick=\""
                + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:_id0';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + " return false;" + "\">abc</a>", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlCommandLink_.setRendered(false);
        htmlCommandLink_.setValue("abc");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("c");
        form.getChildren().add(htmlCommandLink_);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        // ## Arrange ##
        htmlCommandLink_.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink_);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';" +
                // " f['a'].value = '1';" +
                " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + " return false;" + "\"></a>", getResponseText());
    }

    public void testEncode_WithParameter() throws Exception {
        // ## Arrange ##
        htmlCommandLink_.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink_);

        UIParameter param = new UIParameter();
        param.setName("c");
        param.setValue("1");
        htmlCommandLink_.getChildren().add(param);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';"
                + " f['c'].value = '1';" + " if (f.onsubmit) { f.onsubmit(); }"
                + " f.submit();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_WithParameters() throws Exception {
        // ## Arrange ##
        htmlCommandLink_.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink_);

        {
            UIParameter param = new UIParameter();
            param.setName("c");
            param.setValue("1");
            htmlCommandLink_.getChildren().add(param);
        }
        {
            UIParameter param = new UIParameter();
            param.setName("d");
            param.setValue("2");
            htmlCommandLink_.getChildren().add(param);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';"
                + " f['c'].value = '1';" + " f['d'].value = '2';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + " return false;" + "\"></a>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("zz");
        form.getChildren().add(htmlCommandLink_);

        htmlCommandLink_.setAccesskey("a");
        htmlCommandLink_.setCharset("b");
        htmlCommandLink_.setCoords("c");
        htmlCommandLink_.setDir("d");
        htmlCommandLink_.setHreflang("e");
        htmlCommandLink_.setLang("f");
        htmlCommandLink_.setOnblur("g");
        htmlCommandLink_.setOnclick("h");
        htmlCommandLink_.setOndblclick("i");
        htmlCommandLink_.setOnfocus("j");
        htmlCommandLink_.setOnkeydown("k");
        htmlCommandLink_.setOnkeypress("l");
        htmlCommandLink_.setOnkeyup("m");
        htmlCommandLink_.setOnmousedown("n");
        htmlCommandLink_.setOnmousemove("o");
        htmlCommandLink_.setOnmouseout("p");
        htmlCommandLink_.setOnmouseover("q");
        htmlCommandLink_.setOnmouseup("r");
        htmlCommandLink_.setRel("s");
        htmlCommandLink_.setRev("t");
        htmlCommandLink_.setShape("u");
        htmlCommandLink_.setStyle("v");
        htmlCommandLink_.setStyleClass("w");
        htmlCommandLink_.setTabindex("x");
        htmlCommandLink_.setTarget("y");
        htmlCommandLink_.setTitle("z");
        htmlCommandLink_.setType("1");

        htmlCommandLink_.setId("A");
        htmlCommandLink_.setValue("B");

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlCommandLink_);

        Diff diff = new Diff("<a" + " id=\"A\"" + " href=\"#\"" + " onclick=\""
                + "var f = document.forms['zz'];"
                + " f['zz:__link_clicked__'].value = 'zz:A';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + " return false;\"" + " accesskey=\"a\"" + " charset=\"b\""
                + " coords=\"c\"" + " dir=\"d\"" + " hreflang=\"e\""
                + " lang=\"f\"" + " onblur=\"g\"" + " ondblclick=\"i\""
                + " onfocus=\"j\"" + " onkeydown=\"k\"" + " onkeypress=\"l\""
                + " onkeyup=\"m\"" + " onmousedown=\"n\""
                + " onmousemove=\"o\"" + " onmouseout=\"p\""
                + " onmouseover=\"q\"" + " onmouseup=\"r\"" + " rel=\"s\""
                + " rev=\"t\"" + " shape=\"u\"" + " style=\"v\""
                + " class=\"w\"" + " tabindex=\"x\"" + " target=\"y\""
                + " title=\"z\"" + " type=\"1\">B</a>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncodeWithoutJavascript() throws Exception {
        // ## Arrange ##
        htmlCommandLink_.setId("hoge");
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("foo");
        form.getChildren().add(htmlCommandLink_);
        UIParameter param = new UIParameter();
        param.setName("bar");
        param.setValue("1111");
        htmlCommandLink_.getChildren().add(param);
        htmlCommandLink_.setValue("value");
        MockFacesContext context = getFacesContext();
        MockExternalContext extContext = (MockExternalContext) context
                .getExternalContext();
        MockHttpServletRequest request = new MockHttpServletRequestImpl(
                new MockServletContextImpl("hoge"), "/faces");
        request.setPathInfo("/no_allow_js/aaa");
        extContext.setMockHttpServletRequest(request);

        FacesConfigOptions
                .setJavascriptNotPermittedPath(new String[] { "/no_allow_js" });
        UIViewRoot root = new UIViewRoot();
        root.setViewId("/baz");
        context.setViewRoot(root);
        // ## Act ##
        //        renderer_.encodeHtmlCommandLinkWithoutJavaScript(context, context
        //                .getResponseWriter(), htmlCommandLink_);

        encodeByRenderer(renderer_, context, htmlCommandLink_);

        // ## Assert ##
        assertEquals(
                "<a id=\"hoge\" href=\"/baz?foo/baz=foo/baz&amp;foo%3A__link_clicked__=foo%3Ahoge&amp;bar=1111\">value</a>",
                getResponseText());
    }

    public void testDecode_NoEntry() throws Exception {
        // ## Arrange ##
        final FacesEvent[] args = { null };
        MockHtmlCommandLink htmlCommandLink = new MockHtmlCommandLink() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        htmlCommandLink.setRenderer(renderer_);
        htmlCommandLink.setClientId("linkClientId");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("formId");
        form.getChildren().add(htmlCommandLink);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlCommandLink);

        // ## Assert ##
        assertEquals(null, args[0]);
    }

    public void testDecode_EmptyStringEntry() throws Exception {
        // ## Arrange ##
        final FacesEvent[] args = { null };
        MockHtmlCommandLink htmlCommandLink = new MockHtmlCommandLink() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        htmlCommandLink.setRenderer(renderer_);
        htmlCommandLink.setClientId("linkClientId");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("formId");
        form.getChildren().add(htmlCommandLink);

        MockFacesContext context = getFacesContext();
        String hiddenFieldName = "formId:__link_clicked__";
        context.getExternalContext().getRequestParameterMap().put(
                hiddenFieldName, "");

        // ## Act ##
        renderer_.decode(context, htmlCommandLink);

        // ## Assert ##
        assertEquals(null, args[0]);
    }

    public void testDecode_DifferentEntry() throws Exception {
        // ## Arrange ##
        final FacesEvent[] args = { null };
        MockHtmlCommandLink htmlCommandLink = new MockHtmlCommandLink() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        htmlCommandLink.setRenderer(renderer_);
        htmlCommandLink.setClientId("linkClientId");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("formId");
        form.getChildren().add(htmlCommandLink);

        MockFacesContext context = getFacesContext();
        String hiddenFieldName = "formId:__link_clicked__";
        context.getExternalContext().getRequestParameterMap().put(
                hiddenFieldName, "NoCommandLinkClientId");

        // ## Act ##
        renderer_.decode(context, htmlCommandLink);

        // ## Assert ##
        assertEquals(null, args[0]);
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        final FacesEvent[] args = { null };
        MockHtmlCommandLink htmlCommandLink = new MockHtmlCommandLink() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        htmlCommandLink.setRenderer(renderer_);
        htmlCommandLink.setClientId("linkClientId");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("formId");
        form.getChildren().add(htmlCommandLink);

        MockFacesContext context = getFacesContext();
        String hiddenFieldName = "formId:__link_clicked__";
        context.getExternalContext().getRequestParameterMap().put(
                hiddenFieldName, "linkClientId");

        // ## Act ##
        renderer_.decode(context, htmlCommandLink);

        // ## Assert ##
        ObjectAssert.assertInstanceOf(ActionEvent.class, args[0]);
        assertSame(htmlCommandLink, args[0].getSource());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer_.getRendersChildren());
    }

    private HtmlCommandLinkRenderer createHtmlCommandLinkRenderer() {
        return (HtmlCommandLinkRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlCommandLinkRenderer renderer = new HtmlCommandLinkRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
