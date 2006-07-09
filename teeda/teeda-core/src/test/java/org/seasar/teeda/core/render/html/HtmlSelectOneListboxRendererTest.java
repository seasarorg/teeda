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
import javax.faces.component.html.HtmlSelectOneListbox;
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
public class HtmlSelectOneListboxRendererTest extends RendererTest {

    private HtmlSelectOneListboxRenderer renderer_;

    private MockHtmlSelectOneListbox htmlSelectOneListbox_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlSelectOneListboxRenderer();
        htmlSelectOneListbox_ = new MockHtmlSelectOneListbox();
        htmlSelectOneListbox_.setRenderer(renderer_);
    }

    public void testEncode_NoChild() throws Exception {
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox_.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        FacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        htmlSelectOneListbox_.setId("a");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        htmlSelectOneListbox_.setId("a");
        htmlSelectOneListbox_.getAttributes().put("bb", "cc");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\" bb=\"cc\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        htmlSelectOneListbox_.setId("a");
        htmlSelectOneListbox_.getAttributes().put("b.b", "cc");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        htmlSelectOneListbox_.setSize(6);

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"6\">"
                + "<option value=\"v1\">l1</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Selected() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox_.setValue("v2");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox_.setDisabled(true);
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\" disabled=\"disabled\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox_.setEnabledClass("ec");
        htmlSelectOneListbox_.setDisabledClass("dc");
        MockFacesContext context = getFacesContext();
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"3\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectOneListbox_.setAccesskey("a");
        htmlSelectOneListbox_.setDir("b");
        htmlSelectOneListbox_.setDisabled(true);
        htmlSelectOneListbox_.setDisabledClass("d");
        htmlSelectOneListbox_.setEnabledClass("e");
        htmlSelectOneListbox_.setLang("f");
        htmlSelectOneListbox_.setOnblur("g");
        htmlSelectOneListbox_.setOnchange("h");
        htmlSelectOneListbox_.setOnclick("i");
        htmlSelectOneListbox_.setOndblclick("j");
        htmlSelectOneListbox_.setOnfocus("k");
        htmlSelectOneListbox_.setOnkeydown("l");
        htmlSelectOneListbox_.setOnkeypress("m");
        htmlSelectOneListbox_.setOnkeyup("n");
        htmlSelectOneListbox_.setOnmousedown("o");
        htmlSelectOneListbox_.setOnmousemove("p");
        htmlSelectOneListbox_.setOnmouseout("q");
        htmlSelectOneListbox_.setOnmouseover("r");
        htmlSelectOneListbox_.setOnmouseup("s");
        htmlSelectOneListbox_.setOnselect("t");
        htmlSelectOneListbox_.setReadonly(true);
        htmlSelectOneListbox_.setSize(4);
        htmlSelectOneListbox_.setStyle("w");
        htmlSelectOneListbox_.setStyleClass("u");
        htmlSelectOneListbox_.setTabindex("x");
        htmlSelectOneListbox_.setTitle("y");

        htmlSelectOneListbox_.setId("A");
        htmlSelectOneListbox_.setValue("val");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneListbox_.getChildren().add(selectItem);
        }
        MockFacesContext context = getFacesContext();
        encodeByRenderer(renderer_, context, htmlSelectOneListbox_);

        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " size=\"4\" style=\"w\"" + " class=\"u\""
                + " accesskey=\"a\"" + " dir=\"b\"" + " disabled=\"disabled\""
                + " lang=\"f\"" + " onblur=\"g\"" + " onchange=\"h\""
                + " onclick=\"i\"" + " ondblclick=\"j\"" + " onfocus=\"k\""
                + " onkeydown=\"l\"" + " onkeypress=\"m\"" + " onkeyup=\"n\""
                + " onmousedown=\"o\"" + " onmousemove=\"p\""
                + " onmouseout=\"q\"" + " onmouseover=\"r\""
                + " onmouseup=\"s\"" + " onselect=\"t\"" + " readonly=\"true\""
                + " tabindex=\"x\"" + " title=\"y\"" + ">"
                + "<option value=\"val\" class=\"d\" selected=\"selected\">"
                + "lab</option></select>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox_.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer_.decode(context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals(0, htmlSelectOneListbox_.getSetSubmittedValueCalls());
        assertEquals(null, htmlSelectOneListbox_.getSubmittedValue());
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox_.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        MockHttpServletRequest mockHttpServletRequest = context
                .getMockExternalContext().getMockHttpServletRequest();
        mockHttpServletRequest.addParameter("keyA", new String[] { "a" });

        // ## Act ##
        renderer_.decode(context, htmlSelectOneListbox_);

        // ## Assert ##
        assertEquals(1, htmlSelectOneListbox_.getSetSubmittedValueCalls());
        assertEquals("a", htmlSelectOneListbox_.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    public void testGetConvertedValue() throws Exception {
        assertEquals("hoge", renderer_.getConvertedValue(getFacesContext(),
                htmlSelectOneListbox_, "hoge"));
    }

    private HtmlSelectOneListboxRenderer createHtmlSelectOneListboxRenderer() {
        return (HtmlSelectOneListboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectOneListboxRenderer renderer = new HtmlSelectOneListboxRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlSelectOneListbox extends HtmlSelectOneListbox {
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
