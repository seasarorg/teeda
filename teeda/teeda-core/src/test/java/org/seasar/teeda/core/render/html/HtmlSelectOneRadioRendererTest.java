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

    private HtmlSelectOneRadioRenderer renderer_;

    private MockHtmlSelectOneRadio htmlSelectOneRadio_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlSelectOneRadioRenderer();
        htmlSelectOneRadio_ = new MockHtmlSelectOneRadio();
        htmlSelectOneRadio_.setRenderer(renderer_);
    }

    public void testEncode_NoChild() throws Exception {
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio_.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        htmlSelectOneRadio_.setId("a");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"a\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                + "l1</label></td>" + "<td><label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children_PageDirection() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        htmlSelectOneRadio_.setLayout("pageDirection");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals("<table>" + "<tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v1\" />"
                + "l1</label>" + "</td></tr>" + "<tr><td>" + "<label>"
                + "<input type=\"radio\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr>" + "</table>", getResponseText());
    }

    public void testEncode_Checked() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio_.setValue("v2");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        htmlSelectOneRadio_.setDisabled(true);
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        htmlSelectOneRadio_.setEnabledClass("ec");
        htmlSelectOneRadio_.setDisabledClass("dc");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        MockFacesContext context = getFacesContext();
        {
            UISelectItems selectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            selectItems.setValue(new SelectItem[] { item1, item2 });
            htmlSelectOneRadio_.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        MockFacesContext context = getFacesContext();
        {
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            SelectItemGroup group = new SelectItemGroup("gl");
            group.setSelectItems(new SelectItem[] { item1, item2 });
            UISelectItem selectItem = new UISelectItem();
            selectItem.setValue(group);
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        htmlSelectOneRadio_.setAccesskey("a");
        htmlSelectOneRadio_.setBorder(3);
        htmlSelectOneRadio_.setDir("b");
        htmlSelectOneRadio_.setDisabled(true);
        htmlSelectOneRadio_.setDisabledClass("d");
        htmlSelectOneRadio_.setEnabledClass("e");
        htmlSelectOneRadio_.setLang("f");
        htmlSelectOneRadio_.setOnblur("g");
        htmlSelectOneRadio_.setOnchange("h");
        htmlSelectOneRadio_.setOnclick("i");
        htmlSelectOneRadio_.setOndblclick("j");
        htmlSelectOneRadio_.setOnfocus("k");
        htmlSelectOneRadio_.setOnkeydown("l");
        htmlSelectOneRadio_.setOnkeypress("m");
        htmlSelectOneRadio_.setOnkeyup("n");
        htmlSelectOneRadio_.setOnmousedown("o");
        htmlSelectOneRadio_.setOnmousemove("p");
        htmlSelectOneRadio_.setOnmouseout("q");
        htmlSelectOneRadio_.setOnmouseover("r");
        htmlSelectOneRadio_.setOnmouseup("s");
        htmlSelectOneRadio_.setOnselect("t");
        htmlSelectOneRadio_.setReadonly(true);
        htmlSelectOneRadio_.setStyle("w");
        htmlSelectOneRadio_.setStyleClass("u");
        htmlSelectOneRadio_.setTabindex("x");
        htmlSelectOneRadio_.setTitle("y");

        htmlSelectOneRadio_.setId("A");
        htmlSelectOneRadio_.setValue("val");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneRadio_.getChildren().add(selectItem);
        }
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlSelectOneRadio_);

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
        htmlSelectOneRadio_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals(0, htmlSelectOneRadio_.getSetSubmittedValueCalls());
        assertEquals(null, htmlSelectOneRadio_.getSubmittedValue());
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectOneRadio_.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        MockHttpServletRequest mockHttpServletRequest = context
                .getMockExternalContext().getMockHttpServletRequest();
        mockHttpServletRequest.addParameter("keyA", "a");

        // ## Act ##
        renderer_.decode(context, htmlSelectOneRadio_);

        // ## Assert ##
        assertEquals(1, htmlSelectOneRadio_.getSetSubmittedValueCalls());
        assertEquals("a", htmlSelectOneRadio_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlSelectOneRadioRenderer createHtmlSelectOneRadioRenderer() {
        return (HtmlSelectOneRadioRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlSelectOneRadioRenderer();
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
