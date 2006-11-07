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

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlDataTable;
import org.seasar.teeda.core.mock.MockHtmlOutputText;
import org.seasar.teeda.core.mock.MockHtmlPanelGroup;
import org.seasar.teeda.core.unit.TestUtil;

/**
 * @author manhole
 */
public class HtmlDataTableRendererTest extends RendererTest {

    private HtmlDataTableRenderer renderer;

    private MockHtmlDataTable htmlDataTable;

    private HtmlOutputTextRenderer textRenderer;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlDataTableRenderer();
        textRenderer = new HtmlOutputTextRenderer();
        htmlDataTable = new MockHtmlDataTable();
        htmlDataTable.setRenderer(renderer);
    }

    public void testEncodeBegin() throws Exception {
        // ## Arrange ##
        // ## Act ##
        renderer.encodeBegin(getFacesContext(), htmlDataTable);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlDataTable.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlDataTable);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncodeBegin_TableStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable.setStyle("s");
        htmlDataTable.setStyleClass("t");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table class=\"t\" style=\"s\"", getResponseText());
    }

    public void testEncodeBegin_Id() throws Exception {
        // ## Arrange ##
        htmlDataTable.setId("aa");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table id=\"aa\"", getResponseText());
    }

    public void testEncodeBegin_WithUnknownAttribute() throws Exception {
        // ## Arrange ##
        htmlDataTable.setId("aa");
        htmlDataTable.getAttributes().put("a", "b");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table id=\"aa\" a=\"b\"", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tbody></tbody></table>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlDataTable.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">a</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacet2() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("a");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("b");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        htmlDataTable.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\">ab</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableHeaderStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlDataTable.setHeader(facet);
        htmlDataTable.setHeaderClass("cc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th scope=\"colgroup\" class=\"cc\">a</th></tr>"
                        + "</thead>", getResponseText());
    }

    public void testEncodeBegin_TableHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        facet.setRendered(false);
        htmlDataTable.setHeader(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderFacet() throws Exception {
        // ## Arrange ##
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><thead><tr>" + "<th colgroup=\"col\">c1</th>"
                + "<th colgroup=\"col\">c2</th>" + "</tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable.setHeaderClass("ccc");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><thead><tr>"
                + "<th colgroup=\"col\" class=\"ccc\">c1</th>"
                + "<th colgroup=\"col\" class=\"ccc\">c2</th>"
                + "</tr></thead>", getResponseText());
    }

    public void testEncodeBegin_HeaderColumnRenderFalse() throws Exception {
        // ## Arrange ##
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals(
                "<table><thead><tr><th colgroup=\"col\">c2</th></tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnHeaderFacetRenderFalse() throws Exception {
        // ## Arrange ##
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            colFacet.setRendered(false);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><thead><tr>" + "<th colgroup=\"col\"></th>"
                + "<th colgroup=\"col\">c2</th>" + "</tr></thead>",
                getResponseText());
    }

    public void testEncodeBegin_HeaderAndColumnsFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText tableFacet = new MockHtmlOutputText();
        tableFacet.setRenderer(textRenderer);
        tableFacet.setValue("a");
        htmlDataTable.setHeader(tableFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colHeaderFacet = new MockHtmlOutputText();
            colHeaderFacet.setValue("c2");
            colHeaderFacet.setRenderer(textRenderer);
            col.setHeader(colHeaderFacet);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

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
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(textRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable.setHeader(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(textRenderer);
            col.setHeader(colFacet);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><thead>"
                + "<tr><th scope=\"colgroup\">a</th></tr>"
                + "<tr><th colgroup=\"col\">col1</th></tr>" + "</thead>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlDataTable.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacet2() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("a");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        {
            MockHtmlOutputText c = new MockHtmlOutputText();
            c.setValue("b");
            c.setRenderer(textRenderer);
            facet.getChildren().add(c);
        }
        htmlDataTable.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>ab</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterStyle() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        htmlDataTable.setFooter(facet);
        htmlDataTable.setFooterClass("d");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td class=\"d\">a</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_TableFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText facet = new MockHtmlOutputText();
        facet.setRenderer(textRenderer);
        facet.setValue("a");
        facet.setRendered(false);
        htmlDataTable.setFooter(facet);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table", getResponseText());
    }

    public void testEncodeBegin_ColumnFooterFacet() throws Exception {
        // ## Arrange ##
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>c1</td><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnFooterStyle() throws Exception {
        // ## Arrange ##
        htmlDataTable.setFooterClass("ee");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table>" + "<tfoot>" + "<tr><td class=\"ee\">c1</td>"
                + "<td class=\"ee\">c2</td></tr>" + "</tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_FooterColumnRenderFalse() throws Exception {
        // ## Arrange ##
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_ColumnFooterFacetRenderFalse() throws Exception {
        // ## Arrange ##
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c1");
            colFacet.setRenderer(textRenderer);
            colFacet.setRendered(false);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("c2");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot><tr><td></td><td>c2</td></tr></tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_FooterAndColumnsFacet() throws Exception {
        // ## Arrange ##
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(textRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable.setFooter(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col2");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot>" + "<tr><td colspan=\"2\">a</td></tr>"
                + "<tr><td>col1</td>" + "<td>col2</td></tr>" + "</tfoot>",
                getResponseText());
    }

    public void testEncodeBegin_NotRenderFooterColspanIfSingleColumn()
            throws Exception {
        // ## Arrange ##
        MockHtmlOutputText tableHeaderFacet = new MockHtmlOutputText();
        tableHeaderFacet.setRenderer(textRenderer);
        tableHeaderFacet.setValue("a");
        htmlDataTable.setFooter(tableHeaderFacet);

        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText colFacet = new MockHtmlOutputText();
            colFacet.setValue("col1");
            colFacet.setRenderer(textRenderer);
            col.setFooter(colFacet);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<table><tfoot>" + "<tr><td>a</td></tr>"
                + "<tr><td>col1</td></tr>" + "</tfoot>", getResponseText());
    }

    public void testEncodeChildren1() throws Exception {
        htmlDataTable.setValue(new String[] { "a", "b", "c" });
        htmlDataTable.setVar("fooVar");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>Z</td><td>a</td></tr>"
                + "<tr><td>Z</td><td>b</td></tr>"
                + "<tr><td>Z</td><td>c</td></tr>" + "</tbody>",
                getResponseText());
    }

    public void testEncodeChildren_WithNestedChildren() throws Exception {
        HtmlPanelGroupRenderer htmlPanelGroupRenderer = new HtmlPanelGroupRenderer();
        htmlDataTable.setValue(new String[] { "a", "b", "c" });
        htmlDataTable.setVar("fooVar");
        {
            UIColumn col = new UIColumn();
            {
                MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
                htmlOutputText.setRenderer(textRenderer);
                ELParser parser = new CommonsELParser();
                parser
                        .setExpressionProcessor(new CommonsExpressionProcessorImpl());
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{fooVar}", parser);
                htmlOutputText.setValueBinding("value", vb);
                col.getChildren().add(htmlOutputText);
            }
            {
                MockHtmlPanelGroup group = new MockHtmlPanelGroup();
                group.setRenderer(htmlPanelGroupRenderer);
                {
                    MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
                    htmlOutputText.setRenderer(textRenderer);
                    htmlOutputText.setValue("y");
                    group.getChildren().add(htmlOutputText);
                }
                {
                    MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
                    htmlOutputText.setRenderer(textRenderer);
                    htmlOutputText.setValue("z");
                    group.getChildren().add(htmlOutputText);
                }
                col.getChildren().add(group);
            }
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>ayz</td></tr>"
                + "<tr><td>byz</td></tr>" + "<tr><td>cyz</td></tr>"
                + "</tbody>", getResponseText());
    }

    public void testEncodeChildren_SetFirst() throws Exception {
        htmlDataTable.setValue(new String[] { "a", "b", "c" });
        htmlDataTable.setVar("fooVar");
        htmlDataTable.setFirst(1);
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>b</td></tr>" + "<tr><td>c</td></tr>"
                + "</tbody>", getResponseText());
    }

    public void testEncodeChildren_SetRows() throws Exception {
        htmlDataTable.setValue(new String[] { "a", "b", "c", "d" });
        htmlDataTable.setVar("fooVar");
        htmlDataTable.setRows(2);
        {
            UIColumn col = new UIColumn();
            col.setRendered(false);
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlDataTable);

        // ## Assert ##
        assertEquals("<tbody>" + "<tr><td>a</td></tr>" + "<tr><td>b</td></tr>"
                + "</tbody>", getResponseText());
    }

    public void testEncodeChildren_RowAndColumnStyle() throws Exception {
        htmlDataTable.setValue(new String[] { "a", "b", "c", "d", "e" });
        htmlDataTable.setVar("fooVar");
        htmlDataTable.setColumnClasses("c1, c2, c3");
        htmlDataTable.setRowClasses("r1, r2");
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            htmlOutputText.setValue("Z");
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            htmlOutputText.setValue("Y");
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            htmlOutputText.setValue("X");
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{fooVar}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);
            htmlDataTable.getChildren().add(col);
        }

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeChildren(context, htmlDataTable);

        // ## Assert ##
        final String readText = TestUtil.readText(getClass(),
                "testEncodeChildren_RowAndColumnStyle.html", "UTF-8");
        final String expected = extract(readText);
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        // attributes
        {
            htmlDataTable.setBgcolor("gray");
            htmlDataTable.setBorder(3);
            htmlDataTable.setCellpadding("c");
            htmlDataTable.setCellspacing("d");
            htmlDataTable.setColumnClasses("e1, e2");
            htmlDataTable.setDir("f");
            htmlDataTable.setFooterClass("g");
            htmlDataTable.setFrame("h");
            htmlDataTable.setHeaderClass("i");
            htmlDataTable.setLang("j");
            htmlDataTable.setOnclick("k");
            htmlDataTable.setOndblclick("l");
            htmlDataTable.setOnkeydown("m");
            htmlDataTable.setOnkeypress("n");
            htmlDataTable.setOnkeyup("o");
            htmlDataTable.setOnmousedown("p");
            htmlDataTable.setOnmousemove("q");
            htmlDataTable.setOnmouseout("r");
            htmlDataTable.setOnmouseover("s");
            htmlDataTable.setOnmouseup("t");
            htmlDataTable.setRowClasses("u1, u2");
            htmlDataTable.setRules("v");
            htmlDataTable.setStyle("w");
            htmlDataTable.setStyleClass("x");
            htmlDataTable.setSummary("y");
            htmlDataTable.setTitle("z");
            htmlDataTable.setWidth("1");
        }
        // table header, footer
        {
            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(textRenderer);
            header.setValue("tableHeader");
            htmlDataTable.setHeader(header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(textRenderer);
            footer.setValue("tableFooter");
            htmlDataTable.setFooter(footer);
        }
        htmlDataTable.setFirst(4);
        htmlDataTable.setRows(3);
        htmlDataTable.setVar("barVar");
        htmlDataTable.setId("A");
        {
            Map m0 = new HashMap();
            Map m1 = new HashMap();
            Map m2 = new HashMap();
            Map m3 = new HashMap();
            Map m4 = new HashMap();
            Map m5 = new HashMap();
            Map m6 = new HashMap();
            Map m7 = new HashMap();
            Map m8 = new HashMap();
            Map m9 = new HashMap();
            m0.put("k1", "a0");
            m1.put("k1", "a1");
            m2.put("k1", "a2");
            m3.put("k1", "a3");
            m4.put("k1", "a4");
            m5.put("k1", "a5");
            m6.put("k1", "a6");
            m7.put("k1", "a7");
            m8.put("k1", "a8");
            m9.put("k1", "a9");
            m0.put("k2", "b0");
            m1.put("k2", "b1");
            m2.put("k2", "b2");
            m3.put("k2", "b3");
            m4.put("k2", "b4");
            m5.put("k2", "b5");
            m6.put("k2", "b6");
            m7.put("k2", "b7");
            m8.put("k2", "b8");
            m9.put("k2", "b9");
            htmlDataTable.setValue(new Map[] { m0, m1, m2, m3, m4, m5, m6, m7,
                    m8, m9 });
        }
        // columns
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{barVar.k1}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);

            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(textRenderer);
            header.setValue("col1Header");
            col.setHeader(header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(textRenderer);
            footer.setValue("col1Footer");
            col.setFooter(footer);

            htmlDataTable.getChildren().add(col);
        }
        {
            UIColumn col = new UIColumn();
            MockHtmlOutputText htmlOutputText = new MockHtmlOutputText();
            htmlOutputText.setRenderer(textRenderer);
            ELParser parser = new CommonsELParser();
            parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
            ValueBinding vb = new ValueBindingImpl(getFacesContext()
                    .getApplication(), "#{barVar.k2}", parser);
            htmlOutputText.setValueBinding("value", vb);
            col.getChildren().add(htmlOutputText);

            MockHtmlOutputText header = new MockHtmlOutputText();
            header.setRenderer(textRenderer);
            header.setValue("col2Header");
            col.setHeader(header);

            MockHtmlOutputText footer = new MockHtmlOutputText();
            footer.setRenderer(textRenderer);
            footer.setValue("col2Footer");
            col.setFooter(footer);

            htmlDataTable.getChildren().add(col);
        }

        encodeByRenderer(renderer, htmlDataTable);

        final String readText = TestUtil.readText(getClass(),
                "testEncode_WithAllAttributes.html", "UTF-8");
        final String expected = extract(readText);
        final String responseText = getResponseText();
        System.out.println(responseText);
        Diff diff = diff(expected, responseText);
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(true, renderer.getRendersChildren());
    }

    private HtmlDataTableRenderer createHtmlDataTableRenderer() {
        return (HtmlDataTableRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlDataTableRenderer renderer = new HtmlDataTableRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
