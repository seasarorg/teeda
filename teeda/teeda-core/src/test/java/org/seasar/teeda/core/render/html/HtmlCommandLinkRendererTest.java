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
import org.seasar.teeda.core.mock.MockHtmlCommandLink;
import org.seasar.teeda.core.mock.MockHtmlForm;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class HtmlCommandLinkRendererTest extends RendererTest {

    private HtmlCommandLinkRenderer renderer;

    private MockHtmlCommandLink htmlCommandLink;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlCommandLinkRenderer();
        htmlCommandLink = new MockHtmlCommandLink();
        htmlCommandLink.setRenderer(renderer);
    }

    public void testNoParentForm() throws Exception {
        // ## Arrange ##
        // ## Act ##
        // ## Assert ##
        try {
            encodeByRenderer(renderer, htmlCommandLink);
            fail();
        } catch (TagNotFoundRuntimeException e) {
            ExceptionAssert.assertMessageExist(e);
        }
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("a");
        form.getChildren().add(htmlCommandLink);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        MockFacesContext context = getFacesContext();
        assertEquals("a", form.getClientId(context));
        assertEquals("<a" + " href=\"#\"" + " onclick=\"" + "clear_a();"
                + "var f = document.forms['a'];"
                + " f['a:__link_clicked__'].value = 'a:_id0';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_a();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setValue("abc");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " href=\"#\"" + " onclick=\"" + "clear_b();"
                + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:_id0';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_b();" + " return false;" + "\">abc</a>",
                getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setRendered(false);
        htmlCommandLink.setValue("abc");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("c");
        form.getChildren().add(htmlCommandLink);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "clear_b();" + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';"
                +
                // " f['a'].value = '1';" +
                " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_b();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_WithParameter() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        UIParameter param = new UIParameter();
        param.setName("c");
        param.setValue("1");
        htmlCommandLink.getChildren().add(param);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "clear_b();" + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';"
                + " f['c'].value = '1';" + " if (f.onsubmit) { f.onsubmit(); }"
                + " f.submit();" + "clear_b();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_WithParameters() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        {
            UIParameter param = new UIParameter();
            param.setName("c");
            param.setValue("1");
            htmlCommandLink.getChildren().add(param);
        }
        {
            UIParameter param = new UIParameter();
            param.setName("d");
            param.setValue("2");
            htmlCommandLink.getChildren().add(param);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "clear_b();" + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';"
                + " f['c'].value = '1';" + " f['d'].value = '2';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_b();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_WithNullParameter() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setId("a");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        UIParameter param = new UIParameter();
        param.setName("c");
        param.setValue(null);
        htmlCommandLink.getChildren().add(param);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " id=\"a\"" + " href=\"#\"" + " onclick=\""
                + "clear_b();" + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:a';"
                + " f['c'].value = 'null';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_b();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_IgnoreAttributes() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setId("aaa");

        final MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        // この属性は出力されないこと
        htmlCommandLink.setAction(new MockMethodBinding());
        htmlCommandLink.setImmediate(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " id=\"aaa\"" + " href=\"#\"" + " onclick=\""
                + "clear_b();" + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:aaa';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_b();" + " return false;" + "\"></a>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setValue("abc");
        htmlCommandLink.getAttributes().put("c", "d");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("b");
        form.getChildren().add(htmlCommandLink);

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals("<a" + " href=\"#\"" + " onclick=\"" + "clear_b();"
                + "var f = document.forms['b'];"
                + " f['b:__link_clicked__'].value = 'b:_id0';"
                + " if (f.onsubmit) { f.onsubmit(); }" + " f.submit();"
                + "clear_b();" + " return false;" + "\" c=\"d\">abc</a>",
                getResponseText());
    }

    public void testEncode_WithTarget() throws Exception {
        // ## Arrange ##
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("frm");
        form.getChildren().add(htmlCommandLink);
        htmlCommandLink.setTarget("_blank");
        htmlCommandLink.setId("x");
        htmlCommandLink.setValue("aaa");

        // ## Act ##
        encodeByRenderer(renderer, htmlCommandLink);

        // ## Assert ##
        assertEquals(
                "<a id=\"x\" href=\"#\"" + " onclick=\"" + "clear_frm();"
                        + "var f = document.forms['frm'];"
                        + " f['frm:__link_clicked__'].value = 'frm:x';"
                        + " f.target = '_blank';"
                        + " if (f.onsubmit) { f.onsubmit(); } f.submit();"
                        + "clear_frm();"
                        + " return false;\" target=\"_blank\">aaa</a>",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("zz");
        form.getChildren().add(htmlCommandLink);

        htmlCommandLink.setAccesskey("a");
        htmlCommandLink.setCharset("b");
        htmlCommandLink.setCoords("c");
        htmlCommandLink.setDir("d");
        htmlCommandLink.setHreflang("e");
        htmlCommandLink.setLang("f");
        htmlCommandLink.setOnblur("g");
        htmlCommandLink.setOnclick("h");
        htmlCommandLink.setOndblclick("i");
        htmlCommandLink.setOnfocus("j");
        htmlCommandLink.setOnkeydown("k");
        htmlCommandLink.setOnkeypress("l");
        htmlCommandLink.setOnkeyup("m");
        htmlCommandLink.setOnmousedown("n");
        htmlCommandLink.setOnmousemove("o");
        htmlCommandLink.setOnmouseout("p");
        htmlCommandLink.setOnmouseover("q");
        htmlCommandLink.setOnmouseup("r");
        htmlCommandLink.setRel("s");
        htmlCommandLink.setRev("t");
        htmlCommandLink.setShape("u");
        htmlCommandLink.setStyle("v");
        htmlCommandLink.setStyleClass("w");
        htmlCommandLink.setTabindex("x");
        htmlCommandLink.setTarget("y");
        htmlCommandLink.setTitle("z");
        htmlCommandLink.setType("1");

        htmlCommandLink.setId("A");
        htmlCommandLink.setValue("B");

        encodeByRenderer(renderer, htmlCommandLink);

        Diff diff = new Diff("<a" + " id=\"A\"" + " href=\"#\"" + " onclick=\""
                + "clear_zz();" + "var f = document.forms['zz'];"
                + " f['zz:__link_clicked__'].value = 'zz:A';"
                + " f.target = 'y';" + " if (f.onsubmit) { f.onsubmit(); }"
                + " f.submit();" + "clear_zz();" + " return false;\""
                + " accesskey=\"a\"" + " charset=\"b\"" + " coords=\"c\""
                + " dir=\"d\"" + " hreflang=\"e\"" + " lang=\"f\""
                + " onblur=\"g\"" + " ondblclick=\"i\"" + " onfocus=\"j\""
                + " onkeydown=\"k\"" + " onkeypress=\"l\"" + " onkeyup=\"m\""
                + " onmousedown=\"n\"" + " onmousemove=\"o\""
                + " onmouseout=\"p\"" + " onmouseover=\"q\""
                + " onmouseup=\"r\"" + " rel=\"s\"" + " rev=\"t\""
                + " shape=\"u\"" + " style=\"v\"" + " class=\"w\""
                + " tabindex=\"x\"" + " target=\"y\"" + " title=\"z\""
                + " type=\"1\">B</a>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithoutJavascript() throws Exception {
        // ## Arrange ##
        htmlCommandLink.setId("hoge");
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("foo");
        form.getChildren().add(htmlCommandLink);
        UIParameter param = new UIParameter();
        param.setName("bar");
        param.setValue("1111");

        htmlCommandLink.getChildren().add(param);
        htmlCommandLink.setValue("value");
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

        encodeByRenderer(renderer, htmlCommandLink);

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
        htmlCommandLink.setRenderer(renderer);
        htmlCommandLink.setClientId("linkClientId");

        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("formId");
        form.getChildren().add(htmlCommandLink);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlCommandLink);

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
        htmlCommandLink.setRenderer(renderer);
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
        renderer.decode(context, htmlCommandLink);

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
        htmlCommandLink.setRenderer(renderer);
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
        renderer.decode(context, htmlCommandLink);

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
        htmlCommandLink.setRenderer(renderer);
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
        renderer.decode(context, htmlCommandLink);

        // ## Assert ##
        ObjectAssert.assertInstanceOf(ActionEvent.class, args[0]);
        assertSame(htmlCommandLink, args[0].getSource());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer.getRendersChildren());
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
