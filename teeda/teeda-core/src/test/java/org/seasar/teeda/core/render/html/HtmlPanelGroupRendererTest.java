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

import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlPanelGroupRendererTest extends RendererTest {

    private HtmlPanelGroupRenderer renderer_;

    private MockHtmlPanelGroup htmlPanelGroup_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlPanelGroupRenderer();
        htmlPanelGroup_ = new MockHtmlPanelGroup();
        htmlPanelGroup_.setRenderer(renderer_);
    }

    public void testEncode_NoChild() throws Exception {
        // ## Arrange ##
        // ## Act ##
        renderer_.encodeBegin(getFacesContext(), htmlPanelGroup_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("a");
            child.setRenderer(htmlOutputTextRenderer);
            htmlPanelGroup_.getChildren().add(child);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        assertEquals("a", getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("a");
            child.setRenderer(htmlOutputTextRenderer);
            htmlPanelGroup_.getChildren().add(child);
        }
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("b");
            child.setRenderer(htmlOutputTextRenderer);
            htmlPanelGroup_.getChildren().add(child);
        }
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("c");
            child.setRenderer(htmlOutputTextRenderer);
            htmlPanelGroup_.getChildren().add(child);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        assertEquals("abc", getResponseText());
    }

    public void testEncode_NestedChildren() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("a");
            child.setRenderer(htmlOutputTextRenderer);
            htmlPanelGroup_.getChildren().add(child);
        }
        {
            MockHtmlPanelGroup childGroup = new MockHtmlPanelGroup();
            childGroup.setRenderer(renderer_);
            {
                MockHtmlOutputText child = new MockHtmlOutputText();
                child.setValue("b");
                child.setRenderer(htmlOutputTextRenderer);
                childGroup.getChildren().add(child);
            }
            {
                MockHtmlOutputText child = new MockHtmlOutputText();
                child.setValue("c");
                child.setRenderer(htmlOutputTextRenderer);
                childGroup.getChildren().add(child);
            }
            htmlPanelGroup_.getChildren().add(childGroup);
        }
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("d");
            child.setRenderer(htmlOutputTextRenderer);
            htmlPanelGroup_.getChildren().add(child);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        assertEquals("abcd", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlPanelGroup_.setRendered(false);
        htmlPanelGroup_.setId("a");

        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText child = new MockHtmlOutputText();
        child.setValue("v");
        child.setRenderer(htmlOutputTextRenderer);
        htmlPanelGroup_.getChildren().add(child);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        htmlPanelGroup_.setId("aaa");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        assertEquals("<span id=\"aaa\"></span>", getResponseText());
    }

    public void testEncode_Style() throws Exception {
        // ## Arrange ##
        htmlPanelGroup_.setStyle("a");
        htmlPanelGroup_.setStyleClass("b");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        assertEquals("<span style=\"a\" class=\"b\"></span>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // ## Arrange ##
        htmlPanelGroup_.setId("a");
        htmlPanelGroup_.setStyle("b");
        htmlPanelGroup_.setStyleClass("c");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGroup_);

        // ## Assert ##
        Diff diff = new Diff("<span id=\"a\" style=\"b\" class=\"c\"></span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer_.getRendersChildren());
    }

    private HtmlPanelGroupRenderer createHtmlPanelGroupRenderer() {
        return (HtmlPanelGroupRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlPanelGroupRenderer();
    }

}
