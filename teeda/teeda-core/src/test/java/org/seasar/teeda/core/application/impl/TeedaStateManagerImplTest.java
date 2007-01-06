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
package org.seasar.teeda.core.application.impl;

import java.util.Locale;

import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.SerializableStateHolder;
import javax.faces.render.RenderKitFactory;

import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.application.TreeStructureManager;
import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockResponseStateManager;
import org.seasar.teeda.core.mock.MockResponseStateManagerImpl;
import org.seasar.teeda.core.mock.MockUIViewRoot;
import org.seasar.teeda.core.mock.NullResponseStateManager;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TeedaStateManagerImplTest extends TeedaTestCase {

    // TODO testing need more real test?
    public void testRestoreView() throws Exception {
    }

    public void testRestoreView_renderKitIdNull() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        try {
            manager.restoreView(getFacesContext(), "a", null);
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }
    }

    public void testRestoreView_getFromServer() throws Exception {
        // # Arrange #
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_SERVER);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        MockUIViewRoot root = new MockUIViewRoot();
        root.setId("aaa");
        root.setRenderKitId("renderKitId");
        root.setViewId("viewId");
        root.setLocale(Locale.JAPAN);
        getFacesContext().setViewRoot(root);
        manager.setTreeStructureManager(new TreeStructureManagerImpl());
        manager.saveSerializedView(getFacesContext());

        // # Act #
        UIViewRoot r = manager.restoreView(getFacesContext(), "viewId",
                RenderKitFactory.HTML_BASIC_RENDER_KIT);

        // # Assert #
        assertNotNull(r);
        assertTrue(r instanceof MockUIViewRoot);
        assertEquals("aaa", r.getId());
        assertEquals("renderKitId", r.getRenderKitId());
        assertEquals("viewId", r.getViewId());
        assertEquals(Locale.JAPAN, r.getLocale());
        assertNotNull(manager.getSerializedViewFromServer("viewId"));
    }

    public void testRestoreView_getFromClient() throws Exception {
        // # Arrange #
        MockUIViewRoot root = new MockUIViewRoot();
        root.setId("hoge");
        root.setViewId("foo");
        root.setRenderKitId("bar");
        TreeStructure struct = new TreeStructure(root.getClass().getName(),
                root.getId());
        MockResponseStateManager responseStateManager = new MockResponseStateManagerImpl();
        responseStateManager.setTreeStructureToRestore(struct);
        responseStateManager.setComponentStateToRestore("state");
        getRenderKit().setResponseStateManager(responseStateManager);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);
        getFacesContext().setViewRoot(root);
        manager.saveSerializedView(getFacesContext());

        // # Act #
        UIViewRoot r = manager.restoreView(getFacesContext(), "foo",
                RenderKitFactory.HTML_BASIC_RENDER_KIT);

        // # Assert #
        assertNotNull(r);
        assertTrue(r instanceof MockUIViewRoot);
        assertEquals("hoge", r.getId());
        assertEquals("foo", r.getViewId());
        assertEquals("bar", r.getRenderKitId());
    }
/*
    public void testSaveSerializedView_componentIdDuplicated() throws Exception {
        // # Arrange #
        UIViewRoot orgRoot = getFacesContext().getViewRoot();
        MockUIViewRoot parent = new MockUIViewRoot();
        parent.setId("id0");
        MockUIComponentBase child1 = new MockUIComponentBase();
        child1.setId("id1");
        parent.getChildren().add(child1);
        MockUIComponentBase child2 = new MockUIComponentBase();
        child2.setId("id0");
        parent.getFacets().put("hoge", child2);
        getFacesContext().setViewRoot(parent);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        try {
            // # Act & Assert #
            manager.saveSerializedView(getFacesContext());
            fail();
        } catch (IllegalStateException expected) {
            success();
        } finally {
            getFacesContext().setViewRoot(orgRoot);
        }
    }
*/
    public void testSaveSerializedView_whenSavingStateClient() throws Exception {
        // # Arrange #
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_CLIENT);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        MockUIViewRoot root = new MockUIViewRoot();
        root.setId("aaa");
        root.setRenderKitId("renderKitId");
        root.setViewId("viewId");
        root.setLocale(Locale.JAPAN);
        getFacesContext().setViewRoot(root);
        manager.setTreeStructureManager(new TreeStructureManagerImpl());

        // # Act #
        SerializedView serView = manager.saveSerializedView(getFacesContext());

        SerializableStateHolder holder = (SerializableStateHolder) serView
                .getState();
        Object[] states = (Object[]) holder.getState();
        assertEquals(root.getRenderKitId(), states[1]);
        assertEquals(root.getViewId(), states[2]);
        TreeStructure struct = (TreeStructure) serView.getStructure();
        assertEquals(root.getClass().getName(), struct.getComponentClassName());
        assertEquals(root.getId(), struct.getComponentId());
    }

    public void testSaveSerializedView_whenSavingStateServer() throws Exception {
        // # Arrange #
        getServletContext().setInitParameter(
                StateManager.STATE_SAVING_METHOD_PARAM_NAME,
                StateManager.STATE_SAVING_METHOD_SERVER);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        MockUIViewRoot root = new MockUIViewRoot();
        root.setId("aaa");
        root.setRenderKitId("renderKitId");
        root.setViewId("viewId");
        root.setLocale(Locale.JAPAN);
        getFacesContext().setViewRoot(root);
        manager.setTreeStructureManager(new TreeStructureManagerImpl());

        // # Act & Assert#
        assertNull(manager.saveSerializedView(getFacesContext()));
        SerializedView serView = manager.getSerializedViewFromServer("viewId");
        SerializableStateHolder holder = (SerializableStateHolder) serView
                .getState();
        Object[] states = (Object[]) holder.getState();
        assertEquals(root.getRenderKitId(), states[1]);
        assertEquals(root.getViewId(), states[2]);
        TreeStructure struct = (TreeStructure) serView.getStructure();
        assertEquals(root.getClass().getName(), struct.getComponentClassName());
        assertEquals(root.getId(), struct.getComponentId());
    }

    public void testGetComponentStateToSave_success() throws Exception {
        // # Arrange #
        UIViewRoot root = new UIViewRoot();
        root.setId("aaa");
        getFacesContext().setViewRoot(root);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act #
        Object o = manager.getComponentStateToSave(getFacesContext());

        // # Assert #
        assertNotNull(o);
        assertTrue(o instanceof SerializableStateHolder);
    }

    public void testGetComponentStateToSave_viewRootIsTransient()
            throws Exception {
        // # Arrange #
        NotifyUIViewRoot root = new NotifyUIViewRoot() {
            public boolean isTransient() {
                return true;
            }
        };
        UIViewRoot orgRoot = getFacesContext().getViewRoot();
        getFacesContext().setViewRoot(root);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act & Assert #
        assertNull(manager.getComponentStateToSave(getFacesContext()));
        getFacesContext().setViewRoot(orgRoot);
    }

    public void testGetTreeStructureToSave_success() throws Exception {
        // # Arrange #
        UIViewRoot root = new UIViewRoot();
        root.setId("aaa");
        getFacesContext().setViewRoot(root);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        manager.setTreeStructureManager(new TreeStructureManagerImpl());

        // # Act #
        Object o = manager.getTreeStructureToSave(getFacesContext());

        // # Assert #
        assertNotNull(o);
        assertTrue(o instanceof TreeStructure);
        TreeStructure struct = (TreeStructure) o;
        assertEquals("aaa", struct.getComponentId());
        assertEquals(root.getClass().getName(), struct.getComponentClassName());
    }

    public void testGetTreeStructureToSave_viewRootIsTransient()
            throws Exception {
        // # Arrange #
        UIViewRoot root = new UIViewRoot() {
            public boolean isTransient() {
                return true;
            }
        };
        UIViewRoot orgRoot = getFacesContext().getViewRoot();
        getFacesContext().setViewRoot(root);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        manager.setTreeStructureManager(new TreeStructureManagerImpl());

        // # Act & Assert #
        assertNull(manager.getTreeStructureToSave(getFacesContext()));
        getFacesContext().setViewRoot(orgRoot);
    }

    public void testRestoreComponentStateFromClient_serializedViewIsNull()
            throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act #
        try {
            manager.restoreComponentState(getFacesContext(), new UIViewRoot(),
                    null);
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }
    }

    public void testRestoreComponentStateFromClient_success() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        NotifyUIViewRoot component = new NotifyUIViewRoot();
        MockResponseStateManager responseStateManager = new MockResponseStateManagerImpl();
        responseStateManager.setComponentStateToRestore("state");
        getRenderKit().setResponseStateManager(responseStateManager);

        // # Act #
        manager.restoreComponentStateFromClient(getFacesContext(), component,
                RenderKitFactory.HTML_BASIC_RENDER_KIT);

        // # Assert #
        assertEquals(1, component.getNotifyCount());
    }

    public void testRestoreComponentStateFromServer_succeed() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        NotifyUIViewRoot component = new NotifyUIViewRoot();
        component.setViewId("id");
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, "id");
        manager.saveSerializedViewToServer("id", view);

        // # Act #
        manager.restoreComponentStateFromServer(getFacesContext(), component);

        // # Assert #
        assertEquals(1, component.getNotifyCount());
    }

    public void testRestoreComponentStateFromServer_stateIsNull()
            throws Exception {
        // # Arrange #
        NotifyUIViewRoot component = new NotifyUIViewRoot();
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        component.setId("id");
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, null);
        manager.saveSerializedViewToServer("id", view);

        // # Act #
        manager.restoreComponentStateFromServer(getFacesContext(), component);

        // # Assert #
        assertEquals(0, component.getNotifyCount());
    }

    public void testRestoreComponenentStateFromServer_serializedViewIsNull()
            throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        NotifyUIViewRoot root = new NotifyUIViewRoot();

        // # Act #
        manager.restoreComponentStateFromServer(getFacesContext(), root);

        // # Assert #
        assertEquals(0, root.getNotifyCount());
    }

    public void testRestoreTreeStructure_renderKitIdNull() throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act #
        try {
            manager.restoreTreeStructure(getFacesContext(), "id", null);
            fail();
        } catch (IllegalArgumentException expected) {
            success();
        }
    }

    public void testRestoreTreeStructureFromClient_restoredStructureIsNull()
            throws Exception {
        // # Arrange #
        MockRenderKit renderKit = getRenderKit();
        renderKit.setResponseStateManager(new NullResponseStateManager());
        setRenderKit(renderKit);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();

        // # Act & Assert #
        assertNull(manager.restoreTreeStructureFromClient(getFacesContext(),
                "id", RenderKitFactory.HTML_BASIC_RENDER_KIT));
    }

    public void testRestoreTreeStructureFromClient_restoreSucceess()
            throws Exception {
        // # Arrange #
        MockUIViewRoot root = new MockUIViewRoot();
        root.setId("hoge");
        TreeStructure struct = new TreeStructure(root.getClass().getName(),
                root.getId());
        MockResponseStateManager responseStateManager = new MockResponseStateManagerImpl();
        responseStateManager.setTreeStructureToRestore(struct);
        getRenderKit().setResponseStateManager(responseStateManager);
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);

        // # Act #
        UIViewRoot returnRoot = manager.restoreTreeStructureFromClient(
                getFacesContext(), "hoge",
                RenderKitFactory.HTML_BASIC_RENDER_KIT);

        // # Assert #
        assertNotNull(returnRoot);
        assertEquals("hoge", returnRoot.getId());
        assertTrue(returnRoot instanceof MockUIViewRoot);
    }

    public void testRestoreTreeStructureFromServer_serializedViewIsNull()
            throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);
        // # Act & Assert #
        assertNull(manager.restoreTreeStructureFromServer(getFacesContext(),
                "id"));
    }

    public void testRestoreTreeStructureFromServer_restoreSuccess()
            throws Exception {
        // # Arrange #
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        UIViewRoot component = new UIViewRoot();
        component.setId("root");
        TreeStructure struct = new TreeStructure(
                component.getClass().getName(), component.getId());
        SerializedView view = manager.new SerializedView(struct, "state");
        manager.saveSerializedViewToServer("id", view);
        TreeStructureManager structManager = new TreeStructureManagerImpl();
        manager.setTreeStructureManager(structManager);

        // # Act #
        UIViewRoot root = manager.restoreTreeStructureFromServer(
                getFacesContext(), "id");

        // # Arrange #
        assertNotNull(root);
        assertEquals("root", root.getId());
    }

    public static class NotifyUIViewRoot extends UIViewRoot {

        private int count = 0;

        public NotifyUIViewRoot() {
        }

        public void processRestoreState(FacesContext context, Object state) {
            count++;
        }

        public void reset() {
            count = 0;
        }

        public int getNotifyCount() {
            return count;
        }
    }
}
