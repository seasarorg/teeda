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

import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockHtmlOutputText;
import org.seasar.teeda.core.mock.MockHtmlPanelGroup;

/**
 * @author manhole
 */
public class HtmlPanelGroupRendererTest extends RendererTest {

    private HtmlPanelGroupRenderer renderer;

    private MockHtmlPanelGroup htmlPanelGroup;

    private HtmlOutputTextRenderer textRenderer;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlPanelGroupRenderer();
        textRenderer = new HtmlOutputTextRenderer();
        htmlPanelGroup = new MockHtmlPanelGroup();
        htmlPanelGroup.setRenderer(renderer);
    }

    public void testEncode_NoChild() throws Exception {
        // ## Arrange ##
        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlPanelGroup);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("a");
            child.setRenderer(textRenderer);
            htmlPanelGroup.getChildren().add(child);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("a", getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("a");
            child.setRenderer(textRenderer);
            htmlPanelGroup.getChildren().add(child);
        }
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("b");
            child.setRenderer(textRenderer);
            htmlPanelGroup.getChildren().add(child);
        }
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("c");
            child.setRenderer(textRenderer);
            htmlPanelGroup.getChildren().add(child);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("abc", getResponseText());
    }

    public void testEncode_NestedChildren() throws Exception {
        // ## Arrange ##
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("a");
            child.setRenderer(textRenderer);
            htmlPanelGroup.getChildren().add(child);
        }
        {
            MockHtmlPanelGroup childGroup = new MockHtmlPanelGroup();
            childGroup.setRenderer(renderer);
            {
                MockHtmlOutputText child = new MockHtmlOutputText();
                child.setValue("b");
                child.setRenderer(textRenderer);
                childGroup.getChildren().add(child);
            }
            {
                MockHtmlOutputText child = new MockHtmlOutputText();
                child.setValue("c");
                child.setRenderer(textRenderer);
                childGroup.getChildren().add(child);
            }
            htmlPanelGroup.getChildren().add(childGroup);
        }
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setValue("d");
            child.setRenderer(textRenderer);
            htmlPanelGroup.getChildren().add(child);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("abcd", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlPanelGroup.setRendered(false);
        htmlPanelGroup.setId("a");

        HtmlOutputTextRenderer textRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText child = new MockHtmlOutputText();
        child.setValue("v");
        child.setRenderer(textRenderer);
        htmlPanelGroup.getChildren().add(child);

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        htmlPanelGroup.setId("aaa");

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("<span id=\"aaa\"></span>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        htmlPanelGroup.getAttributes().put("a", "b");

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("<span a=\"b\"></span>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        htmlPanelGroup.getAttributes().put("a.c", "b");

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Style() throws Exception {
        // ## Arrange ##
        htmlPanelGroup.setStyle("a");
        htmlPanelGroup.setStyleClass("b");

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        Diff diff = new Diff("<span style=\"a\" class=\"b\"></span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // ## Arrange ##
        htmlPanelGroup.setId("a");
        htmlPanelGroup.setStyle("b");
        htmlPanelGroup.setStyleClass("c");

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGroup);

        // ## Assert ##
        Diff diff = new Diff("<span id=\"a\" style=\"b\" class=\"c\"></span>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer.getRendersChildren());
    }

    private HtmlPanelGroupRenderer createHtmlPanelGroupRenderer() {
        return (HtmlPanelGroupRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlPanelGroupRenderer renderer = new HtmlPanelGroupRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
