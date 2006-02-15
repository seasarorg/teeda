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
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import junitx.framework.ArrayAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlSelectManyCheckboxRendererTest extends RendererTest {

    private HtmlSelectManyCheckboxRenderer renderer_;

    private MockHtmlSelectManyCheckbox htmlSelectManyCheckbox_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlSelectManyCheckboxRenderer();
        htmlSelectManyCheckbox_ = new MockHtmlSelectManyCheckbox();
        htmlSelectManyCheckbox_.setRenderer(renderer_);
    }

    public void testEncode_NoChild() throws Exception {
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox_.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

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
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        htmlSelectManyCheckbox_.setId("a");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"a\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label></td>" + "<td><label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children_PageDirection() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        htmlSelectManyCheckbox_.setLayout("pageDirection");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("<table>" + "<tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label>" + "</td></tr>" + "<tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr>" + "</table>", getResponseText());
    }

    public void testEncode_Checked() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox_.setSelectedValues(new String[] { "val" });
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"val\" checked=\"checked\" />"
                        + "lab</label></td>"
                        + "<td><label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
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
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" disabled=\"disabled\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                        + "l2</label>" + "</td></tr>" + "</table>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox_.setDisabled(true);
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" disabled=\"disabled\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr>" + "</table>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox_.setEnabledClass("ec");
        htmlSelectManyCheckbox_.setDisabledClass("dc");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals(
                "<table>"
                        + "<tr><td>"
                        + "<label class=\"ec\">"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label class=\"dc\">"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr>" + "</table>",
                getResponseText());
    }

    public void testEncode_GroupChildren() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItems selectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2");
            selectItems.setValue(new SelectItem[] { item1, item2 });
            htmlSelectManyCheckbox_.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label></td>" + "<td><label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_NestedChildren() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2");
            SelectItemGroup group = new SelectItemGroup("gl");
            group.setSelectItems(new SelectItem[] { item1, item2 });
            UISelectItem selectItem = new UISelectItem();
            selectItem.setValue(group);
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<table><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label></td>" + "<td><label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr></table>" + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectManyCheckbox_.setAccesskey("a");
        htmlSelectManyCheckbox_.setBorder(3);
        htmlSelectManyCheckbox_.setDir("b");
        htmlSelectManyCheckbox_.setDisabled(true);
        htmlSelectManyCheckbox_.setDisabledClass("d");
        htmlSelectManyCheckbox_.setEnabledClass("e");
        htmlSelectManyCheckbox_.setLang("f");
        htmlSelectManyCheckbox_.setOnblur("g");
        htmlSelectManyCheckbox_.setOnchange("h");
        htmlSelectManyCheckbox_.setOnclick("i");
        htmlSelectManyCheckbox_.setOndblclick("j");
        htmlSelectManyCheckbox_.setOnfocus("k");
        htmlSelectManyCheckbox_.setOnkeydown("l");
        htmlSelectManyCheckbox_.setOnkeypress("m");
        htmlSelectManyCheckbox_.setOnkeyup("n");
        htmlSelectManyCheckbox_.setOnmousedown("o");
        htmlSelectManyCheckbox_.setOnmousemove("p");
        htmlSelectManyCheckbox_.setOnmouseout("q");
        htmlSelectManyCheckbox_.setOnmouseover("r");
        htmlSelectManyCheckbox_.setOnmouseup("s");
        htmlSelectManyCheckbox_.setOnselect("t");
        htmlSelectManyCheckbox_.setReadonly(true);
        htmlSelectManyCheckbox_.setStyle("w");
        htmlSelectManyCheckbox_.setStyleClass("u");
        htmlSelectManyCheckbox_.setTabindex("x");
        htmlSelectManyCheckbox_.setTitle("y");

        htmlSelectManyCheckbox_.setId("A");
        htmlSelectManyCheckbox_.setValue(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox_.getChildren().add(selectItem);
        }
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlSelectManyCheckbox_);

        Diff diff = new Diff("<table id=\"A\" border=\"3\" style=\"w\""
                + " class=\"u\"" + ">" + "<tr><td>" + "<label class=\"d\">"
                + "<input type=\"checkbox\" name=\"A\" value=\"val\""
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
        htmlSelectManyCheckbox_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectManyCheckbox_.getSetSubmittedValueCalls());
        String[] submittedValue = (String[]) htmlSelectManyCheckbox_
                .getSubmittedValue();
        assertEquals(0, submittedValue.length);
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox_.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterValuesMap().put("keyA",
                new String[] { "a", "b", "c" });

        // ## Act ##
        renderer_.decode(context, htmlSelectManyCheckbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectManyCheckbox_.getSetSubmittedValueCalls());
        ArrayAssert.assertEquivalenceArrays(new String[] { "a", "b", "c" },
                (Object[]) htmlSelectManyCheckbox_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlSelectManyCheckboxRenderer createHtmlSelectManyCheckboxRenderer() {
        return (HtmlSelectManyCheckboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlSelectManyCheckboxRenderer();
    }

    private static class MockHtmlSelectManyCheckbox extends
            HtmlSelectManyCheckbox {
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
