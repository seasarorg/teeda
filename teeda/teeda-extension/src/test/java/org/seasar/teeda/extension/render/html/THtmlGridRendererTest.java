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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockHtmlOutputText;
import org.seasar.teeda.core.render.html.HtmlOutputTextRenderer;
import org.seasar.teeda.core.unit.TestUtil;
import org.seasar.teeda.extension.component.html.THtmlGrid;
import org.seasar.teeda.extension.component.html.THtmlGridBody;
import org.seasar.teeda.extension.component.html.THtmlGridColumn;
import org.seasar.teeda.extension.component.html.THtmlGridColumnGroup;
import org.seasar.teeda.extension.component.html.THtmlGridHeader;
import org.seasar.teeda.extension.component.html.THtmlGridInputText;
import org.seasar.teeda.extension.component.html.THtmlGridTd;
import org.seasar.teeda.extension.component.html.THtmlGridTh;
import org.seasar.teeda.extension.component.html.THtmlGridTr;
import org.seasar.teeda.extension.render.html.THtmlGridRendererTest.FooPage.FooItem;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public class THtmlGridRendererTest extends RendererTest {

    // TODO making

    private THtmlGridRenderer renderer;

    private MockHtmlGrid htmlGrid;

    private HtmlOutputTextRenderer outputTextRenderer;

    private THtmlGridInputTextRenderer gridInputTextRenderer;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlGridRenderer) createRenderer();
        outputTextRenderer = new HtmlOutputTextRenderer();
        outputTextRenderer.setRenderAttributes(getRenderAttributes());
        gridInputTextRenderer = new THtmlGridInputTextRenderer();
        gridInputTextRenderer.setRenderAttributes(getRenderAttributes());
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
        final String responseText = getResponseText();
        final String afterText = afterText(responseText, "</script>");
        assertEquals(
                "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"></table>",
                afterText);
    }

    private String afterText(final String s1, String s2) {
        final int pos = s1.indexOf(s2);
        if (pos == -1) {
            return s1;
        }
        return s1.substring(pos + s2.length()).trim();
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
        htmlGrid.setItemsName("someItems");

        FooPage fooPage = new FooPage();
        {
            final String pageName = "fooPage";
            getVariableResolver().putValue(pageName, fooPage);
            htmlGrid.setPageName(pageName);
        }

        // items
        {
            FooItem item = new FooItem();
            item.setTd1("td1");
            item.setTd2("td2");
            fooPage.setSomeItems(new FooItem[] { item });
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
                        .getApplication(), "#{fooPage.td1}", parser);
                text.setValueBinding("value", vb);
                addChild(td, text);
            }
            {
                THtmlGridTd td = new THtmlGridTd();
                addChild(tr, td);
                MockHtmlOutputText text = new MockHtmlOutputText();
                text.setRenderer(outputTextRenderer);
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{fooPage.td2}", parser);
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
        htmlGrid.setItemsName("someItems");

        FooPage fooPage = new FooPage();
        {
            final String pageName = "fooPage";
            getVariableResolver().putValue(pageName, fooPage);
            htmlGrid.setPageName(pageName);
        }

        // items
        {
            final List items = new ArrayList();
            for (int i = 1; i <= 7; i++) {
                FooItem fooItem = new FooItem();
                fooItem.setTd1("TD1_" + i);
                fooItem.setTd2("TD2_" + i);
                fooItem.setTd3("TD3_" + i);
                fooItem.setTd4("TD4_" + i);
                fooItem.setTd5("TD5_" + i);
                fooItem.setTd6("TD6_" + i);
                //                final Map m = new HashMap();
                //                for (int j = 1; j <= 6; j++) {
                //                    m.put("td" + j, "TD" + j + "_" + i);
                //                }
                //                items.add(m);
                items.add(fooItem);
            }
            fooPage.setSomeItems((FooItem[]) items.toArray(new FooItem[items
                    .size()]));
            //htmlGrid.setValue(items);
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
            {
                ValueBinding vb = new ValueBindingImpl(getFacesContext()
                        .getApplication(), "#{fooPage.styleClass}", parser);
                tr.setValueBinding("styleClass", vb);
                fooPage.addStyleClass("row_odd");
                fooPage.addStyleClass("row_even");
            }
            for (int i = 1; i <= 6; i++) {
                THtmlGridTd td = new THtmlGridTd();
                addChild(tr, td);
                if (i == 3) {
                    MockHtmlGridInputText gridInputText = new MockHtmlGridInputText();
                    gridInputText.setId("gridText");
                    gridInputText.setRenderer(gridInputTextRenderer);
                    ValueBinding vb = new ValueBindingImpl(getFacesContext()
                            .getApplication(), "#{fooPage.td" + i + "}", parser);
                    gridInputText.setValueBinding("value", vb);
                    addChild(td, gridInputText);
                } else {
                    MockHtmlOutputText text = new MockHtmlOutputText();
                    text.setRenderer(outputTextRenderer);
                    ValueBinding vb = new ValueBindingImpl(getFacesContext()
                            .getApplication(), "#{fooPage.td" + i + "}", parser);
                    text.setValueBinding("value", vb);
                    addChild(td, text);
                }
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

    protected Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        return super.diff("<dummy>" + expected + "</dummy>", "<dummy>" + actual
                + "</dummy>");
    }

    public static class MockHtmlGrid extends THtmlGrid {

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

    public static class MockHtmlGridInputText extends THtmlGridInputText {

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

    public static class FooPage {

        private int someIndex;

        private FooItem[] someItems;

        public FooItem[] getSomeItems() {
            return someItems;
        }

        public void setSomeItems(FooItem[] fooItems) {
            this.someItems = fooItems;
        }

        public String getRowStyleClass() {
            return null;
        }

        public static class FooItem {

            private String td1;

            private String td2;

            private String td3;

            private String td4;

            private String td5;

            private String td6;

            public String getTd1() {
                return td1;
            }

            public void setTd1(String td1) {
                this.td1 = td1;
            }

            public String getTd2() {
                return td2;
            }

            public void setTd2(String td2) {
                this.td2 = td2;
            }

            public String getTd3() {
                return td3;
            }

            public void setTd3(String td3) {
                this.td3 = td3;
            }

            public String getTd4() {
                return td4;
            }

            public void setTd4(String td4) {
                this.td4 = td4;
            }

            public String getTd5() {
                return td5;
            }

            public void setTd5(String td5) {
                this.td5 = td5;
            }

            public String getTd6() {
                return td6;
            }

            public void setTd6(String td6) {
                this.td6 = td6;
            }
        }

        public int getSomeIndex() {
            return someIndex;
        }

        public void setSomeIndex(int someIndex) {
            this.someIndex = someIndex;
        }

        public String getStyleClass() {
            if (styleClasses.isEmpty()) {
                return null;
            }
            if (styleClasses.size() <= index) {
                index = 0;
            }
            return (String) styleClasses.get(getSomeIndex() % 2);
        }

        private List styleClasses = new ArrayList();

        private int index;

        public void addStyleClass(String styleClass) {
            styleClasses.add(styleClass);
        }

        private String td1;

        private String td2;

        private String td3;

        private String td4;

        private String td5;

        private String td6;

        public String getTd1() {
            return td1;
        }

        public void setTd1(String td1) {
            this.td1 = td1;
        }

        public String getTd2() {
            return td2;
        }

        public void setTd2(String td2) {
            this.td2 = td2;
        }

        public String getTd3() {
            return td3;
        }

        public void setTd3(String td3) {
            this.td3 = td3;
        }

        public String getTd4() {
            return td4;
        }

        public void setTd4(String td4) {
            this.td4 = td4;
        }

        public String getTd5() {
            return td5;
        }

        public void setTd5(String td5) {
            this.td5 = td5;
        }

        public String getTd6() {
            return td6;
        }

        public void setTd6(String td6) {
            this.td6 = td6;
        }
    }

    private boolean addChild(UIComponent parent, UIComponent child) {
        return parent.getChildren().add(child);
    }

}
