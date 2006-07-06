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

import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.ResponseWriter;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.render.AbstractResponseStateManager;
import org.seasar.teeda.core.render.EncodeConverter;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class HtmlResponseStateManagerTest extends TeedaTestCase {

    public void testWriteState_getEncodeInClient() throws Exception {
        // # Arrange #
        getFacesContext().getViewRoot().setViewId("hoge");
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_CLIENT);
        StateManager stateManager = getApplication().getStateManager();
        MockEncodeConverter converter = new MockEncodeConverter();
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        manager.setEncodeConverter(converter);
        SerializedView view = stateManager.new SerializedView("a", "b");

        manager.writeState(getFacesContext(), view);

        // doesnt work well.
        // Diff diff = new Diff("<input type=\"hidden\"
        // name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\"
        // value=\"b\" /><input type=\"hidden\" name=\"javax.faces.ViewState\"
        // id=\"javax.faces.ViewState\" value=\"hoge\" />",
        // getResponseText());
        // assertEquals(diff.toString(), true, diff.identical());
        assertEquals(
                "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"b\" />"
                        + "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"hoge\" />",
                getResponseText());
    }

    public void testWriteState_getEncodeInServer() throws Exception {
        // # Arrange #
        getFacesContext().getViewRoot().setViewId("foo");
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_SERVER);
        StateManager stateManager = getApplication().getStateManager();
        MockEncodeConverter converter = new MockEncodeConverter();
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        manager.setEncodeConverter(converter);
        SerializedView view = stateManager.new SerializedView("a", "b");

        manager.writeState(getFacesContext(), view);

        assertEquals(
                "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"a\" />"
                        + "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"foo\" />",
                getResponseText());
    }

    public void testComponentStateToRestore1() throws Exception {
        getExternalContext().getRequestMap().put(
                AbstractResponseStateManager.FACES_VIEW_STATE, "hoge");
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        Object o = manager.getComponentStateToRestore(getFacesContext());
        assertEquals("hoge", o);
        assertNull(getExternalContext().getRequestMap().get(
                AbstractResponseStateManager.FACES_VIEW_STATE));
    }

    public void testGetComponentStateToRestore2() throws Exception {
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        Object o = manager.getComponentStateToRestore(getFacesContext());
        assertNull(o);
    }

    public void testGetTreeStructureToRestore_emptyValue() throws Exception {
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        assertNull(manager.getTreeStructureToRestore(getFacesContext(), "a"));
    }

    public void testGetTreeStructureToRestore_getDecodedInClient()
            throws Exception {
        // # Arrange #
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_CLIENT);
        StateManager stateManager = getApplication().getStateManager();
        SerializedView view = stateManager.new SerializedView("a", "b");
        MockEncodeConverter converter = new MockEncodeConverter(view);
        getFacesContext().getExternalContext().getRequestParameterMap().put(
                AbstractResponseStateManager.VIEW_STATE_PARAM, "c");
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        manager.setEncodeConverter(converter);

        // # Act #
        Object o = manager.getTreeStructureToRestore(getFacesContext(), "hoge");

        // # Assert #
        assertNotNull(o);
        assertEquals("a", o);
    }

    public void testGetTreeStructureToRestore_getDecodedInServer()
            throws Exception {
        // # Arrange #
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_SERVER);
        StateManager stateManager = getApplication().getStateManager();
        SerializedView view = stateManager.new SerializedView("a", "b");
        MockEncodeConverter converter = new MockEncodeConverter(view);
        getFacesContext().getExternalContext().getRequestParameterMap().put(
                AbstractResponseStateManager.VIEW_STATE_PARAM, "c");
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        manager.setEncodeConverter(converter);

        // # Act #
        Object o = manager.getTreeStructureToRestore(getFacesContext(), "hoge");

        // # Assert #
        assertNotNull(o);
        assertEquals("c", o);
    }

    public void testWriteViewId() throws Exception {
        getFacesContext().getViewRoot().setViewId("hoge");
        ResponseWriter writer = getFacesContext().getResponseWriter();
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        manager.writeViewId(writer, getFacesContext());
        Diff diff = new Diff("<input type=\"hidden\""
                + " name=\"javax.faces.ViewState\""
                + " id=\"javax.faces.ViewState\"" + " value=\"hoge\" />",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    private static class MockEncodeConverter implements EncodeConverter {

        private SerializedView view_;

        public MockEncodeConverter() {
        }

        public MockEncodeConverter(SerializedView view) {
            view_ = view;
        }

        public String getAsEncodeString(Object target) {
            if (view_ != null) {
                return (String) view_.getState();
            } else {
                return (String) ((SerializedView) target).getState();
            }
        }

        public Object getAsDecodeObject(String state) {
            return view_.getStructure();
        }

    }
}
