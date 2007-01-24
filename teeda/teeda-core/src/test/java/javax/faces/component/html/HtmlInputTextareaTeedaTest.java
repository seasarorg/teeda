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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInputTeedaTest;

import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlInputTextareaTeedaTest extends UIInputTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        // ## Arrange ##
        final HtmlInputTextarea component1 = createHtmlInputTextarea();
        component1.setAccesskey("foo accesskey");
        component1.setCols(123);
        component1.setDir("foo dir");
        component1.setDisabled(true);
        component1.setLang("foo lang");
        component1.setOnblur("foo onblur");
        component1.setOnchange("foo onchange");
        component1.setOnclick("foo onclick");
        component1.setOndblclick("foo ondblclick");
        component1.setOnfocus("foo onfocus");
        component1.setOnkeydown("foo onkeydown");
        component1.setOnkeypress("foo onkeypress");
        component1.setOnkeyup("foo onkeyup");
        component1.setOnmousedown("foo onmousedown");
        component1.setOnmousemove("foo onmousemove");
        component1.setOnmouseout("foo onmouseout");
        component1.setOnmouseover("foo onmouseover");
        component1.setOnmouseup("foo onmouseup");
        component1.setOnselect("foo onselect");
        component1.setReadonly(false);
        component1.setRows(22);
        component1.setStyle("foo style");
        component1.setStyleClass("foo styleClass");
        component1.setTabindex("foo tabindex");
        component1.setTitle("foo title");
        component1.setWrap("foo wrap");

        final MockFacesContext context = getFacesContext();

        // ## Act ##
        final Object state = component1.saveState(context);
        final HtmlInputTextarea component2 = createHtmlInputTextarea();
        component2.restoreState(context, serializeAndDeserialize(state));

        // ## Assert ##
        assertEquals(component1.getAccesskey(), component2.getAccesskey());
        assertEquals(component1.getCols(), component2.getCols());
        assertEquals(component1.getDir(), component2.getDir());
        assertEquals(component1.isDisabled(), component2.isDisabled());
        assertEquals(component1.getLang(), component2.getLang());
        assertEquals(component1.getOnblur(), component2.getOnblur());
        assertEquals(component1.getOnchange(), component2.getOnchange());
        assertEquals(component1.getOnclick(), component2.getOnclick());
        assertEquals(component1.getOndblclick(), component2.getOndblclick());
        assertEquals(component1.getOnfocus(), component2.getOnfocus());
        assertEquals(component1.getOnkeydown(), component2.getOnkeydown());
        assertEquals(component1.getOnkeypress(), component2.getOnkeypress());
        assertEquals(component1.getOnkeyup(), component2.getOnkeyup());
        assertEquals(component1.getOnmousedown(), component2.getOnmousedown());
        assertEquals(component1.getOnmousemove(), component2.getOnmousemove());
        assertEquals(component1.getOnmouseout(), component2.getOnmouseout());
        assertEquals(component1.getOnmouseover(), component2.getOnmouseover());
        assertEquals(component1.getOnmouseup(), component2.getOnmouseup());
        assertEquals(component1.getOnselect(), component2.getOnselect());
        assertEquals(component1.isDisabled(), component2.isDisabled());
        assertEquals(component1.getRows(), component2.getRows());
        assertEquals(component1.getStyle(), component2.getStyle());
        assertEquals(component1.getStyleClass(), component2.getStyleClass());
        assertEquals(component1.getTabindex(), component2.getTabindex());
        assertEquals(component1.getTitle(), component2.getTitle());
        assertEquals(component1.getWrap(), component2.getWrap());
    }

    private HtmlInputTextarea createHtmlInputTextarea() {
        return (HtmlInputTextarea) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlInputTextarea();
    }

}
