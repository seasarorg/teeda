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

import java.util.Map;

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
        // ## Arrange ##
        setupStateSavingInClient();
        getFacesContext().getViewRoot().setViewId("hogeViewId");
        StateManager stateManager = getApplication().getStateManager();
        NullEncodeConverter converter = new NullEncodeConverter() {
            public String getAsEncodeString(Object target) {
                return "encodedString";
            }
        };
        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        responseStateManager.setEncodeConverter(converter);
        SerializedView serializedView = stateManager.new SerializedView(
                "bbbStructure", "cccState");

        // ## Act ##
        responseStateManager.writeState(getFacesContext(), serializedView);

        // ## Assert ##

        // doesnt work well.
        // Diff diff = new Diff("<input type=\"hidden\"
        // name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\"
        // value=\"b\" /><input type=\"hidden\" name=\"javax.faces.ViewState\"
        // id=\"javax.faces.ViewState\" value=\"hoge\" />",
        // getResponseText());
        // assertEquals(diff.toString(), true, diff.identical());
        assertEquals(
                "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"encodedString\" />"
                        + "<input type=\"hidden\" name=\""
                        + AbstractResponseStateManager.VIEW_ID
                        + "\" id=\""
                        + AbstractResponseStateManager.VIEW_ID
                        + "\" value=\"hogeViewId\" />", getResponseText());
    }

    public void testWriteState_getEncodeInServer() throws Exception {
        // ## Arrange ##
        setupStateSavingInServer();
        getFacesContext().getViewRoot().setViewId("fooViewId");
        StateManager stateManager = getApplication().getStateManager();
        NullEncodeConverter converter = new NullEncodeConverter();
        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        responseStateManager.setEncodeConverter(converter);
        SerializedView view = stateManager.new SerializedView("aaaStructure",
                "b");

        // ## Act ##
        responseStateManager.writeState(getFacesContext(), view);

        // ## Assert ##
        assertEquals(
                "<input type=\"hidden\" name=\"javax.faces.ViewState\" id=\"javax.faces.ViewState\" value=\"aaaStructure\" />"
                        + "<input type=\"hidden\" name=\""
                        + AbstractResponseStateManager.VIEW_ID
                        + "\" id=\""
                        + AbstractResponseStateManager.VIEW_ID
                        + "\" value=\"fooViewId\" />", getResponseText());
    }

    public void testGetComponentStateToRestore1() throws Exception {
        getExternalContext().getRequestMap().put(
                AbstractResponseStateManager.FACES_VIEW_STATE, "hoge");

        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        Object o = responseStateManager
                .getComponentStateToRestore(getFacesContext());

        assertEquals("hoge", o);
        assertNull(getExternalContext().getRequestMap().get(
                AbstractResponseStateManager.FACES_VIEW_STATE));
    }

    public void testGetComponentStateToRestore2() throws Exception {
        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        Object o = responseStateManager
                .getComponentStateToRestore(getFacesContext());
        assertNull(o);
    }

    public void testGetTreeStructureToRestore_emptyValue() throws Exception {
        HtmlResponseStateManager manager = new HtmlResponseStateManager();
        assertNull(manager.getTreeStructureToRestore(getFacesContext(), "a"));
    }

    public void testGetTreeStructureToRestore_getDecodedInClient()
            throws Exception {
        setupStateSavingInClient();
        final Object[] args = new Object[1];
        NullEncodeConverter converter = new NullEncodeConverter() {
            public Object getAsDecodeObject(String state) {
                args[0] = state;
                return new StructureAndState("aaaStructure", "b");
            }
        };
        final Map requestParameterMap = getFacesContext().getExternalContext()
                .getRequestParameterMap();
        requestParameterMap.put(AbstractResponseStateManager.VIEW_STATE_PARAM,
                "fooStateParam");
        requestParameterMap.put(AbstractResponseStateManager.VIEW_ID,
                "hogeViewId");

        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        assertEquals(true, responseStateManager
                .isSavingStateInClient(getFacesContext()));
        responseStateManager.setEncodeConverter(converter);

        // ## Act ##
        final Object restoredStructure = responseStateManager
                .getTreeStructureToRestore(getFacesContext(), "hogeViewId");

        // ## Assert ##
        assertNotNull(restoredStructure);
        assertEquals("aaaStructure", restoredStructure);
        assertEquals(args[0], "fooStateParam");
    }

    public void testGetTreeStructureToRestore_getDecodedInServer()
            throws Exception {
        // ## Arrange ##
        setupStateSavingInServer();
        final Map requestParameterMap = getFacesContext().getExternalContext()
                .getRequestParameterMap();
        requestParameterMap.put(AbstractResponseStateManager.VIEW_STATE_PARAM,
                "cccServerState");
        requestParameterMap.put(AbstractResponseStateManager.VIEW_ID, "hoge");

        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        assertEquals(false, responseStateManager
                .isSavingStateInClient(getFacesContext()));
        NullEncodeConverter converter = new NullEncodeConverter();
        responseStateManager.setEncodeConverter(converter);

        // ## Act ##
        Object o = responseStateManager.getTreeStructureToRestore(
                getFacesContext(), "hoge");

        // ## Assert ##
        assertNotNull(o);
        assertEquals("cccServerState", o);
    }

    public void testGetStructureAndStateToRestore_InClient() throws Exception {
        // ## Arrange ##
        setupStateSavingInClient();
        final Map requestParameterMap = getFacesContext().getExternalContext()
                .getRequestParameterMap();
        requestParameterMap.put(AbstractResponseStateManager.VIEW_STATE_PARAM,
                "fooStateParam");
        requestParameterMap.put(AbstractResponseStateManager.VIEW_ID,
                "hogeViewId");
        NullEncodeConverter converter = new NullEncodeConverter() {
            public Object getAsDecodeObject(String state) {
                return new StructureAndState("111_Structure", "222_State");
            }
        };
        final HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        responseStateManager.setEncodeConverter(converter);

        // ## Act ##
        final Object restoredStructure = responseStateManager
                .getTreeStructureToRestore(getFacesContext(), "hogeViewId");
        final Object restoredState = responseStateManager
                .getComponentStateToRestore(getFacesContext());

        // ## Assert ##
        assertEquals("111_Structure", restoredStructure);
        assertEquals("222_State", restoredState);
    }

    public void testWriteViewId() throws Exception {
        ResponseWriter writer = getFacesContext().getResponseWriter();
        HtmlResponseStateManager responseStateManager = new HtmlResponseStateManager();
        responseStateManager.writeViewId(writer, "hoge");
        Diff diff = new Diff("<input type=\"hidden\"" + " name=\""
                + AbstractResponseStateManager.VIEW_ID + "\"" + " id=\""
                + AbstractResponseStateManager.VIEW_ID + "\""
                + " value=\"hoge\" />", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    private void setupStateSavingInServer() {
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_SERVER);
    }

    private void setupStateSavingInClient() {
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_CLIENT);
    }

    private static class NullEncodeConverter implements EncodeConverter {

        public NullEncodeConverter() {
        }

        public String getAsEncodeString(Object target) {
            return null;
        }

        public Object getAsDecodeObject(String state) {
            return null;
        }

    }
}
