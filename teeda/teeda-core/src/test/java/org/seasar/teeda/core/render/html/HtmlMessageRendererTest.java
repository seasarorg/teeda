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

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class HtmlMessageRendererTest extends RendererTest {

    private HtmlMessageRenderer renderer_;

    private MockHtmlMessage htmlMessage_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlMessageRenderer();
        htmlMessage_ = new MockHtmlMessage();
        htmlMessage_.setRenderer(renderer_);
    }

    public void testEncode_NullForAttribute() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor(null);

        // ## Act ##
        // ## Assert ##
        try {
            encodeByRenderer(renderer_, context, htmlMessage_);
        } catch (FacesException fe) {
            ExceptionAssert.assertMessageExist(fe);
        }
    }

    public void testEncode_NoForComponent() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("barFor");

        // ## Act ##
        // ## Assert ##
        try {
            encodeByRenderer(renderer_, context, htmlMessage_);
        } catch (FacesException fe) {
            ExceptionAssert.assertMessageExist(fe);
        }
    }

    public void testEncode_NoMessage() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoMessageValue() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");
        FacesMessage facesMessage = new FacesMessage();
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Detail() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("detail", getResponseText());
    }

    public void testEncode_Detail_NoEscape() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("<detail>");
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<detail>", getResponseText());
    }

    public void testEncode_FirstMessageOnly() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");

        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setDetail("first");
            context.addMessage("fooFor", facesMessage);
        }
        {
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setDetail("second");
            context.addMessage("fooFor", facesMessage);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("first", getResponseText());
    }

    public void testEncode_Summary() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");
        htmlMessage_.setShowSummary(true);
        htmlMessage_.setShowDetail(false);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("summary");
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("summary", getResponseText());
    }

    public void testEncode_Summary_NoEscape() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");
        htmlMessage_.setShowSummary(true);
        htmlMessage_.setShowDetail(false);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("<summary>");
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<summary>", getResponseText());
    }

    public void testEncode_SummaryAndDetail() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");
        htmlMessage_.setShowSummary(true);
        htmlMessage_.setShowDetail(true);

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("ss");
        facesMessage.setDetail("dd");
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("ss dd", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        htmlMessage_.setRendered(false);
        FacesContext context = getFacesContext();
        arrangeForComponent("fooFor");
        htmlMessage_.setFor("fooFor");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        context.addMessage("fooFor", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");
        htmlMessage_.setId("ab");
        htmlMessage_.setFor("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span id=\"ab\">detail</span>", getResponseText());
    }

    public void testEncode_InfoStyle() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        htmlMessage_.setInfoStyle("aaa");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"aaa\">detail</span>", getResponseText());
    }

    public void testEncode_InfoClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        htmlMessage_.setInfoClass("bb");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span class=\"bb\">detail</span>", getResponseText());
    }

    public void testEncode_StyleClassAndWarnStyle() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        context.addMessage("foo", facesMessage);

        htmlMessage_.setStyle("ss");
        htmlMessage_.setWarnClass("tt");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"ss\" class=\"tt\">detail</span>",
                getResponseText());
    }

    public void testEncode_ErrorStyleAndStyleClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("foo", facesMessage);

        htmlMessage_.setErrorStyle("ee");
        htmlMessage_.setStyleClass("ss");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"ee\" class=\"ss\">detail</span>",
                getResponseText());
    }

    public void testEncode_InfoStyleAndInfoClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessage_);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"is\" class=\"ic\">detail</span>",
                getResponseText());
    }

    public void testEncode_WarnStyleAndWarnClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("detail");
        facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessage_);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"ws\" class=\"wc\">detail</span>",
                getResponseText());
    }

    public void testEncode_ErrorStyleAndErrorClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("d");
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessage_);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"es\" class=\"ec\">d</span>",
                getResponseText());
    }

    private void arrangeForComponent(String id) {
        UIComponent forComponent = new MockUIComponentBase();
        forComponent.setId(id);
        htmlMessage_.getChildren().add(forComponent);
        htmlMessage_.setFor(id);
    }

    public void testEncode_FatalStyleAndFatalClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("d");
        facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
        context.addMessage("foo", facesMessage);

        arrangeStyles(htmlMessage_);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"fs\" class=\"fc\">d</span>",
                getResponseText());
    }

    public void testEncode_StyleAndStyleClass() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("d");
        context.addMessage("foo", facesMessage);

        htmlMessage_.setStyle("s1");
        htmlMessage_.setStyleClass("s2");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span style=\"s1\" class=\"s2\">d</span>",
                getResponseText());
    }

    private void arrangeStyles(HtmlMessage htmlMessage) {
        htmlMessage.setInfoClass("ic");
        htmlMessage.setInfoStyle("is");
        htmlMessage.setWarnClass("wc");
        htmlMessage.setWarnStyle("ws");
        htmlMessage.setErrorClass("ec");
        htmlMessage.setErrorStyle("es");
        htmlMessage.setFatalClass("fc");
        htmlMessage.setFatalStyle("fs");
        htmlMessage.setStyle("s");
        htmlMessage.setStyleClass("sc");
    }

    public void testEncode_Title() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("d");
        context.addMessage("foo", facesMessage);

        htmlMessage_.setTitle("t");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span title=\"t\">d</span>", getResponseText());
    }

    public void testEncode_Tooltip() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("foo");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage("foo", facesMessage);

        htmlMessage_.setTooltip(true);
        htmlMessage_.setShowSummary(true);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("<span title=\"s\">d</span>", getResponseText());
    }

    // XXX is this OK?
    public void testEncode_TitleAndTooltip() throws Exception {
        FacesContext context = getFacesContext();
        arrangeForComponent("bar");

        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        facesMessage.setDetail("d");
        context.addMessage("bar", facesMessage);

        htmlMessage_.setTitle("t"); // ignored
        htmlMessage_.setTooltip(true);
        htmlMessage_.setShowSummary(true);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlMessage_);

        // ## Assert ##
        assertEquals("prioritize [tooltip] over [title]",
                "<span title=\"s\">d</span>", getResponseText());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlMessageRenderer createHtmlMessageRenderer() {
        return (HtmlMessageRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlMessageRenderer renderer = new HtmlMessageRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlMessage extends HtmlMessage {
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
