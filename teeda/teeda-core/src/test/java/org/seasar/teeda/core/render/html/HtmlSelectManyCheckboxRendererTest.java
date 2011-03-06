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

import java.lang.reflect.Array;

import javax.faces.component.UIOutput;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.convert.IntegerConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import junitx.framework.ArrayAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlSelectManyCheckbox;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlSelectManyCheckboxRendererTest extends RendererTest {

    private HtmlSelectManyCheckboxRenderer renderer;

    private MockHtmlSelectManyCheckbox htmlSelectManyCheckbox;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectManyCheckboxRenderer();
        htmlSelectManyCheckbox = new MockHtmlSelectManyCheckbox();
        htmlSelectManyCheckbox.setRenderer(renderer);

        // MockHtmlSelectManyCheckboxのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedValueCalls");
    }

    public void testEncode_NoChild() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Child_noneLayout() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
            htmlSelectManyCheckbox.setLayout("none");
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"val\" />"
                + "lab</label>", getResponseText());
    }

    public void testEncode_Id() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        htmlSelectManyCheckbox.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"a\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        htmlSelectManyCheckbox.setId("a");
        htmlSelectManyCheckbox.getAttributes().put("aa", "bb");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals(
                "<table id=\"a\"><tr><td>"
                        + "<label>"
                        + "<input type=\"checkbox\" name=\"a\" value=\"val\" aa=\"bb\" />"
                        + "lab</label>" + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        htmlSelectManyCheckbox.setId("a");
        htmlSelectManyCheckbox.getAttributes().put("a.a", "bb");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<table id=\"a\"><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"a\" value=\"val\" />"
                + "lab</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<table><tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label></td>" + "<td><label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr></table>", getResponseText());
    }

    public void testEncode_Children_noneLayout() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
            htmlSelectManyCheckbox.setLayout("none");
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label><label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>", getResponseText());
    }

    public void testEncode_Children_PageDirection() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        htmlSelectManyCheckbox.setLayout("pageDirection");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals("<table>" + "<tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                + "l1</label>" + "</td></tr>" + "<tr><td>" + "<label>"
                + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" />"
                + "l2</label>" + "</td></tr>" + "</table>", getResponseText());
    }

    public void testEncode_Checked() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox.setSelectedValues(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

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
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

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
        htmlSelectManyCheckbox.setDisabled(true);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

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
        htmlSelectManyCheckbox.setEnabledClass("ec");
        htmlSelectManyCheckbox.setDisabledClass("dc");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

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
        {
            UISelectItems selectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            selectItems.setValue(new SelectItem[] { item1, item2 });
            htmlSelectManyCheckbox.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals(
                "<table><tr><td>"
                        + "<label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr></table>",
                getResponseText());
    }

    public void testEncode_NestedChildren() throws Exception {
        // ## Arrange ##
        {
            SelectItem item1 = new SelectItem("v1", "l1");
            SelectItem item2 = new SelectItem("v2", "l2", null, true);
            SelectItemGroup group = new SelectItemGroup("gl");
            group.setSelectItems(new SelectItem[] { item1, item2 });
            UISelectItem selectItem = new UISelectItem();
            selectItem.setValue(group);
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals(
                "<table><tr><td>"
                        + "<table><tr><td>"
                        + "<label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v1\" />"
                        + "l1</label></td>"
                        + "<td><label>"
                        + "<input type=\"checkbox\" name=\"_id0\" value=\"v2\" disabled=\"disabled\" />"
                        + "l2</label>" + "</td></tr></table>"
                        + "</td></tr></table>", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlSelectManyCheckbox.setAccesskey("a");
        htmlSelectManyCheckbox.setBorder(3);
        htmlSelectManyCheckbox.setDir("b");
        htmlSelectManyCheckbox.setDisabled(true);
        htmlSelectManyCheckbox.setDisabledClass("d");
        htmlSelectManyCheckbox.setEnabledClass("e");
        htmlSelectManyCheckbox.setLang("f");
        htmlSelectManyCheckbox.setOnblur("g");
        htmlSelectManyCheckbox.setOnchange("h");
        htmlSelectManyCheckbox.setOnclick("i");
        htmlSelectManyCheckbox.setOndblclick("j");
        htmlSelectManyCheckbox.setOnfocus("k");
        htmlSelectManyCheckbox.setOnkeydown("l");
        htmlSelectManyCheckbox.setOnkeypress("m");
        htmlSelectManyCheckbox.setOnkeyup("n");
        htmlSelectManyCheckbox.setOnmousedown("o");
        htmlSelectManyCheckbox.setOnmousemove("p");
        htmlSelectManyCheckbox.setOnmouseout("q");
        htmlSelectManyCheckbox.setOnmouseover("r");
        htmlSelectManyCheckbox.setOnmouseup("s");
        htmlSelectManyCheckbox.setOnselect("t");
        htmlSelectManyCheckbox.setReadonly(true);
        htmlSelectManyCheckbox.setStyle("w");
        htmlSelectManyCheckbox.setStyleClass("u");
        htmlSelectManyCheckbox.setTabindex("x");
        htmlSelectManyCheckbox.setTitle("y");

        htmlSelectManyCheckbox.setId("A");
        htmlSelectManyCheckbox.setValue(new String[] { "val" });
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectManyCheckbox.getChildren().add(selectItem);
        }
        encodeByRenderer(renderer, htmlSelectManyCheckbox);

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
                + " readonly=\"readonly\"" + " tabindex=\"x\"" + " title=\"y\""
                + "/>lab</label>" + "</td></tr>" + "</table>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals(1, htmlSelectManyCheckbox.getSetSubmittedValueCalls());
        String[] submittedValue = (String[]) htmlSelectManyCheckbox
                .getSubmittedValue();
        assertEquals(0, submittedValue.length);
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectManyCheckbox.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterValuesMap().put("keyA",
                new String[] { "a", "b", "c" });

        // ## Act ##
        renderer.decode(context, htmlSelectManyCheckbox);

        // ## Assert ##
        assertEquals(1, htmlSelectManyCheckbox.getSetSubmittedValueCalls());
        ArrayAssert.assertEquivalenceArrays(new String[] { "a", "b", "c" },
                (Object[]) htmlSelectManyCheckbox.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testGetConvertedValue() throws Exception {
        UIOutput out = new UIOutput();
        out.setConverter(new IntegerConverter());
        MockValueBinding vb = new MockValueBinding();
        vb.setType(int[].class);
        out.setValueBinding("value", vb);
        Object submittedValue = Array.newInstance(String.class, 3);
        Array.set(submittedValue, 0, "1");
        Array.set(submittedValue, 1, "2");
        Array.set(submittedValue, 2, "3");
        Object o = renderer.getConvertedValue(getFacesContext(), out,
                submittedValue);
        int[] result = (int[]) o;
        assertEquals(3, result.length);
        ArrayAssert.assertEquals(new int[] { 1, 2, 3 }, result);
    }

    private HtmlSelectManyCheckboxRenderer createHtmlSelectManyCheckboxRenderer() {
        return (HtmlSelectManyCheckboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectManyCheckboxRenderer renderer = new HtmlSelectManyCheckboxRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
