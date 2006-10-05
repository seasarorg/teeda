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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.component.helper.TreeModel;
import org.seasar.teeda.extension.component.helper.TreeModelImpl;
import org.seasar.teeda.extension.component.helper.TreeNodeImpl;

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

    public void testGetDataModel() throws Exception {
        FacesContext context = getFacesContext();
        UITreeData tree = new UITreeData();
        assertNull(tree.getDataModel());
        TreeNodeImpl node = new TreeNodeImpl("0", "a", false);
        TreeModelImpl model = new TreeModelImpl(node);
        tree.setValue(model);
        assertNotNull(tree.getDataModel());
        assertNotNull(tree.getDataModel().getNodeById("0"));
        assertEquals(node, tree.getDataModel().getNodeById("0"));

        //        TreeNodeImpl node2 = new TreeNodeImpl("1", "a", false);
        //        tree.setValue(node2);
        //        tree.encodeBegin(context);
        //        assertNotNull(tree.getDataModel());
        //        assertEquals(node2, tree.getDataModel().getNodeById("1"));
        //
        //        try {
        //            tree.setValue("illegal");
        //            fail();
        //        } catch (Exception expected) {
        //            success();
        //        }
    }
}
