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
package org.seasar.teeda.extension.render.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.render.html.MockHtmlOutputText;
import org.seasar.teeda.core.unit.TestUtil;
import org.seasar.teeda.extension.component.html.THtmlGrid;
import org.seasar.teeda.extension.component.html.THtmlGridBody;
import org.seasar.teeda.extension.component.html.THtmlGridColumn;
import org.seasar.teeda.extension.component.html.THtmlGridColumnGroup;
import org.seasar.teeda.extension.component.html.THtmlGridHeader;
import org.seasar.teeda.extension.component.html.THtmlGridTd;
import org.seasar.teeda.extension.component.html.THtmlGridTh;
import org.seasar.teeda.extension.component.html.THtmlGridTr;

/**
 * @author manhole
 */
public class THtmlGridRendererTest extends RendererTest {

    // TODO making

    private THtmlGridRenderer renderer;

    private MockHtmlGrid htmlGrid;

    private HtmlOutputTextRenderer outputTextRenderer = new HtmlOutputTextRenderer();

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlGridRenderer) createRenderer();
        outputTextRenderer = new HtmlOutputTextRenderer();
        outputTextRenderer.setRenderAttributes(getRenderAttributes());
        htmlGrid = new MockHtmlGrid();
        htmlGrid.setRenderer(renderer);
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlGrid.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        assertEquals(
                "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"></table>",
                getResponseText());
    }

    public void ignore_testEncode_HeaderNoValue() throws Exception {
        // ## Arrange ##
        THtmlGridHeader thead = new THtmlGridHeader();
        htmlGrid.getChildren().add(thead);
        htmlGrid.setId("aa");

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String header = "<table><thead></thead></table>";
        assertEquals("<table id=\"aa\"><tr><td>" + header
                + "</td></tr></table>", getResponseText());
    }

    public void ignore_testEncode_Header() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();

        THtmlGridHeader thead = new THtmlGridHeader();
        htmlGrid.getChildren().add(thead);

        THtmlGridTr tr = new THtmlGridTr();
        addChild(thead, tr);
        {
            THtmlGridTh th = new THtmlGridTh();
            addChild(tr, th);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("th1");
            addChild(th, text);
        }
        {
            THtmlGridTh th = new THtmlGridTh();
            addChild(tr, th);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("th2");
            addChild(th, text);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String header = "<table><thead><tr>" + "<th>th1</th>"
                + "<th>th2</th>" + "</tr></thead></table>";
        assertEquals("<table><tr><td>" + header + "</td></tr></table>",
                getResponseText());
    }

    public void ignore_testEncode_BodyNoValue() throws Exception {
        // ## Arrange ##
        THtmlGridBody tbody = new THtmlGridBody();
        htmlGrid.getChildren().add(tbody);

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String body = "<table><tbody></tbody></table>";
        assertEquals("<table><tr><td>" + body + "</td></tr></table>",
                getResponseText());
    }

    public void ignore_testEncode_Body() throws Exception {
        // ## Arrange ##
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();

        THtmlGridBody tbody = new THtmlGridBody();
        addChild(htmlGrid, tbody);

        THtmlGridTr tr = new THtmlGridTr();
        addChild(tbody, tr);
        {
            THtmlGridTd td = new THtmlGridTd();
            addChild(tr, td);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("td1");
            td.getChildren().add(text);
            addChild(td, text);
        }
        {
            THtmlGridTd td = new THtmlGridTd();
            addChild(tr, td);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("td2");
            addChild(td, text);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String body = "<table><tbody>" + "<tr>" + "<td>td1</td>"
                + "<td>td2</td>" + "</tr>" + "</tbody></table>";
        assertEquals("<table><tr><td>" + body + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_Header_Body_XY() throws Exception {
        // ## Arrange ##
        htmlGrid.setId("someGridXY");
        htmlGrid.setWidth(String.valueOf(300));
        htmlGrid.setHeight(String.valueOf(400));

        // items
        {
            final List someItems = new ArrayList();
            {
                Map m = new HashMap();
                m.put("td1", "td1");
                m.put("td2", "td2");
                someItems.add(m);
            }
            htmlGrid.setValue(someItems);
        }

        // colgroup
        {
            THtmlGridColumnGroup columnGroup = new THtmlGridColumnGroup();
            addChild(htmlGrid, columnGroup);
            {
                THtmlGridColumn col = new THtmlGridColumn();
                col.setSpan(String.valueOf(2));
                col.setWidth(String.valueOf(50));
                addChild(columnGroup, col);
            }
        }

        final int trHeight = 12;
        // thead
        {
            THtmlGridHeader thead = new THtmlGridHeader();
            htmlGrid.getChildren().add(thead);

            THtmlGridTr tr = new THtmlGridTr();
            tr.setHeight(String.valueOf(trHeight));
            addChild(thead, tr);
            {
                THtmlGridTh th = new THtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                text.setValue("th1");
                addChild(th, text);
            }
            {
                THtmlGridTh th = new THtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                text.setValue("th2");
                addChild(th, text);
            }
        }

        // tbody
        final ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        {
            THtmlGridBody tbody = new THtmlGridBody();
            addChild(htmlGrid, tbody);

            THtmlGridTr tr = new THtmlGridTr();
            addChild(tbody, tr);
            {
                THtmlGridTd td = new THtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{some.td1}", parser);
                text.setValueBinding("value", vb);
                addChild(td, text);
            }
            {
                THtmlGridTd td = new THtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{some.td2}", parser);
                text.setValueBinding("value", vb);
                addChild(td, text);
            }
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String readText = TestUtil.readText(getClass(),
                "testEncode_Header_Body_XY.html", "UTF-8");
        final String expected = extract(readText);
        Diff diff = diff(expected, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_LeftFixed_Header_Body_XY() throws Exception {
        // ## Arrange ##
        htmlGrid.setId("someGridXY");
        htmlGrid.setWidth(String.valueOf(170));
        htmlGrid.setHeight(String.valueOf(150));

        // items
        {
            final List items = new ArrayList();
            for (int i = 1; i <= 7; i++) {
                final Map m = new HashMap();
                for (int j = 1; j <= 6; j++) {
                    m.put("td" + j, "TD" + j + "_" + i);
                }
                items.add(m);
            }
            htmlGrid.setValue(items);
        }

        // colgroup
        {
            THtmlGridColumnGroup columnGroup = new THtmlGridColumnGroup();
            addChild(htmlGrid, columnGroup);
            {
                THtmlGridColumn col = new THtmlGridColumn();
                col.setSpan(String.valueOf(2));
                col.setWidth(String.valueOf(40));
                col.setStyleClass("teeda_leftFixed");
                addChild(columnGroup, col);
            }
            {
                THtmlGridColumn col = new THtmlGridColumn();
                col.setSpan(String.valueOf(5));
                col.setWidth(String.valueOf(30));
                addChild(columnGroup, col);
            }
        }

        // thead
        {
            THtmlGridHeader thead = new THtmlGridHeader();
            htmlGrid.getChildren().add(thead);

            THtmlGridTr tr = new THtmlGridTr();
            tr.setHeight(String.valueOf(12));
            addChild(thead, tr);
            for (int i = 1; i <= 6; i++) {
                THtmlGridTh th = new THtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                text.setValue("th" + i);
                addChild(th, text);
            }
        }
        final ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        // tbody
        {
            THtmlGridBody tbody = new THtmlGridBody();
            addChild(htmlGrid, tbody);

            THtmlGridTr tr = new THtmlGridTr();
            addChild(tbody, tr);
            for (int i = 1; i <= 6; i++) {
                THtmlGridTd td = new THtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{some.td" + i + "}", parser);
                text.setValueBinding("value", vb);
                addChild(td, text);
            }
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        final String readText = TestUtil.readText(getClass(),
                "testEncode_LeftFixed_Header_Body_XY.html", "UTF-8");
        final String expected = extract(readText);
        Diff diff = diff(expected, getResponseText());
        System.out.println(getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    protected Renderer createRenderer() {
        THtmlGridRenderer renderer = new THtmlGridRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public class MockHtmlGrid extends THtmlGrid {

        private Renderer renderer;

        private String clientId;

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer != null) {
                return renderer;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId != null) {
                return clientId;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

    }

    private boolean addChild(UIComponent parent, UIComponent child) {
        return parent.getChildren().add(child);
    }

}
