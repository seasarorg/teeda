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

import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlSelectOneRadioRendererTest extends RendererTest {

    private HtmlSelectOneRadioRenderer renderer;

    private MockHtmlSelectOneRadio htmlSelectOneRadio;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectOneRadioRenderer();
        htmlSelectOneRadio = new MockHtmlSelectOneRadio();
        htmlSelectOneRadio.setRenderer(renderer);
    }

    public void testEncode_NoChild() throws Exception {

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        htmlSelectOneRadio.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"a\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        htmlSelectOneRadio.setId("a");
        htmlSelectOneRadio.getAttributes().put("z", "x");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"a\" value=\"val\" z=\"x\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        htmlSelectOneRadio.setId("a");
        htmlSelectOneRadio.getAttributes().put(".", "x");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"a\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                + "l1</label></td>" + "<td><label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children_PageDirection() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        htmlSelectOneRadio.setLayout("pageDirection");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals("<table>" + "<tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                + "l1</label>" + "</td></tr>" + "<tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr>" + "</table>", getResponseText());
    }

    public void testEncode_Checked() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio.setValue("v2");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v2\" checked=\"checked\" />"
                        + "l2</label>" + "</td></tr>" + "</table>",
                getResponseText());
    }

    public void testEncode_ItemDisabled() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v1\" disabled=\"disabled\" />"
                        + "l1</label></td>" + "<td><label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v2\" />"
                        + "l2</label>" + "</td></tr>" + "</table>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio.setDisabled(true);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(
                "<table><tr>"
                        + "<td><label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v1\" disabled=\"disabled\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label></td>" + "</tr></table>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio.setEnabledClass("ec");
        htmlSelectOneRadio.setDisabledClass("dc");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label class=\"ec\">"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label class=\"dc\">"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr>" + "</table>",
                getResponseText());
    }

    public void testEncode_GroupChildren() throws Exception {
        // ## Arrange ##
        {
            UISelectItems selectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            selectItems.setValue(new SelectItem[] { item1, item2 });
            htmlSelectOneRadio.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(
                "<table><tr><td>"
                        + "<label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_Optgroup() throws Exception {
        // ## Arrange ##
        {
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            SelectItemGroup group = new SelectItemGroup("gl");
            group.setSelectItems(new SelectItem[] { item1, item2 });
            UISelectItem selectItem = new UISelectItem();
            selectItem.setValue(group);
            htmlSelectOneRadio.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(
                "<table><tr><td>"
                        + "<table><tr><td>"
                        + "<label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"radio\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr></table>"
                        + "</td></tr></table>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectOneRadio.setAccesskey("a");
        htmlSelectOneRadio.setBorder(3);
        htmlSelectOneRadio.setDir("b");
        htmlSelectOneRadio.setDisabled(true);
        htmlSelectOneRadio.setDisabledClass("d");
        htmlSelectOneRadio.setEnabledClass("e");
        htmlSelectOneRadio.setLang("f");
        htmlSelectOneRadio.setOnblur("g");
        htmlSelectOneRadio.setOnchange("h");
        htmlSelectOneRadio.setOnclick("i");
        htmlSelectOneRadio.setOndblclick("j");
        htmlSelectOneRadio.setOnfocus("k");
        htmlSelectOneRadio.setOnkeydown("l");
        htmlSelectOneRadio.setOnkeypress("m");
        htmlSelectOneRadio.setOnkeyup("n");
        htmlSelectOneRadio.setOnmousedown("o");
        htmlSelectOneRadio.setOnmousemove("p");
        htmlSelectOneRadio.setOnmouseout("q");
        htmlSelectOneRadio.setOnmouseover("r");
        htmlSelectOneRadio.setOnmouseup("s");
        htmlSelectOneRadio.setOnselect("t");
        htmlSelectOneRadio.setReadonly(true);
        htmlSelectOneRadio.setStyle("w");
        htmlSelectOneRadio.setStyleClass("u");
        htmlSelectOneRadio.setTabindex("x");
        htmlSelectOneRadio.setTitle("y");

        htmlSelectOneRadio.setId("A");
        htmlSelectOneRadio.setValue("val");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio.getChildren().add(selectItem);
        }
        encodeByRenderer(renderer, htmlSelectOneRadio);

        Diff diff = new Diff("<table id=\"A\" border=\"3\" style=\"w\""
                + " class=\"u\"" + ">" + "<tr><td>" + "<label class=\"d\">"
                + "<input type=\"radio\" name=\"A\" value=\"val\""
                + " checked=\"checked\"" + " accesskey=\"a\"" + " dir=\"b\""
                + " disabled=\"disabled\"" + " lang=\"f\"" + " onblur=\"g\""
                + " onchange=\"h\"" + " onclick=\"i\"" + " ondblclick=\"j\""
                + " onfocus=\"k\"" + " onkeydown=\"l\"" + " onkeypress=\"m\""
                + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"true\"" + " tabindex=\"x\"" + " title=\"y\""
                + "/>lab</label>" + "</td></tr>" + "</table>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(0, htmlSelectOneRadio.getSetSubmittedValueCalls());
        assertEquals(null, htmlSelectOneRadio.getSubmittedValue());
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        MockHttpServletRequest mockHttpServletRequest = context
                .getMockExternalContext().getMockHttpServletRequest();
        mockHttpServletRequest.addParameter("keyA", "a");

        // ## Act ##
        renderer.decode(context, htmlSelectOneRadio);

        // ## Assert ##
        assertEquals(1, htmlSelectOneRadio.getSetSubmittedValueCalls());
        assertEquals("a", htmlSelectOneRadio.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlSelectOneRadioRenderer createHtmlSelectOneRadioRenderer() {
        return (HtmlSelectOneRadioRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectOneRadioRenderer renderer = new HtmlSelectOneRadioRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setRenderAttributes(getRenderAttributes());
        return renderer;
    }

    private static class MockHtmlSelectOneRadio extends HtmlSelectOneRadio {
        private Renderer renderer_;

        private String clientId_;

        private int setSubmittedValueCalls_;

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

        public void setSubmittedValue(Object submittedValue) {
            setSubmittedValueCalls_++;
            super.setSubmittedValue(submittedValue);
        }

        public int getSetSubmittedValueCalls() {
            return setSubmittedValueCalls_;
        }
    }

}
