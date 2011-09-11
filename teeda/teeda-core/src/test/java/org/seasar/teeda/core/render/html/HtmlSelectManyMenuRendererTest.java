/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import junitx.framework.ArrayAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockHtmlSelectManyMenu;

/**
 * @author manhole
 */
public class HtmlSelectManyMenuRendererTest extends RendererTest {

    private HtmlSelectManyMenuRenderer renderer;

    private MockHtmlSelectManyMenu htmlSelectManyMenu;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectManyMenuRenderer();
        htmlSelectManyMenu = new MockHtmlSelectManyMenu();
        htmlSelectManyMenu.setRenderer(renderer);

        // MockHtmlSelectManyMenuのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedValueCalls");
    }

    public void testEncode_NoChild() throws Exception {

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        htmlSelectManyMenu.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        htmlSelectManyMenu.setId("a");
        htmlSelectManyMenu.getAttributes().put("a1", "b1");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\" a1=\"b1\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        htmlSelectManyMenu.setId("a");
        htmlSelectManyMenu.getAttributes().put("a.1", "b1");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Selected() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu.setSelectedValues(new String[] { "v2" });

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
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
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu.setDisabled(true);

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"1\" disabled=\"disabled\">"
                        + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                        + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu.setEnabledClass("ec");
        htmlSelectManyMenu.setDisabledClass("dc");

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
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
            htmlSelectManyMenu.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
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
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectManyMenu.setAccesskey("a");
        htmlSelectManyMenu.setDir("b");
        htmlSelectManyMenu.setDisabled(true);
        htmlSelectManyMenu.setDisabledClass("d");
        htmlSelectManyMenu.setEnabledClass("e");
        htmlSelectManyMenu.setLang("f");
        htmlSelectManyMenu.setOnblur("g");
        htmlSelectManyMenu.setOnchange("h");
        htmlSelectManyMenu.setOnclick("i");
        htmlSelectManyMenu.setOndblclick("j");
        htmlSelectManyMenu.setOnfocus("k");
        htmlSelectManyMenu.setOnkeydown("l");
        htmlSelectManyMenu.setOnkeypress("m");
        htmlSelectManyMenu.setOnkeyup("n");
        htmlSelectManyMenu.setOnmousedown("o");
        htmlSelectManyMenu.setOnmousemove("p");
        htmlSelectManyMenu.setOnmouseout("q");
        htmlSelectManyMenu.setOnmouseover("r");
        htmlSelectManyMenu.setOnmouseup("s");
        htmlSelectManyMenu.setOnselect("t");
        htmlSelectManyMenu.setReadonly(true);
        htmlSelectManyMenu.setStyle("w");
        htmlSelectManyMenu.setStyleClass("u");
        htmlSelectManyMenu.setTabindex("x");
        htmlSelectManyMenu.setTitle("y");
        htmlSelectManyMenu.getAttributes().put("name", "hoge");

        htmlSelectManyMenu.setId("A");
        htmlSelectManyMenu.setValue(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyMenu.getChildren().add(selectItem);
        }

        encodeByRenderer(renderer, htmlSelectManyMenu);

        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " multiple=\"multiple\" size=\"1\" style=\"w\""
                + " class=\"u\"" + " accesskey=\"a\"" + " dir=\"b\""
                + " disabled=\"disabled\"" + " lang=\"f\"" + " onblur=\"g\""
                + " onchange=\"h\"" + " onclick=\"i\"" + " ondblclick=\"j\""
                + " onfocus=\"k\"" + " onkeydown=\"l\"" + " onkeypress=\"m\""
                + " onkeyup=\"n\"" + " onmousedown=\"o\""
                + " onmousemove=\"p\"" + " onmouseout=\"q\""
                + " onmouseover=\"r\"" + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"readonly\"" + " tabindex=\"x\"" + " title=\"y\""
                + ">"
                + "<option value=\"val\" class=\"d\" selected=\"selected\">"
                + "lab</option></select>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu.setClientId("key");

        // ## Act ##
        renderer.decode(getFacesContext(), htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(1, htmlSelectManyMenu.getSetSubmittedValueCalls());
        String[] submittedValue = (String[]) htmlSelectManyMenu
                .getSubmittedValue();
        assertEquals(0, submittedValue.length);
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectManyMenu.setClientId("keyA");

        final FacesContext context = getFacesContext();
        ;
        context.getExternalContext().getRequestParameterValuesMap().put("keyA",
                new String[] { "a", "b", "c" });

        // ## Act ##
        renderer.decode(context, htmlSelectManyMenu);

        // ## Assert ##
        assertEquals(1, htmlSelectManyMenu.getSetSubmittedValueCalls());
        ArrayAssert.assertEquivalenceArrays(new String[] { "a", "b", "c" },
                (Object[]) htmlSelectManyMenu.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlSelectManyMenuRenderer createHtmlSelectManyMenuRenderer() {
        return (HtmlSelectManyMenuRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectManyMenuRenderer renderer = new HtmlSelectManyMenuRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
