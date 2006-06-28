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
import java.util.Arrays;
import java.util.Collection;
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
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.render.html.MockHtmlOutputText;
import org.seasar.teeda.core.unit.TestUtil;
import org.seasar.teeda.extension.component.html.HtmlGrid;
import org.seasar.teeda.extension.component.html.HtmlGridBody;
import org.seasar.teeda.extension.component.html.HtmlGridColumn;
import org.seasar.teeda.extension.component.html.HtmlGridColumnGroup;
import org.seasar.teeda.extension.component.html.HtmlGridHeader;
import org.seasar.teeda.extension.component.html.HtmlGridTd;
import org.seasar.teeda.extension.component.html.HtmlGridTh;
import org.seasar.teeda.extension.component.html.HtmlGridTr;

/**
 * @author manhole
 */
public class HtmlGridRendererTest extends RendererTest {

    // TODO making

    private HtmlGridRenderer renderer;

    private MockHtmlGrid htmlGrid;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (HtmlGridRenderer) createRenderer();
        htmlGrid = new MockHtmlGrid();
        htmlGrid.setRenderer(renderer);
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlGrid.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer, context, htmlGrid);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlGrid);

        // ## Assert ##
        assertEquals("<table></table>", getResponseText());
    }

    public void ignore_testEncode_HeaderNoValue() throws Exception {
        // ## Arrange ##
        HtmlGridHeader thead = new HtmlGridHeader();
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

        HtmlGridHeader thead = new HtmlGridHeader();
        htmlGrid.getChildren().add(thead);

        HtmlGridTr tr = new HtmlGridTr();
        addChild(thead, tr);
        {
            HtmlGridTh th = new HtmlGridTh();
            addChild(tr, th);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("th1");
            addChild(th, text);
        }
        {
            HtmlGridTh th = new HtmlGridTh();
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
        HtmlGridBody tbody = new HtmlGridBody();
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

        HtmlGridBody tbody = new HtmlGridBody();
        addChild(htmlGrid, tbody);

        HtmlGridTr tr = new HtmlGridTr();
        addChild(tbody, tr);
        {
            HtmlGridTd td = new HtmlGridTd();
            addChild(tr, td);
            MockHtmlOutputText text = new MockHtmlOutputText();
            text.setRenderer(htmlOutputTextRenderer);
            text.setValue("td1");
            td.getChildren().add(text);
            addChild(td, text);
        }
        {
            HtmlGridTd td = new HtmlGridTd();
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
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
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
            //            getFacesContext().getExternalContext().getRequestMap().put(
            //                    "someItems", someItems);
            htmlGrid.setValue(someItems);
        }

        final int trHeight = 12;
        // thead
        {
            HtmlGridHeader thead = new HtmlGridHeader();
            htmlGrid.getChildren().add(thead);

            HtmlGridTr tr = new HtmlGridTr();
            tr.setHeight(String.valueOf(trHeight));
            addChild(thead, tr);
            {
                HtmlGridTh th = new HtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("th1");
                addChild(th, text);
            }
            {
                HtmlGridTh th = new HtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("th2");
                addChild(th, text);
            }
        }

        // tbody
        final ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        {
            HtmlGridBody tbody = new HtmlGridBody();
            addChild(htmlGrid, tbody);

            HtmlGridTr tr = new HtmlGridTr();
            addChild(tbody, tr);
            {
                HtmlGridTd td = new HtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{some.td1}", parser);
                text.setValueBinding("value", vb);
                addChild(td, text);
            }
            {
                HtmlGridTd td = new HtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
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
        HtmlOutputTextRenderer htmlOutputTextRenderer = new HtmlOutputTextRenderer();
        htmlGrid.setId("someGridXY");
        htmlGrid.setWidth(String.valueOf(300));
        htmlGrid.setHeight(String.valueOf(400));

        // items
        {
            final List someItems = new ArrayList();
            {
                Map m = new HashMap();
                m.put("td1", "TD1_1");
                m.put("td2", "TD2_1");
                someItems.add(m);
            }
            {
                Map m = new HashMap();
                m.put("td1", "TD1_2");
                m.put("td2", "TD2_2");
                someItems.add(m);
            }
            {
                Map m = new HashMap();
                m.put("td1", "TD1_3");
                m.put("td2", "TD2_3");
                someItems.add(m);
            }
            {
                Map m = new HashMap();
                m.put("td1", "TD1_4");
                m.put("td2", "TD2_4");
                someItems.add(m);
            }
            //            getFacesContext().getExternalContext().getRequestMap().put(
            //                    "someItems", someItems);
            htmlGrid.setValue(someItems);
        }

        // colgroup
        {
            HtmlGridColumnGroup columnGroup = new HtmlGridColumnGroup();
            addChild(htmlGrid, columnGroup);

            HtmlGridColumn col = new HtmlGridColumn();
            col.setSpan(String.valueOf(1));
            col.setWidth(String.valueOf(110));
            addChild(columnGroup, col);
        }

        // thead

        {
            HtmlGridHeader thead = new HtmlGridHeader();
            htmlGrid.getChildren().add(thead);

            HtmlGridTr tr = new HtmlGridTr();
            tr.setHeight(String.valueOf(12));
            addChild(thead, tr);
            {
                HtmlGridTh th = new HtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("th1");
                addChild(th, text);
            }
            {
                HtmlGridTh th = new HtmlGridTh();
                addChild(tr, th);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                text.setValue("th2");
                addChild(th, text);
            }
        }
        final ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        // tbody
        {
            HtmlGridBody tbody = new HtmlGridBody();
            addChild(htmlGrid, tbody);

            HtmlGridTr tr = new HtmlGridTr();
            addChild(tbody, tr);
            {
                HtmlGridTd td = new HtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{some.td1}", parser);
                text.setValueBinding("value", vb);
                addChild(td, text);
            }
            {
                HtmlGridTd td = new HtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(htmlOutputTextRenderer);
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
                "testEncode_LeftFixed_Header_Body_XY.html", "UTF-8");
        final String expected = extract(readText);
        Diff diff = diff(expected, getResponseText());
        System.out.println(getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetItems() throws Exception {
        // ## Arrange ##
        htmlGrid.setId("someGridXY");

        final FacesContext facesContext = getFacesContext();
        final List someItems = Arrays.asList(new String[] { "v1", "v2", "v3" });
        htmlGrid.setValue(someItems);

        // ## Act ##
        final Collection items = renderer.getBodyItems(facesContext, htmlGrid);

        // ## Assert ##
        assertEquals(someItems, items);
    }

    public void testEnterRow() throws Exception {
        // ## Arrange ##
        htmlGrid.setId("fooooGrid");
        final FacesContext facesContext = getFacesContext();
        final Object object = new Object();

        // ## Act ##
        renderer.enterRow(facesContext, htmlGrid, object);

        // ## Assert ##
        final Object rowBean = facesContext.getApplication()
                .getVariableResolver().resolveVariable(facesContext, "foooo");
        assertSame(object, rowBean);
    }

    public void testLeaveRow() throws Exception {
        // ## Arrange ##
        htmlGrid.setId("a12345GridX");
        final FacesContext facesContext = getFacesContext();
        final Object object = new Object();
        facesContext.getExternalContext().getRequestMap().put("a12345", object);

        // ## Act ##
        renderer.leaveRow(facesContext, htmlGrid);

        // ## Assert ##
        assertEquals(null, facesContext.getExternalContext().getRequestMap()
                .get("a12345"));
    }

    protected Renderer createRenderer() {
        HtmlGridRenderer renderer = new HtmlGridRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public class MockHtmlGrid extends HtmlGrid {

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
