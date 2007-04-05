/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import junitx.framework.ArrayAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockHtmlSelectManyListbox;

/**
 * @author manhole
 */
public class HtmlSelectManyListboxRendererTest extends RendererTest {

    private HtmlSelectManyListboxRenderer renderer;

    private MockHtmlSelectManyListbox htmlSelectManyListbox;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectManyListboxRenderer();
        htmlSelectManyListbox = new MockHtmlSelectManyListbox();
        htmlSelectManyListbox.setRenderer(renderer);

        // MockHtmlSelectManyListboxのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedValueCalls");
    }

    public void testEncode_NoChild() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

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
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        htmlSelectManyListbox.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

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
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        htmlSelectManyListbox.setId("a");
        htmlSelectManyListbox.getAttributes().put("zz", "y");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals(
                "<select id=\"a\" name=\"a\" multiple=\"multiple\" size=\"1\" zz=\"y\">"
                        + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        htmlSelectManyListbox.setId("a");
        htmlSelectManyListbox.getAttributes().put("z.z", "y");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

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
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Size() throws Exception {
        // ## Arrange ##

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        htmlSelectManyListbox.setSize(6);

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"6\">"
                + "<option value=\"v1\">l1</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Selected() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox.setSelectedValues(new String[] { "v2" });

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
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
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox.setDisabled(true);

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"2\" disabled=\"disabled\">"
                        + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                        + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox.setEnabledClass("ec");
        htmlSelectManyListbox.setDisabledClass("dc");

        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
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
            htmlSelectManyListbox.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"2\">"
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
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"3\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectManyListbox.setAccesskey("a");
        htmlSelectManyListbox.setDir("b");
        htmlSelectManyListbox.setDisabled(true);
        htmlSelectManyListbox.setDisabledClass("d");
        htmlSelectManyListbox.setEnabledClass("e");
        htmlSelectManyListbox.setLang("f");
        htmlSelectManyListbox.setOnblur("g");
        htmlSelectManyListbox.setOnchange("h");
        htmlSelectManyListbox.setOnclick("i");
        htmlSelectManyListbox.setOndblclick("j");
        htmlSelectManyListbox.setOnfocus("k");
        htmlSelectManyListbox.setOnkeydown("l");
        htmlSelectManyListbox.setOnkeypress("m");
        htmlSelectManyListbox.setOnkeyup("n");
        htmlSelectManyListbox.setOnmousedown("o");
        htmlSelectManyListbox.setOnmousemove("p");
        htmlSelectManyListbox.setOnmouseout("q");
        htmlSelectManyListbox.setOnmouseover("r");
        htmlSelectManyListbox.setOnmouseup("s");
        htmlSelectManyListbox.setOnselect("t");
        htmlSelectManyListbox.setReadonly(true);
        htmlSelectManyListbox.setSize(4);
        htmlSelectManyListbox.setStyle("w");
        htmlSelectManyListbox.setStyleClass("u");
        htmlSelectManyListbox.setTabindex("x");
        htmlSelectManyListbox.setTitle("y");

        htmlSelectManyListbox.setId("A");
        htmlSelectManyListbox.setValue(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyListbox.getChildren().add(selectItem);
        }

        encodeByRenderer(renderer, htmlSelectManyListbox);

        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " multiple=\"multiple\" size=\"4\" style=\"w\""
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
        htmlSelectManyListbox.setClientId("key");
        final FacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals(1, htmlSelectManyListbox.getSetSubmittedValueCalls());
        String[] submittedValue = (String[]) htmlSelectManyListbox
                .getSubmittedValue();
        assertEquals(0, submittedValue.length);
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox.setClientId("keyA");

        final FacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterValuesMap().put("keyA",
                new String[] { "a", "b", "c" });

        // ## Act ##
        renderer.decode(context, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals(1, htmlSelectManyListbox.getSetSubmittedValueCalls());
        ArrayAssert.assertEquivalenceArrays(new String[] { "a", "b", "c" },
                (Object[]) htmlSelectManyListbox.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testSelectItemValueIsNull() throws Exception {
        // ## Arrange ##
        htmlSelectManyListbox.setRendered(true);
        {
            UISelectItems selectItems = new UISelectItems();
            List list = new ArrayList();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("aaa");
            list.add(selectItem);
            selectItems.setValue(list);
            htmlSelectManyListbox.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" multiple=\"multiple\" size=\"1\">"
                + "<option>aaa</option>" + "</select>", getResponseText());
    }

    private HtmlSelectManyListboxRenderer createHtmlSelectManyListboxRenderer() {
        return (HtmlSelectManyListboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectManyListboxRenderer renderer = new HtmlSelectManyListboxRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
