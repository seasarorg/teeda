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
package org.seasar.teeda.extension.render.html;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.extension.component.html.THtmlSelectOneMenu;

/**
 * @author shot
 */
public class THtmlSelectOneMenuRendererTest extends RendererTest {

    private THtmlSelectOneMenuRenderer renderer;

    private MockTHtmlSelectOneMenu htmlSelectOneMenu;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlSelectOneMenuRenderer();
        htmlSelectOneMenu = new MockTHtmlSelectOneMenu();
        htmlSelectOneMenu.setRenderer(renderer);

        // MockHtmlSelectOneMenuのプロパティ
        renderer.addIgnoreAttributeName("setSubmittedValueCalls");
    }

    public void testEncode_WithPageName() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setPageName("hogePage");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        System.out.println(getResponseText());
        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithLabelName() throws Exception {
        // ## Arrange ##
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setLabelName("aaaLabel");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        // ## Assert ##
        assertEquals("<select name=\"_id0\" size=\"1\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithOnTeedaError1() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setClientId("hoge");
        MockFacesContext facesContext = getFacesContext();
        facesContext.addMessage("hoge", new FacesMessage("sssss"));
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setPageName("hogePage");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        System.out.println(getResponseText());
        // ## Assert ##
        assertEquals("<select name=\"hoge\" size=\"1\" class=\"onTeedaError\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    public void testEncode_WithOnTeedaError2() throws Exception {
        // ## Arrange ##
        htmlSelectOneMenu.setClientId("hoge");
        MockFacesContext facesContext = getFacesContext();
        facesContext.addMessage("hoge", new FacesMessage("sssss"));
        htmlSelectOneMenu.setErrorStyleClass("foo");
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("v");
            selectItem.setItemLabel("l");
            htmlSelectOneMenu.getChildren().add(selectItem);
        }
        htmlSelectOneMenu.setPageName("hogePage");

        // ## Act ##
        encodeByRenderer(renderer, htmlSelectOneMenu);

        System.out.println(getResponseText());
        // ## Assert ##
        assertEquals("<select name=\"hoge\" size=\"1\" class=\"foo\">"
                + "<option value=\"v\">l</option>" + "</select>",
                getResponseText());
    }

    private THtmlSelectOneMenuRenderer createTHtmlSelectOneMenuRenderer() {
        return (THtmlSelectOneMenuRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlSelectOneMenuRenderer renderer = new THtmlSelectOneMenuRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public static class MockTHtmlSelectOneMenu extends THtmlSelectOneMenu {

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
