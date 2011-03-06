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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;

import org.seasar.teeda.core.el.SimpleMethodBinding;
import org.seasar.teeda.core.exception.NoValueReferenceRuntimeException;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class CommandButtonTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        CommandButtonTag tag = new CommandButtonTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlCommandButton", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        CommandButtonTag tag = new CommandButtonTag();

        // # Act & Assert #
        assertEquals("javax.faces.Button", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlCommandButton command = createHtmlCommandButton();
        CommandButtonTag tag = new CommandButtonTag();

        tag.setImmediate("false");
        tag.setImage("image");
        tag.setValue("value 1");
        tag.setAccesskey("access key");
        tag.setAlt("alt");
        tag.setDir("dir");
        tag.setDisabled("false");
        tag.setLang("lang");
        tag.setOnblur("onblur");
        tag.setOnchange("onchange");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnfocus("onfocus");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeyup("onkeyup");
        tag.setOnmousedown("onmousedown");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseup("onmouseup");
        tag.setOnselect("onselect");
        tag.setReadonly("false");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTabindex("5");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(command);

        // # Assert #
        assertFalse(command.isImmediate());
        assertEquals("image", command.getImage());
        assertEquals("value 1", command.getValue());
        assertEquals("access key", command.getAccesskey());
        assertEquals("alt", command.getAlt());
        assertEquals("dir", command.getDir());
        assertFalse(command.isDisabled());
        assertEquals("lang", command.getLang());
        assertEquals("onblur", command.getOnblur());
        assertEquals("onchange", command.getOnchange());
        assertEquals("onclick", command.getOnclick());
        assertEquals("ondblclick", command.getOndblclick());
        assertEquals("onfocus", command.getOnfocus());
        assertEquals("onkeydown", command.getOnkeydown());
        assertEquals("onkeypress", command.getOnkeypress());
        assertEquals("onkeyup", command.getOnkeyup());
        assertEquals("onmousedown", command.getOnmousedown());
        assertEquals("onmousemove", command.getOnmousemove());
        assertEquals("onmouseup", command.getOnmouseup());
        assertEquals("onselect", command.getOnselect());
        assertFalse(command.isReadonly());
        assertEquals("style", command.getStyle());
        assertEquals("styleclass", command.getStyleClass());
        assertEquals("5", command.getTabindex());
        assertEquals("title", command.getTitle());
    }

    public void testSetAction_constantValue() throws Exception {
        // # Arrange #
        HtmlCommandButton command = createHtmlCommandButton();
        CommandButtonTag tag = new CommandButtonTag();

        // # Act #
        tag.setAction("testAction");
        tag.setProperties(command);

        // # Asert #
        assertTrue(command.getAction() instanceof SimpleMethodBinding);
    }

    public void testSetAction_bindingValue() throws Exception {
        // # Arrange #
        HtmlCommandButton command = createHtmlCommandButton();
        CommandButtonTag tag = new CommandButtonTag();
        //MockMethodBinding mb = new MockMethodBinding("#{hoge.test}");

        // # Act #
        tag.setAction("#{hoge.test}");
        tag.setProperties(command);

        // # Asert #
        assertTrue(command.getAction() instanceof MockMethodBinding);
    }

    public void testSetActionListener_noValue() throws Exception {
        // # Arrange #
        HtmlCommandButton command = createHtmlCommandButton();
        CommandButtonTag tag = new CommandButtonTag();

        // # Act & Assert#
        tag.setActionListener("testActionListener");
        try {
            tag.setProperties(command);
            fail();
        } catch (NoValueReferenceRuntimeException e) {
            success();
        }
    }

    public void testSetActionListener_bindingValue() throws Exception {
        // # Arrange #
        HtmlCommandButton command = createHtmlCommandButton();
        CommandButtonTag tag = new CommandButtonTag();

        // # Act & Assert#
        tag.setActionListener("#{hoge.do}");
        tag.setProperties(command);

        assertTrue(command.getActionListener() instanceof MockMethodBinding);
    }

    public void testRelease() throws Exception {
        // # Arrange #
        CommandButtonTag tag = new CommandButtonTag();
        tag.setImmediate("false");
        tag.setActionListener("testActionListener");
        tag.setImage("image");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getImmediate());
        assertEquals(null, tag.getImage());
        assertEquals(null, tag.getActionListener());
    }

    private HtmlCommandButton createHtmlCommandButton() {
        return (HtmlCommandButton) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlCommandButton();
    }
}
