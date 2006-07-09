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
import javax.faces.component.html.HtmlSelectOneMenu;
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
public class HtmlSelectOneMenuRendererTest extends RendererTest {

    private HtmlSelectOneMenuRenderer renderer;

    private MockHtmlSelectOneMenu htmlSelectOneMenu;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectOneMenuRenderer();
        htmlSelectOneMenu = new MockHtmlSelectOneMenu();
        htmlSelectOneMenu.setRenderer(renderer);
    }

    public void testEncode_NoChild() throws Exception {

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\">"
                + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setId("a");
        htmlSelectOneMenu.getAttributes().put("aa", "11");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\" aa=\"11\">"
                + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setId("a");
        htmlSelectOneMenu.getAttributes().put("a.a", "11");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\">"
                + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Selected() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setValue("v2");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" selected=\"selected\">l2</option>"
                + "</select>", getResponseText());
    }

    public void testEncode_ItemDisabled() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setDisabled(true);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\" disabled=\"disabled\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setEnabledClass("ec");
        htmlSelectOneMenu.setDisabledClass("dc");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" size=\"1\">"
                        + "<option value=\"v1\" class=\"ec\">l1</option>"
                        + "<option value=\"v2\" class=\"dc\" disabled=\"disabled\">l2</option>"
                        + "</select>", getResponseText());
    }

    public void testEncode_GroupChildren() throws Exception {
        // ## Arrange ##
        {
            UISelectItems selectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            selectItems.setValue(new SelectItem[] { item1, item2 });
            htmlSelectOneMenu.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</select>", getResponseText());
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
            htmlSelectOneMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectOneMenu.setAccesskey("a");
        htmlSelectOneMenu.setDir("b");
        htmlSelectOneMenu.setDisabled(true);
        htmlSelectOneMenu.setDisabledClass("d");
        htmlSelectOneMenu.setEnabledClass("e");
        htmlSelectOneMenu.setLang("f");
        htmlSelectOneMenu.setOnblur("g");
        htmlSelectOneMenu.setOnchange("h");
        htmlSelectOneMenu.setOnclick("i");
        htmlSelectOneMenu.setOndblclick("j");
        htmlSelectOneMenu.setOnfocus("k");
        htmlSelectOneMenu.setOnkeydown("l");
        htmlSelectOneMenu.setOnkeypress("m");
        htmlSelectOneMenu.setOnkeyup("n");
        htmlSelectOneMenu.setOnmousedown("o");
        htmlSelectOneMenu.setOnmousemove("p");
        htmlSelectOneMenu.setOnmouseout("q");
        htmlSelectOneMenu.setOnmouseover("r");
        htmlSelectOneMenu.setOnmouseup("s");
        htmlSelectOneMenu.setOnselect("t");
        htmlSelectOneMenu.setReadonly(true);
        htmlSelectOneMenu.setStyle("w");
        htmlSelectOneMenu.setStyleClass("u");
        htmlSelectOneMenu.setTabindex("x");
        htmlSelectOneMenu.setTitle("y");

        htmlSelectOneMenu.setId("A");
        htmlSelectOneMenu.setValue("val");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        encodeByRenderer(renderer, htmlSelectOneMenu);

        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " size=\"1\" style=\"w\"" + " class=\"u\""
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
        htmlSelectOneMenu.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals(0, htmlSelectOneMenu.getSetSubmittedValueCalls());
        assertEquals(null, htmlSelectOneMenu.getSubmittedValue());
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        MockHttpServletRequest mockHttpServletRequest = context
                .getMockExternalContext().getMockHttpServletRequest();
        mockHttpServletRequest.addParameter("keyA", new String[] { "a" });

        // ## Act ##
        renderer.decode(context, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals(1, htmlSelectOneMenu.getSetSubmittedValueCalls());
        assertEquals("a", htmlSelectOneMenu.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlSelectOneMenuRenderer createHtmlSelectOneMenuRenderer() {
        return (HtmlSelectOneMenuRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectOneMenuRenderer renderer = new HtmlSelectOneMenuRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlSelectOneMenu extends HtmlSelectOneMenu {
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
