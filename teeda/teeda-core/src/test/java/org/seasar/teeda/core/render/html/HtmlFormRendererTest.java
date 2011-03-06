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

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.config.webapp.element.impl.WebappConfigImpl;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlForm;
import org.seasar.teeda.core.mock.MockStateManager;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;

/**
 * @author manhole
 */
public class HtmlFormRendererTest extends RendererTest {

    // TODO ViewHandler#getActionURL
    // TODO ExternalContext#encodeActionURL

    private HtmlFormRenderer renderer;

    private MockHtmlForm htmlForm;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlFormRenderer();
        htmlForm = new MockHtmlForm();
        htmlForm.setRenderer(renderer);
        htmlForm.setEnctype(null);

        // MockHtmlFormのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedCalls");
    }

    public void testEncode_NoValue() throws Exception {
        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/aa");

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        System.out.println(getResponseText());
        assertEquals(
                "<form name=\"_id0\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" action=\"/aa\">"
                        + "<input type=\"hidden\" name=\"_id0/aa\" value=\"_id0\" />"
                        + "</form>", getResponseText());
    }

    public void testEncode_CallsViewHandler() throws Exception {
        MockFacesContext context = getFacesContext();
        final boolean[] calls = { false };
        context.getApplication().setViewHandler(new MockViewHandlerImpl() {
            public void writeState(FacesContext context) throws IOException {
                calls[0] = true;
            }
        });
        context.getViewRoot().setViewId("/abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlForm.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/abc");
        htmlForm.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        assertEquals(
                "<form id=\"a\" name=\"a\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" action=\"/abc\">"
                        + "<input type=\"hidden\" name=\"a/abc\" value=\"a\" />"
                        + "</form>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/abc");
        htmlForm.setId("a");
        htmlForm.getAttributes().put("zz", "yy");

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        assertEquals(
                "<form id=\"a\" name=\"a\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" zz=\"yy\" action=\"/abc\">"
                        + "<input type=\"hidden\" name=\"a/abc\" value=\"a\" />"
                        + "</form>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlForm.setAccept("a");
        htmlForm.setAcceptcharset("b");
        htmlForm.setDir("c");
        htmlForm.setEnctype("d");
        htmlForm.setLang("e");
        htmlForm.setOnclick("f");
        htmlForm.setOndblclick("g");
        htmlForm.setOnkeydown("h");
        htmlForm.setOnkeypress("i");
        htmlForm.setOnkeyup("j");
        htmlForm.setOnmousedown("k");
        htmlForm.setOnmousemove("l");
        htmlForm.setOnmouseout("m");
        htmlForm.setOnmouseover("n");
        htmlForm.setOnmouseup("o");
        htmlForm.setOnreset("p");
        htmlForm.setOnsubmit("q");
        htmlForm.setStyle("r");
        htmlForm.setStyleClass("s");
        htmlForm.setTarget("t");
        htmlForm.setTitle("u");

        htmlForm.setId("AA");
        htmlForm.getAttributes().put("name", "BB");
        htmlForm.getAttributes().put("method", "CC");

        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/xyz");
        encodeByRenderer(renderer, htmlForm);

        Diff diff = new Diff("<form id=\"AA\" name=\"AA\" method=\"post\""
                + " accept=\"a\"" + " accept-charset=\"b\"" + " dir=\"c\""
                + " enctype=\"d\"" + " lang=\"e\"" + " onclick=\"f\""
                + " ondblclick=\"g\"" + " onkeydown=\"h\""
                + " onkeypress=\"i\"" + " onkeyup=\"j\"" + " onmousedown=\"k\""
                + " onmousemove=\"l\"" + " onmouseout=\"m\""
                + " onmouseover=\"n\"" + " onmouseup=\"o\"" + " onreset=\"p\""
                + " onsubmit=\"q\"" + " style=\"r\"" + " class=\"s\""
                + " target=\"t\"" + " title=\"u\""
                + " action=\"/xyz?newwindow=true\">"
                + "<input type=\"hidden\" name=\"AA/xyz\" value=\"AA\" />"
                + "</form>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithoutCookie() throws Exception {
        MockFacesContext facesContext = getFacesContext();
        MockExternalContext externalContext = (MockExternalContext) facesContext
                .getExternalContext();
        externalContext
                .setMockHttpServletResponse(new MockHttpServletResponseImpl(
                        externalContext.getMockHttpServletRequest()) {
                    public String encodeURL(String url) {
                        return url + ";jsessionid=HOGEHOGE";
                    }
                });
        facesContext.getViewRoot().setViewId("/aa");

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        System.out.println(getResponseText());
        assertEquals(
                "<form name=\"_id0\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" action=\"/aa;jsessionid=HOGEHOGE\">"
                        + "<input type=\"hidden\" name=\"_id0/aa\" value=\"_id0\" />"
                        + "</form>", getResponseText());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlForm.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlForm);

        // ## Assert ##
        assertEquals(1, htmlForm.getSetSubmittedCalls());
        assertEquals(false, htmlForm.isSubmitted());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlForm.setClientId("key1");

        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/xyz");
        context.getExternalContext().getRequestParameterMap().put("key1/xyz",
                "12345");

        // ## Act ##
        renderer.decode(context, htmlForm);

        // ## Assert ##
        assertEquals(1, htmlForm.getSetSubmittedCalls());
        assertEquals(true, htmlForm.isSubmitted());
    }

    public void testAction_Null() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        assertTrue(getResponseText().indexOf("action") == -1);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testEncode_HiddenValue() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/aa");
        HtmlFormRenderer.setCommandLinkHiddenParameter(htmlForm, "hoge", "foo");
        try {
            // ## Act ##
            encodeByRenderer(renderer, htmlForm);

            // ## Assert ##
            // ## Assert ##
            String readText = "<form name=\"_id0\" method=\"post\" "
                    + "enctype=\"application/x-www-form-urlencoded\" "
                    + "action=\"/aa\"><input type=\"hidden\" name=\"_id0/aa\" "
                    + "value=\"_id0\" /><input type=\"hidden\" name=\"hoge\" value=\"foo\" />"
                    + "<script language=\"JavaScript\" type=\"text/javascript\">\n"
                    + "<!--\n"
                    + "function clear__5Fid0(){var f = document.forms['_id0']; f.elements['hoge'].value='null'; f.target='';} clear__5Fid0();\n"
                    + "//-->\n</script>" + "</form>";

            final Diff diff = diff(readText, getResponseText());
            assertEquals(diff.toString(), true, diff.identical());
        } finally {
            HtmlFormRenderer.clearCommandLinkHiddenParameters(htmlForm, "hoge");
        }
    }

    public void testEncode_encodeActionURL() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        MockServletContext servletContext = new MockServletContextImpl("/aaa");
        MockHttpServletRequest req = new MockHttpServletRequestImpl(
                servletContext, "/bbb");
        MockHttpServletResponse res = new MockHttpServletResponseImpl(req);
        MockExternalContext extContext = new MockExternalContextImpl(
                servletContext, req, res);
        context.setExternalContext(extContext);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        context.getApplication().setViewHandler(handler);
        context.getViewRoot().setViewId("/cc");
        MockStateManager sm = new MockStateManager();
        context.getApplication().setStateManager(sm);

        WebappConfigImpl webAppConfig = new WebappConfigImpl();
        extContext.getApplicationMap().put(WebappConfig.class.getName(),
                webAppConfig);

        // ## Act ##
        htmlForm.setTarget("_blank");
        encodeByRenderer(renderer, htmlForm);

        // ## Assert ##
        String readText = "<form name=\"_id0\" method=\"post\" enctype=\"application/x-www-form-urlencoded\" target=\"_blank\" action=\"/aaa/cc?newwindow=true\"><input type=\"hidden\" name=\"_id0/cc\" value=\"_id0\" /></form>";
        final Diff diff = diff(readText, getResponseText());
        //assertEquals(readText, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    private HtmlFormRenderer createHtmlFormRenderer() {
        return (HtmlFormRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlFormRenderer renderer = new HtmlFormRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
