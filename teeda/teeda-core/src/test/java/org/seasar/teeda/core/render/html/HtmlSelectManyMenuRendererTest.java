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
import javax.faces.component.html.HtmlSelectManyMenu;
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
public class HtmlSelectManyMenuRendererTest extends RendererTest {

    private HtmlSelectManyMenuRenderer renderer_;

    private MockHtmlSelectManyMenu htmlSelectManyMenu_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlSelectManyMenuRenderer();
        htmlSelectManyMenu_ = new MockHtmlSelectManyMenu();
        htmlSelectManyMenu_.setRenderer(renderer_);
    }

    public void testEncode_NoChild() throws Exception {
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu_.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        htmlSelectManyMenu_.setId("a");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        htmlSelectManyMenu_.setId("a");
        htmlSelectManyMenu_.getAttributes().put("a1", "b1");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\" a1=\"b1\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        htmlSelectManyMenu_.setId("a");
        htmlSelectManyMenu_.getAttributes().put("a.1", "b1");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Selected() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu_.setSelectedValues(new String[] { "v2" });
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" selected=\"selected\">l2</option>"
                + "</select>", getResponseText());
    }

    public void testEncode_ItemDisabled() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu_.setDisabled(true);
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"1\" disabled=\"disabled\">"
                        + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                        + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu_.setEnabledClass("ec");
        htmlSelectManyMenu_.setDisabledClass("dc");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                        + "<option value=\"v1\" class=\"ec\">l1</option>"
                        + "<option value=\"v2\" class=\"dc\" disabled=\"disabled\">l2</option>"
                        + "</select>", getResponseText());
    }

    public void testEncode_GroupChildren() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItems selectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            selectItems.setValue(new SelectItem[] { item1, item2 });
            htmlSelectManyMenu_.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</select>", getResponseText());
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
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectManyMenu_.setAccesskey("a");
        htmlSelectManyMenu_.setDir("b");
        htmlSelectManyMenu_.setDisabled(true);
        htmlSelectManyMenu_.setDisabledClass("d");
        htmlSelectManyMenu_.setEnabledClass("e");
        htmlSelectManyMenu_.setLang("f");
        htmlSelectManyMenu_.setOnblur("g");
        htmlSelectManyMenu_.setOnchange("h");
        htmlSelectManyMenu_.setOnclick("i");
        htmlSelectManyMenu_.setOndblclick("j");
        htmlSelectManyMenu_.setOnfocus("k");
        htmlSelectManyMenu_.setOnkeydown("l");
        htmlSelectManyMenu_.setOnkeypress("m");
        htmlSelectManyMenu_.setOnkeyup("n");
        htmlSelectManyMenu_.setOnmousedown("o");
        htmlSelectManyMenu_.setOnmousemove("p");
        htmlSelectManyMenu_.setOnmouseout("q");
        htmlSelectManyMenu_.setOnmouseover("r");
        htmlSelectManyMenu_.setOnmouseup("s");
        htmlSelectManyMenu_.setOnselect("t");
        htmlSelectManyMenu_.setReadonly(true);
        htmlSelectManyMenu_.setStyle("w");
        htmlSelectManyMenu_.setStyleClass("u");
        htmlSelectManyMenu_.setTabindex("x");
        htmlSelectManyMenu_.setTitle("y");

        htmlSelectManyMenu_.setId("A");
        htmlSelectManyMenu_.setValue(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu_.getChildren().add(selectItem);
        }
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlSelectManyMenu_);

        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " multiple=\"multiple\" size=\"1\" style=\"w\""
                + " class=\"u\"" + " accesskey=\"a\"" + " dir=\"b\""
                + " disabled=\"disabled\"" + " lang=\"f\"" + " onblur=\"g\""
                + " onchange=\"h\"" + " onclick=\"i\"" + " ondblclick=\"j\""
                + " onfocus=\"k\"" + " onkeydown=\"l\"" + " onkeypress=\"m\""
                + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"true\"" + " tabindex=\"x\"" + " title=\"y\""
                + ">"
                + "<option value=\"val\" class=\"d\" selected=\"selected\">"
                + "lab</option></select>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(1, htmlSelectManyMenu_.getSetSubmittedValueCalls());
        String[] submittedValue = (String[]) htmlSelectManyMenu_
                .getSubmittedValue();
        assertEquals(0, submittedValue.length);
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu_.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterValuesMap().put("keyA",
                new String[] { "a", "b", "c" });

        // ## Act ##
        renderer_.decode(context, htmlSelectManyMenu_);

        // ## Assert ##
        assertEquals(1, htmlSelectManyMenu_.getSetSubmittedValueCalls());
        ArrayAssert.assertEquivalenceArrays(new String[] { "a", "b", "c" },
                (Object[]) htmlSelectManyMenu_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlSelectManyMenuRenderer createHtmlSelectManyMenuRenderer() {
        return (HtmlSelectManyMenuRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectManyMenuRenderer renderer = new HtmlSelectManyMenuRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlSelectManyMenu extends HtmlSelectManyMenu {
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
