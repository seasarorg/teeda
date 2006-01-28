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

import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlPanelGridRendererTest extends RendererTest {

    private HtmlPanelGridRenderer renderer_;

    private MockHtmlPanelGrid htmlPanelGrid_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlPanelGridRenderer();
        htmlPanelGrid_ = new MockHtmlPanelGrid();
        htmlPanelGrid_.setRenderer(renderer_);
    }

    public void testEncodeBegin_NoValue() throws Exception {
        // ## Arrange ##
        // ## Act ##
        renderer_.encodeBegin(getFacesContext(), htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlPanelGrid_.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer_, getFacesContext(), htmlPanelGrid_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_Id() throws Exception {
        // ## Arrange ##
        htmlPanelGrid_.setId("aaa");

        // ## Act ##
        renderer_.encodeBegin(getFacesContext(), htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table id=\"aaa\"", getResponseText());
    }

    public void testEncodeBegin_TableStyle() throws Exception {
        // ## Arrange ##
        htmlPanelGrid_.setStyle("a");
        htmlPanelGrid_.setStyleClass("b");

        // ## Act ##
        renderer_.encodeBegin(getFacesContext(), htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table style=\"a\" class=\"b\"", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table><tbody></tbody></table>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlPanelGrid_.getFacets().put("header", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">a</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("aa");
        htmlPanelGrid_.getFacets().put("header", facet);
        htmlPanelGrid_.setHeaderClass("bb");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\" class=\"bb\">aa</th></tr>"
                        + "</thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderColspan() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlPanelGrid_.getFacets().put("header", facet);
        htmlPanelGrid_.setColumns(4);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table><thead><tr>"
                + "<th colspan=\"4\" scope=\"colgroup\">a</th>"
                + "</tr></thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        facet.setRendered(false);
        htmlPanelGrid_.getFacets().put("header", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlPanelGrid_.getFacets().put("footer", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("aa");
        htmlPanelGrid_.getFacets().put("footer", facet);
        htmlPanelGrid_.setFooterClass("bb");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td class=\"bb\">aa</td></tr>"
                + "</tfoot>", getResponseText());
    }

    public void testEncodeBegin_TableFooterColspan() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlPanelGrid_.getFacets().put("footer", facet);
        htmlPanelGrid_.setColumns(4);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr>" + "<td colspan=\"4\">a</td>"
                + "</tr></tfoot>", getResponseText());
    }

    public void testEncodeBegin_TableFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        facet.setRendered(false);
        htmlPanelGrid_.getFacets().put("footer", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeChildren() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setRenderer(htmlOutputTextRenderer);
            child.setValue("Z");
            htmlPanelGrid_.getChildren().add(child);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>Z</td></tr>" + "</tbody>",
                getResponseText());
    }

    public void testEncodeChildren_RowAndColumnStyle() throws Exception {
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlPanelGrid_.setColumnClasses("c1, c2, c3");
        htmlPanelGrid_.setRowClasses("r1a r1b, r2");
        htmlPanelGrid_.setColumns(7);

        for (int i = 0; i < 15; i++) {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setRenderer(htmlOutputTextRenderer);
            char c = (char) ('A' + i); // A .. O
            child.setValue(String.valueOf(c));
            htmlPanelGrid_.getChildren().add(child);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeChildren(context, htmlPanelGrid_);

        // ## Assert ##
        assertEquals(
                "<tbody>"
                        + "<tr class=\"r1a r1b\">"
                        + "<td class=\"c1\">A</td><td class=\"c2\">B</td><td class=\"c3\">C</td>"
                        + "<td class=\"c1\">D</td><td class=\"c2\">E</td><td class=\"c3\">F</td>"
                        + "<td class=\"c1\">G</td>"
                        + "</tr>"

                        + "<tr class=\"r2\">"
                        + "<td class=\"c1\">H</td><td class=\"c2\">I</td><td class=\"c3\">J</td>"
                        + "<td class=\"c1\">K</td><td class=\"c2\">L</td><td class=\"c3\">M</td>"
                        + "<td class=\"c1\">N</td>" + "</tr>"

                        + "<tr class=\"r1a r1b\">" + "<td class=\"c1\">O</td>"
                        + "</tr>"

                        + "</tbody>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // attributes
        {
            htmlPanelGrid_.setBgcolor("a");
            htmlPanelGrid_.setBorder(3);
            htmlPanelGrid_.setCellpadding("c");
            htmlPanelGrid_.setCellspacing("d");
            htmlPanelGrid_.setColumnClasses("e1, e2");
            htmlPanelGrid_.setDir("f");
            htmlPanelGrid_.setFooterClass("g");
            htmlPanelGrid_.setFrame("h");
            htmlPanelGrid_.setHeaderClass("i");
            htmlPanelGrid_.setLang("j");
            htmlPanelGrid_.setOnclick("k");
            htmlPanelGrid_.setOndblclick("l");
            htmlPanelGrid_.setOnkeydown("m");
            htmlPanelGrid_.setOnkeypress("n");
            htmlPanelGrid_.setOnkeyup("o");
            htmlPanelGrid_.setOnmousedown("p");
            htmlPanelGrid_.setOnmousemove("q");
            htmlPanelGrid_.setOnmouseout("r");
            htmlPanelGrid_.setOnmouseover("s");
            htmlPanelGrid_.setOnmouseup("t");
            htmlPanelGrid_.setRowClasses("u1, u2");
            htmlPanelGrid_.setRules("v");
            htmlPanelGrid_.setStyle("w");
            htmlPanelGrid_.setStyleClass("x");
            htmlPanelGrid_.setSummary("y");
            htmlPanelGrid_.setTitle("z");
            htmlPanelGrid_.setWidth("1");
        }
        htmlPanelGrid_.setId("A");

        htmlPanelGrid_.setColumnClasses("c1, c2");
        htmlPanelGrid_.setRowClasses("r1, r2,r3");
        htmlPanelGrid_.setColumns(3);

        // table header, footer
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(htmlOutputTextRenderer);
            header.setValue("tableHeader");
            htmlPanelGrid_.getFacets().put("header", header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(htmlOutputTextRenderer);
            footer.setValue("tableFooter");
            htmlPanelGrid_.getFacets().put("footer", footer);
        }

        // table value
        for (int i = 10; i < 24; i++) {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setRenderer(htmlOutputTextRenderer);
            child.setValue(String.valueOf(i));
            htmlPanelGrid_.getChildren().add(child);
        }

        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlPanelGrid_);

        Diff diff = new Diff(
                "<table"
                        + " id=\"A\""
                        + " bgcolor=\"a\""
                        + " border=\"3\""
                        + " cellpadding=\"c\""
                        + " cellspacing=\"d\""
                        + " dir=\"f\""
                        + " frame=\"h\""
                        + " lang=\"j\""
                        + " onclick=\"k\""
                        + " ondblclick=\"l\""
                        + " onkeydown=\"m\""
                        + " onkeypress=\"n\""
                        + " onkeyup=\"o\""
                        + " onmousedown=\"p\""
                        + " onmousemove=\"q\""
                        + " onmouseout=\"r\""
                        + " onmouseover=\"s\""
                        + " onmouseup=\"t\""
                        + " rules=\"v\""
                        + " style=\"w\""
                        + " class=\"x\""
                        + " summary=\"y\""
                        + " title=\"z\""
                        + " width=\"1\""
                        + ">"

                        + "<thead>"
                        + "<tr>"
                        + "<th class=\"i\" colspan=\"3\" scope=\"colgroup\">tableHeader</th>"
                        + "</tr>"
                        + "</thead>"

                        + "<tfoot>"
                        + "<tr>"
                        + "<td class=\"g\" colspan=\"3\">tableFooter</td>"
                        + "</tr>"
                        + "</tfoot>"

                        + "<tbody>"
                        + "<tr class=\"r1\"><td class=\"c1\">10</td><td class=\"c2\">11</td><td class=\"c1\">12</td></tr>"
                        + "<tr class=\"r2\"><td class=\"c1\">13</td><td class=\"c2\">14</td><td class=\"c1\">15</td></tr>"
                        + "<tr class=\"r3\"><td class=\"c1\">16</td><td class=\"c2\">17</td><td class=\"c1\">18</td></tr>"
                        + "<tr class=\"r1\"><td class=\"c1\">19</td><td class=\"c2\">20</td><td class=\"c1\">21</td></tr>"
                        + "<tr class=\"r2\"><td class=\"c1\">22</td><td class=\"c2\">23</td></tr>"
                        + "</tbody>"

                        + "</table>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer_.getRendersChildren());
    }

    private HtmlPanelGridRenderer createHtmlPanelGridRenderer() {
        return (HtmlPanelGridRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlPanelGridRenderer();
    }

    private static class MockHtmlPanelGrid extends HtmlPanelGrid {

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
