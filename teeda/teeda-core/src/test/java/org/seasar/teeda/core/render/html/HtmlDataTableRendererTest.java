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

import javax.faces.component.UIColumn;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.render.html.HtmlDataTableRenderer.LoopList;

/**
 * @author manhole
 */
public class HtmlDataTableRendererTest extends RendererTest {

    private HtmlDataTableRenderer renderer_;

    private MockHtmlDataTable htmlDataTable_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlDataTableRenderer();
        htmlDataTable_ = new MockHtmlDataTable();
        htmlDataTable_.setRenderer(renderer_);
    }

    public void testEncodeBegin() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_TableStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setStyle("s");
        htmlDataTable_.setStyleClass("t");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table style=\"s\" class=\"t\"", getResponseText());
    }

    public void testEncodeBeginToChildrenToEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);
        renderer_.encodeChildren(context, htmlDataTable_);
        renderer_.encodeEnd(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_Id() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setId("aa");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table id=\"aa\"", getResponseText());
    }

    public void testEncodeBeginToChildrenToEnd() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);
        renderer_.encodeChildren(context, htmlDataTable_);
        renderer_.encodeEnd(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tbody></tbody></table>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">a</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setHeader(facet);
        htmlDataTable_.setHeaderClass("cc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\" class=\"cc\">a</th></tr>"
                        + "</thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        facet.setRendered(false);
        htmlDataTable_.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead><tr>" + "<th colgroup=\"col\">c1</th>"
                + "<th colgroup=\"col\">c2</th>" + "</tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderStyle() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlDataTable_.setHeaderClass("ccc");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead><tr>"
                + "<th colgroup=\"col\" class=\"ccc\">c1</th>"
                + "<th colgroup=\"col\" class=\"ccc\">c2</th>"
                + "</tr></thead>", getResponseText());
    }

    public void testEncodeBegin_HeaderColumnRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th colgroup=\"col\">c2</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            colFacet.setRendered(false);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead><tr>" + "<th colgroup=\"col\"></th>"
                + "<th colgroup=\"col\">c2</th>" + "</tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_HeaderAndColumnsFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableFacet = new MockHtmlOutputText();
        tableFacet.setRenderer(htmlOutputTextRenderer);
        tableFacet.setValue("a");
        htmlDataTable_.setHeader(tableFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colHeaderFacet = new MockHtmlOutputText();
            colHeaderFacet.setValue("c2");
            colHeaderFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colHeaderFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead>"
                + "<tr><th colspan=\"2\" scope=\"colgroup\">a</th></tr>"
                + "<tr>" + "<th colgroup=\"col\">c1</th>"
                + "<th colgroup=\"col\">c2</th></tr>" + "</thead>",
                getResponseText());
    }

    public void testEncodeBegin_NotRenderHeaderColspanIfSingleColumn()
            throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(htmlOutputTextRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable_.setHeader(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setHeader(colFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><thead>"
                + "<tr><th scope=\"colgroup\">a</th></tr>"
                + "<tr><th colgroup=\"col\">col1</th></tr>" + "</thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        htmlDataTable_.setFooter(facet);
        htmlDataTable_.setFooterClass("d");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td class=\"d\">a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(new HtmlOutputTextRenderer());
        facet.setValue("a");
        facet.setRendered(false);
        htmlDataTable_.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_ColumnFooterFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>c1</td><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnFooterStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable_.setFooterClass("ee");
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table>" + "<tfoot>" + "<tr><td class=\"ee\">c1</td>"
                + "<td class=\"ee\">c2</td></tr>" + "</tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_FooterColumnRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            colFacet.setRendered(false);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td></td><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_FooterAndColumnsFacet() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(htmlOutputTextRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable_.setFooter(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col2");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot>" + "<tr><td colspan=\"2\">a</td></tr>"
                + "<tr><td>col1</td>" + "<td>col2</td></tr>" + "</tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_NotRenderFooterColspanIfSingleColumn()
            throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(htmlOutputTextRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable_.setFooter(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(htmlOutputTextRenderer);
            col.setFooter(colFacet);
            htmlDataTable_.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.encodeBegin(context, htmlDataTable_);

        // ## Assert ##
        assertEquals("<table><tfoot>" + "<tr><td>a</td></tr>"
                + "<tr><td>col1</td></tr>" + "</tfoot>", getResponseText());
    }

    public void testLoopList() throws Exception {
        LoopList loopList = new LoopList();
        assertEquals(null, loopList.next());
        assertEquals(true, loopList.isEmpty());
        assertEquals(false, loopList.isNotEmpty());
        loopList.add("a");
        assertEquals(false, loopList.isEmpty());
        assertEquals(true, loopList.isNotEmpty());
        loopList.add("bb");
        loopList.add("ccc");

        assertEquals(false, loopList.isEmpty());
        assertEquals(true, loopList.isNotEmpty());
        assertEquals("a", loopList.next());
        assertEquals("bb", loopList.next());
        assertEquals("ccc", loopList.next());
        assertEquals("a", loopList.next());
        assertEquals("bb", loopList.next());
        assertEquals("ccc", loopList.next());
        assertEquals("a", loopList.next());
        assertEquals("bb", loopList.next());
        loopList.moveFirst();
        assertEquals("a", loopList.next());
        assertEquals("bb", loopList.next());
        assertEquals("ccc", loopList.next());
    }

    public void testSplitByComma() throws Exception {
        // ## Arrange ##
        // ## Act ##
        String[] result = renderer_.splitByComma("a, b  ,c , d");

        // ## Assert ##
        assertEquals(4, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals("d", result[3]);
    }

    // TODO test

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer_.getRendersChildren());
    }

    private HtmlDataTableRenderer createHtmlDataTableRenderer() {
        return (HtmlDataTableRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlDataTableRenderer();
    }

}
