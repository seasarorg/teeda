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
import org.seasar.teeda.core.mock.MockHtmlOutputText;

/**
 * @author manhole
 */
public class HtmlPanelGridRendererTest extends RendererTest {

    private HtmlPanelGridRenderer renderer;

    private MockHtmlPanelGrid htmlPanelGrid;

    private HtmlOutputTextRenderer textRenderer;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlPanelGridRenderer();
        textRenderer = new HtmlOutputTextRenderer();
        htmlPanelGrid = new MockHtmlPanelGrid();
        htmlPanelGrid.setRenderer(renderer);
    }

    public void testEncodeBegin_NoValue() throws Exception {
        // ## Arrange ##
        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlPanelGrid.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGrid);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_Id() throws Exception {
        // ## Arrange ##
        htmlPanelGrid.setId("aaa");

        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table id=\"aaa\"", getResponseText());
    }

    public void testEncodeBegin_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        htmlPanelGrid.getAttributes().put("abc", "ABC");

        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table abc=\"ABC\"", getResponseText());
    }

    public void testEncodeBegin_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        htmlPanelGrid.getAttributes().put("a.bc", "ABC");

        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_TableStyle() throws Exception {
        // ## Arrange ##
        htmlPanelGrid.setStyle("a");
        htmlPanelGrid.setStyleClass("b");

        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table class=\"b\" style=\"a\"", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table><tbody></tbody></table>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlPanelGrid.getFacets().put("header", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">a</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("aa");
        htmlPanelGrid.getFacets().put("header", facet);
        htmlPanelGrid.setHeaderClass("bb");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\" class=\"bb\">aa</th></tr>"
                        + "</thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderColspan() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlPanelGrid.getFacets().put("header", facet);
        htmlPanelGrid.setColumns(4);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table><thead><tr>"
                + "<th colspan=\"4\" scope=\"colgroup\">a</th>"
                + "</tr></thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        facet.setRendered(false);
        htmlPanelGrid.getFacets().put("header", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlPanelGrid.getFacets().put("footer", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("aa");
        htmlPanelGrid.getFacets().put("footer", facet);
        htmlPanelGrid.setFooterClass("bb");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td class=\"bb\">aa</td></tr>"
                + "</tfoot>", getResponseText());
    }

    public void testEncodeBegin_TableFooterColspan() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlPanelGrid.getFacets().put("footer", facet);
        htmlPanelGrid.setColumns(4);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table><tfoot><tr>" + "<td colspan=\"4\">a</td>"
                + "</tr></tfoot>", getResponseText());
    }

    public void testEncodeBegin_TableFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        facet.setRendered(false);
        htmlPanelGrid.getFacets().put("footer", facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeChildren() throws Exception {
        {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setRenderer(textRenderer);
            child.setValue("Z");
            htmlPanelGrid.getChildren().add(child);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlPanelGrid);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>Z</td></tr>" + "</tbody>",
                getResponseText());
    }

    public void testEncodeChildren_RowAndColumnStyle() throws Exception {
        htmlPanelGrid.setColumnClasses("c1, c2, c3");
        htmlPanelGrid.setRowClasses("r1a r1b, r2");
        htmlPanelGrid.setColumns(7);

        for (int i = 0; i < 15; i++) {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setRenderer(textRenderer);
            char c = (char) ('A' + i); // A .. O
            child.setValue(String.valueOf(c));
            htmlPanelGrid.getChildren().add(child);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlPanelGrid);

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
            htmlPanelGrid.setBgcolor("a");
            htmlPanelGrid.setBorder(3);
            htmlPanelGrid.setCellpadding("c");
            htmlPanelGrid.setCellspacing("d");
            htmlPanelGrid.setColumnClasses("e1, e2");
            htmlPanelGrid.setDir("f");
            htmlPanelGrid.setFooterClass("g");
            htmlPanelGrid.setFrame("h");
            htmlPanelGrid.setHeaderClass("i");
            htmlPanelGrid.setLang("j");
            htmlPanelGrid.setOnclick("k");
            htmlPanelGrid.setOndblclick("l");
            htmlPanelGrid.setOnkeydown("m");
            htmlPanelGrid.setOnkeypress("n");
            htmlPanelGrid.setOnkeyup("o");
            htmlPanelGrid.setOnmousedown("p");
            htmlPanelGrid.setOnmousemove("q");
            htmlPanelGrid.setOnmouseout("r");
            htmlPanelGrid.setOnmouseover("s");
            htmlPanelGrid.setOnmouseup("t");
            htmlPanelGrid.setRowClasses("u1, u2");
            htmlPanelGrid.setRules("v");
            htmlPanelGrid.setStyle("w");
            htmlPanelGrid.setStyleClass("x");
            htmlPanelGrid.setSummary("y");
            htmlPanelGrid.setTitle("z");
            htmlPanelGrid.setWidth("1");
        }
        htmlPanelGrid.setId("A");

        htmlPanelGrid.setColumnClasses("c1, c2");
        htmlPanelGrid.setRowClasses("r1, r2,r3");
        htmlPanelGrid.setColumns(3);

        // table header, footer
        {
            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(textRenderer);
            header.setValue("tableHeader");
            htmlPanelGrid.getFacets().put("header", header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(textRenderer);
            footer.setValue("tableFooter");
            htmlPanelGrid.getFacets().put("footer", footer);
        }

        // table value
        for (int i = 10; i < 24; i++) {
            MockHtmlOutputText child = new MockHtmlOutputText();
            child.setRenderer(textRenderer);
            child.setValue(String.valueOf(i));
            htmlPanelGrid.getChildren().add(child);
        }

        encodeByRenderer(renderer, htmlPanelGrid);

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
        assertEquals(true, renderer.getRendersChildren());
    }

    private HtmlPanelGridRenderer createHtmlPanelGridRenderer() {
        return (HtmlPanelGridRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlPanelGridRenderer renderer = new HtmlPanelGridRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
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
