/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlSelectOneListbox;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.util.PostbackUtil;

/**
 * @author manhole
 */
public class HtmlSelectOneListboxRendererTest extends RendererTest {

    private HtmlSelectOneListboxRenderer renderer;

    private MockHtmlSelectOneListbox htmlSelectOneListbox;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlSelectOneListboxRenderer();
        htmlSelectOneListbox = new MockHtmlSelectOneListbox();
        htmlSelectOneListbox.setRenderer(renderer);

        // MockHtmlSelectOneListboxのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedValueCalls");
    }

    public void testEncode_NoChild() throws Exception {
        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox.setRendered(false);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_Child() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

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
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        htmlSelectOneListbox.setId("a");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

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
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        htmlSelectOneListbox.setId("a");
        htmlSelectOneListbox.getAttributes().put("bb", "cc");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select id=\"a\" name=\"a\" size=\"1\" bb=\"cc\">"
                + "<option value=\"val\">lab</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        htmlSelectOneListbox.setId("a");
        htmlSelectOneListbox.getAttributes().put("b.b", "cc");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

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
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        htmlSelectOneListbox.setSize(6);

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"6\">"
                + "<option value=\"v1\">l1</option>" + "</select>",
                getResponseText());
    }

    //https://www.seasar.org/issues/TEEDA-153
    public void testEncode_SelectedIfPostbacked() throws Exception {
        // ## Arrange ##
        PostbackUtil.setPostback(getFacesContext().getExternalContext()
                .getRequestMap(), true);
        htmlSelectOneListbox.setValue("v2");
        //htmlSelectOneListbox.setSubmittedValue("v2");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" selected=\"selected\">l2</option>"
                + "</select>", getResponseText());
    }

    public void testEncode_Selected_noValueBinding() throws Exception {
        htmlSelectOneListbox.setValue("2");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"1\">l1</option>"
                + "<option value=\"2\" selected=\"selected\">l2</option>"
                + "</select>", getResponseText());
    }

    public void testEncode_Selected_targetIsPrimitive() throws Exception {
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(2));
        vb.setType(int.class);
        htmlSelectOneListbox.setValue("2");
        htmlSelectOneListbox.setValueBinding("value", vb);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"1\">l1</option>"
                + "<option value=\"2\" selected=\"selected\">l2</option>"
                + "</select>", getResponseText());
    }

    public void testEncode_Selected_primitiveZeroIgnored() throws Exception {
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(0));
        vb.setType(int.class);
        htmlSelectOneListbox.setValue("0");
        htmlSelectOneListbox.setValueBinding("value", vb);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("0");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("1");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"0\">l1</option>"
                + "<option value=\"1\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_SelectedIfInitialized() throws Exception {
        // ## Arrange ##
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "2");
        vb.setType(Integer.class);
        htmlSelectOneListbox.setValue("2");
        htmlSelectOneListbox.setValueBinding("value", vb);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"1\">l1</option>"
                + "<option value=\"2\" selected=\"selected\">l2</option>"
                + "</select>", getResponseText());
    }

    public void testEncode_ItemDisabled() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_Disabled() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox.setDisabled(true);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            selectItem.setItemDisabled(true);
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\" disabled=\"disabled\">"
                + "<option value=\"v1\" disabled=\"disabled\">l1</option>"
                + "<option value=\"v2\">l2</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_LabelClass() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox.setEnabledClass("ec");
        htmlSelectOneListbox.setDisabledClass("dc");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v1");
            selectItem.setItemLabel("l1");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v2");
            selectItem.setItemLabel("l2");
            selectItem.setItemDisabled(true);
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals(
                "<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox.getChildren().add(selectItems);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"2\">"
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
            htmlSelectOneListbox.getChildren().add(selectItem);
        }

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"3\">"
                + "<optgroup label=\"gl\">"
                + "<option value=\"v1\">l1</option>"
                + "<option value=\"v2\" disabled=\"disabled\">l2</option>"
                + "</optgroup>" + "</select>", getResponseText());
    }

    //https://www.seasar.org/issues/TEEDA-153
    public void testEncode_WithAllAttributes() throws Exception {
        PostbackUtil.setPostback(getFacesContext().getExternalContext()
                .getRequestMap(), true);
        htmlSelectOneListbox.setAccesskey("a");
        htmlSelectOneListbox.setDir("b");
        htmlSelectOneListbox.setDisabled(true);
        htmlSelectOneListbox.setDisabledClass("d");
        htmlSelectOneListbox.setEnabledClass("e");
        htmlSelectOneListbox.setLang("f");
        htmlSelectOneListbox.setOnblur("g");
        htmlSelectOneListbox.setOnchange("h");
        htmlSelectOneListbox.setOnclick("i");
        htmlSelectOneListbox.setOndblclick("j");
        htmlSelectOneListbox.setOnfocus("k");
        htmlSelectOneListbox.setOnkeydown("l");
        htmlSelectOneListbox.setOnkeypress("m");
        htmlSelectOneListbox.setOnkeyup("n");
        htmlSelectOneListbox.setOnmousedown("o");
        htmlSelectOneListbox.setOnmousemove("p");
        htmlSelectOneListbox.setOnmouseout("q");
        htmlSelectOneListbox.setOnmouseover("r");
        htmlSelectOneListbox.setOnmouseup("s");
        htmlSelectOneListbox.setOnselect("t");
        htmlSelectOneListbox.setReadonly(true);
        htmlSelectOneListbox.setSize(4);
        htmlSelectOneListbox.setStyle("w");
        htmlSelectOneListbox.setStyleClass("u");
        htmlSelectOneListbox.setTabindex("x");
        htmlSelectOneListbox.setTitle("y");

        htmlSelectOneListbox.setId("A");
        htmlSelectOneListbox.setValue("val");
        htmlSelectOneListbox.setSubmittedValue("val");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("val");
            selectItem.setItemLabel("lab");
            htmlSelectOneListbox.getChildren().add(selectItem);
        }
        encodeByRenderer(renderer, htmlSelectOneListbox);

        System.out.println(getResponseText());
        Diff diff = new Diff("<select id=\"A\"" + " name=\"A\""
                + " size=\"4\" style=\"w\"" + " class=\"u\""
                + " accesskey=\"a\"" + " dir=\"b\"" + " disabled=\"disabled\""
                + " lang=\"f\"" + " onblur=\"g\"" + " onchange=\"h\""
                + " onclick=\"i\"" + " ondblclick=\"j\"" + " onfocus=\"k\""
                + " onkeydown=\"l\"" + " onkeypress=\"m\"" + " onkeyup=\"n\""
                + " onmousedown=\"o\"" + " onmousemove=\"p\""
                + " onmouseout=\"q\"" + " onmouseover=\"r\""
                + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"readonly\"" + " tabindex=\"x\"" + " title=\"y\""
                + ">"
                + "<option value=\"val\" class=\"d\" selected=\"selected\">"
                + "lab</option></select>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_RequestParameterNotExist() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals(0, htmlSelectOneListbox.getSetSubmittedValueCalls());
        assertEquals(null, htmlSelectOneListbox.getSubmittedValue());
    }

    public void testDecodeSuccess() throws Exception {
        // ## Arrange ##
        htmlSelectOneListbox.setClientId("keyA");

        MockFacesContext context = getFacesContext();
        MockHttpServletRequest mockHttpServletRequest = context
                .getMockExternalContext().getMockHttpServletRequest();
        mockHttpServletRequest.addParameter("keyA", new String[] { "a" });

        // ## Act ##
        renderer.decode(context, htmlSelectOneListbox);

        // ## Assert ##
        assertEquals(1, htmlSelectOneListbox.getSetSubmittedValueCalls());
        assertEquals("a", htmlSelectOneListbox.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testGetConvertedValue() throws Exception {
        assertEquals("hoge", renderer.getConvertedValue(getFacesContext(),
                htmlSelectOneListbox, "hoge"));
    }

    private HtmlSelectOneListboxRenderer createHtmlSelectOneListboxRenderer() {
        return (HtmlSelectOneListboxRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlSelectOneListboxRenderer renderer = new HtmlSelectOneListboxRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
