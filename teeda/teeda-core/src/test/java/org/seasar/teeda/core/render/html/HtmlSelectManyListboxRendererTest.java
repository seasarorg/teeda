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
import javax.faces.component.html.HtmlSelectManyListbox;
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
public class HtmlSelectManyListboxRendererTest extends RendererTest {

    private HtmlSelectManyListboxRenderer renderer_;

    private MockHtmlSelectManyListbox htmlSelectManyListbox_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlSelectManyListboxRenderer();
        htmlSelectManyListbox_ = new MockHtmlSelectManyListbox();
        htmlSelectManyListbox_.setRenderer(renderer_);
    }

    public void testEncode_NoChild() throws Exception {
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox_.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

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
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

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
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        htmlSelectManyListbox_.setId("a");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

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
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Size() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        htmlSelectManyListbox_.setSize(6);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"6\">"
                + "<option value=\"v1\">l1</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Selected() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox_.setSelectedValues(new String[] { "v2" });
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
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
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox_.setDisabled(true);
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"2\" disabled=\"disabled\">"
                        + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                        + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox_.setEnabledClass("ec");
        htmlSelectManyListbox_.setDisabledClass("dc");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
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
            htmlSelectManyListbox_.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
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
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"3\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectManyListbox_.setAccesskey("a");
        htmlSelectManyListbox_.setDir("b");
        htmlSelectManyListbox_.setDisabled(true);
        htmlSelectManyListbox_.setDisabledClass("d");
        htmlSelectManyListbox_.setEnabledClass("e");
        htmlSelectManyListbox_.setLang("f");
        htmlSelectManyListbox_.setOnblur("g");
        htmlSelectManyListbox_.setOnchange("h");
        htmlSelectManyListbox_.setOnclick("i");
        htmlSelectManyListbox_.setOndblclick("j");
        htmlSelectManyListbox_.setOnfocus("k");
        htmlSelectManyListbox_.setOnkeydown("l");
        htmlSelectManyListbox_.setOnkeypress("m");
        htmlSelectManyListbox_.setOnkeyup("n");
        htmlSelectManyListbox_.setOnmousedown("o");
        htmlSelectManyListbox_.setOnmousemove("p");
        htmlSelectManyListbox_.setOnmouseout("q");
        htmlSelectManyListbox_.setOnmouseover("r");
        htmlSelectManyListbox_.setOnmouseup("s");
        htmlSelectManyListbox_.setOnselect("t");
        htmlSelectManyListbox_.setReadonly(true);
        htmlSelectManyListbox_.setSize(4);
        htmlSelectManyListbox_.setStyle("w");
        htmlSelectManyListbox_.setStyleClass("u");
        htmlSelectManyListbox_.setTabindex("x");
        htmlSelectManyListbox_.setTitle("y");

        htmlSelectManyListbox_.setId("A");
        htmlSelectManyListbox_.setValue(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyListbox_.getChildren().add(selectItem);
        }
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlSelectManyListbox_);

        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " multiple=\"multiple\" size=\"4\" style=\"w\""
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
        htmlSelectManyListbox_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectManyListbox_.getSetSubmittedValueCalls());
        String[] submittedValue = (String[]) htmlSelectManyListbox_
                .getSubmittedValue();
        assertEquals(0, submittedValue.length);
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox_.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterValuesMap().put("keyA",
                new String[] { "a", "b", "c" });

        // ## Act ##
        renderer_.decode(context, htmlSelectManyListbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectManyListbox_.getSetSubmittedValueCalls());
        ArrayAssert.assertEquivalenceArrays(new String[] { "a", "b", "c" },
                (Object[]) htmlSelectManyListbox_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlSelectManyListboxRenderer createHtmlSelectManyListboxRenderer() {
        return (HtmlSelectManyListboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectManyListboxRenderer renderer = new HtmlSelectManyListboxRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlSelectManyListbox extends
            HtmlSelectManyListbox {
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
