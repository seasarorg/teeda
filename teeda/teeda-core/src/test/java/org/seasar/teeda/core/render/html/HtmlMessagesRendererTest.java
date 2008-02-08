/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlMessages;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

/**
 * @author manhole
 */
public class HtmlMessagesRendererTest extends RendererTest {

    private HtmlMessagesRenderer renderer;

    private MockHtmlMessages htmlMessages;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlMessagesRenderer();
        htmlMessages = new MockHtmlMessages();
        htmlMessages.setRenderer(renderer);
    }

    public void testEncode_NoMessage() throws Exception {
        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoMessageValue_List() throws Exception {
        FacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage();
        context.addMessage(null, facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li></li></ul>", getResponseText());
    }

    public void testEncode_Summary1() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage(null, facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li>s</li></ul>", getResponseText());
    }

    public void testEncode_Summary1_NoEscape() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("<s>");
        context.addMessage(null, facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><s></li></ul>", getResponseText());
    }

    public void testEncode_Summary1_table() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage(null, facesMessage);
        htmlMessages.setLayout("table");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<table><tr><td>s</td></tr></table>", getResponseText());
    }

    public void testEncode_Summary2() throws Exception {
        FacesContext context = getFacesContext();
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s1");
            context.addMessage(null, facesMessage);
        }
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s2");
            context.addMessage("aaa", facesMessage);
        }
        htmlMessages.setLayout("list");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li>s1</li><li>s2</li></ul>", getResponseText());
    }

    public void testEncode_Summary2_table() throws Exception {
        FacesContext context = getFacesContext();
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s1");
            context.addMessage(null, facesMessage);
        }
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s2");
            context.addMessage("aaa", facesMessage);
        }
        htmlMessages.setLayout("table");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<table>" + "<tr><td>s1</td></tr>"
                + "<tr><td>s2</td></tr>" + "</table>", getResponseText());
    }

    public void testEncode_globalOnly() throws Exception {
        FacesContext context = getFacesContext();
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s1");
            context.addMessage("aaa", facesMessage);
        }
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s2");
            context.addMessage(null, facesMessage);
        }
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSummary("s3");
            context.addMessage(null, facesMessage);
        }
        htmlMessages.setGlobalOnly(true);
        htmlMessages.setLayout("table");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<table>" + "<tr><td>s2</td></tr>"
                + "<tr><td>s3</td></tr>" + "</table>", getResponseText());
    }

    public void testEncode_Detail() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.setShowSummary(false);
        htmlMessages.setShowDetail(true);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage(null, facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li>d</li></ul>", getResponseText());
    }

    public void testEncode_Detail_Escape() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.setShowSummary(false);
        htmlMessages.setShowDetail(true);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("<d>");
        context.addMessage(null, facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><d></li></ul>", getResponseText());
    }

    public void testEncode_SummaryAndDetail() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.setShowSummary(true);
        htmlMessages.setShowDetail(true);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage("a", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li>s d</li></ul>", getResponseText());
    }

    public void testEncode_SummaryAndDetail_NoEscape() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.setShowSummary(true);
        htmlMessages.setShowDetail(true);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("<s>");
        facesMessage.setDetail("<d>");
        context.addMessage("a", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><s> <d></li></ul>", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        htmlMessages.setRendered(false);
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage("w", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.setId("ab");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage("foo", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul id=\"ab\"><li>s</li></ul>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.getAttributes().put("abc", "ABC");
        htmlMessages.getAttributes().put("a.b", "AAA");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage("foo", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><span abc=\"ABC\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.getAttributes().put("a.b", "AAA");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage("foo", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li>s</li></ul>", getResponseText());
    }

    public void testEncode_Id_table() throws Exception {
        FacesContext context = getFacesContext();
        htmlMessages.setId("ab");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage("foo", facesMessage);

        htmlMessages.setLayout("table");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<table id=\"ab\"><tr><td>s</td></tr></table>",
                getResponseText());
    }

    public void testEncode_InfoStyle() throws Exception {
        FacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        htmlMessages.setInfoStyle("aaa");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><span style=\"aaa\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_InfoClass() throws Exception {
        FacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        htmlMessages.setInfoClass("bb");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><span class=\"bb\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_StyleClassAndWarnStyle() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        context.addMessage("foo", facesMessage);

        htmlMessages.setStyle("ss");
        htmlMessages.setWarnClass("tt");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"ss\" class=\"tt\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_ErrorStyleAndStyleClass() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("foo", facesMessage);

        htmlMessages.setErrorStyle("ee");
        htmlMessages.setStyleClass("ss");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"ee\" class=\"ss\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_InfoStyleAndInfoClass() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessages);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"is\" class=\"ic\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_WarnStyleAndWarnClass() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessages);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"ws\" class=\"wc\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_ErrorStyleAndErrorClass() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessages);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"es\" class=\"ec\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_FatalStyleAndFatalClass() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessages);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"fs\" class=\"fc\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_FatalStyleAndFatalClass_table() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessages);
        htmlMessages.setLayout("table");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<table><tr><td>"
                + "<span style=\"fs\" class=\"fc\">s</span>"
                + "</td></tr></table>", getResponseText());
    }

    public void testEncode_StyleAndStyleClass() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage(null, facesMessage);

        htmlMessages.setStyle("s1");
        htmlMessages.setStyleClass("s2");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals(
                "<ul><li><span style=\"s1\" class=\"s2\">s</span></li></ul>",
                getResponseText());
    }

    private void arrangeStyles(HtmlMessages htmlMessages) {
        htmlMessages.setInfoClass("ic");
        htmlMessages.setInfoStyle("is");
        htmlMessages.setWarnClass("wc");
        htmlMessages.setWarnStyle("ws");
        htmlMessages.setErrorClass("ec");
        htmlMessages.setErrorStyle("es");
        htmlMessages.setFatalClass("fc");
        htmlMessages.setFatalStyle("fs");
        htmlMessages.setStyle("s");
        htmlMessages.setStyleClass("sc");
    }

    public void testEncode_Title() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        context.addMessage("foo", facesMessage);

        htmlMessages.setTitle("t");

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><span title=\"t\">s</span></li></ul>",
                getResponseText());
    }

    public void testEncode_Tooltip() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage("foo", facesMessage);

        htmlMessages.setTooltip(true);
        htmlMessages.setShowDetail(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("<ul><li><span title=\"s\">d</span></li></ul>",
                getResponseText());
    }

    // XXX is this OK?
    public void testEncode_TitleAndTooltip() throws Exception {
        FacesContext context = getFacesContext();

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage("bar", facesMessage);

        htmlMessages.setTitle("t"); // ignored
        htmlMessages.setTooltip(true);
        htmlMessages.setShowDetail(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlMessages);

        // ## Assert ##
        assertEquals("prioritize [tooltip] over [title]",
                "<ul><li><span title=\"s\">d</span></li></ul>",
                getResponseText());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlMessagesRenderer createHtmlMessagesRenderer() {
        return (HtmlMessagesRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlMessagesRenderer renderer = new HtmlMessagesRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlMessages extends HtmlMessages {
        private Renderer renderer_;

        private String clientId_;

        public void setRenderer(Renderer renderer) {
            renderer_ = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer_ != null) {
                return renderer_;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId_ != null) {
                return clientId_;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            clientId_ = clientId;
        }
    }

}
