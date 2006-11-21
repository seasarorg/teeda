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
package org.seasar.teeda.extension.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class UITreeDataTest extends TeedaTestCase {

    public void testSaveAndRestore() throws Exception {
        FacesContext context = getFacesContext();
        UITreeData tree = new UITreeData();
        tree.setVar("aaa");
        tree.setValue(new TreeModelImpl(new TreeNodeImpl("0", "1", true)));
        tree.encodeEnd(context);
        Object saveState = tree.saveState(context);
        UITreeData tree2 = new UITreeData();
        tree2.setValue(new TreeModelImpl(new TreeNodeImpl("", "", true)));
        tree2.restoreState(context, saveState);
        assertEquals("aaa", tree2.getVar());
        TreeModel treemodel = tree2.getDataModel();
        assertNotNull(treemodel.getNodeById("0"));
    }

    public void testGetDataModel_valueIsTreeModel() throws Exception {
        UITreeData tree = new UITreeData();
        assertNull(tree.getDataModel());
        TreeNodeImpl node = new TreeNodeImpl("0", "a", false);
        TreeModelImpl model = new TreeModelImpl(node);
        tree.setValue(model);
        assertNotNull(tree.getDataModel());
        assertNotNull(tree.getDataModel().getNodeById("0"));
        assertEquals(node, tree.getDataModel().getNodeById("0"));
    }

    public void testGetDataModel_valueIsTreeNode() throws Exception {
        UITreeData tree = new UITreeData();
        assertNull(tree.getDataModel());
        TreeNodeImpl node = new TreeNodeImpl("0", "a", false);
        tree.setValue(node);
        assertNotNull(tree.getDataModel());
        assertNotNull(tree.getDataModel().getNodeById("0"));
        assertEquals(node, tree.getDataModel().getNodeById("0"));
    }

    public void testEncodeBegin() throws Exception {
        FacesContext context = getFacesContext();
        final boolean[] calls = new boolean[] { false };
        getRenderKit().addRenderer("org.seasar.teeda.extension.Tree",
                "org.seasar.teeda.extension.Tree", new NullRenderer() {

                    public void encodeBegin(FacesContext context,
                            UIComponent component) throws IOException {
                        super.encodeBegin(context, component);
                        calls[0] = true;
                    }

                });
        UITreeData tree = new UITreeData();
        tree.encodeBegin(context);
        assertTrue(calls[0]);
    }

    public void testEncodeEnd() throws Exception {
        FacesContext context = getFacesContext();
        final boolean[] calls = new boolean[] { false };
        getRenderKit().addRenderer("org.seasar.teeda.extension.Tree",
                "org.seasar.teeda.extension.Tree", new NullRenderer() {

                    public void encodeEnd(FacesContext context,
                            UIComponent component) throws IOException {
                        super.encodeBegin(context, component);
                        calls[0] = true;
                    }

                });
        UITreeData tree = new UITreeData();
        TreeNodeImpl node = new TreeNodeImpl("0", "a", false);
        TreeModelImpl model = new TreeModelImpl(node);
        tree.setValue(model);

        tree.encodeEnd(context);

        TreeModel m = tree.getDataModel();
        assertNotNull(m);
    }

}
