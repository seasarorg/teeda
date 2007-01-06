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
package org.seasar.teeda.core.util;

import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class StateManagerUtilTest extends TeedaTestCase {

    public void testIsSavingStateClient() throws Exception {
        assertFalse(StateManagerUtil.isSavingStateInClient(getFacesContext()));
    }

    public void testAssertComponentNoDuplicateId_success() throws Exception {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("id0");
        MockUIComponentBase child1 = new MockUIComponentBase();
        child1.setId("id1");
        parent.getChildren().add(child1);
        MockUIComponentBase child2 = new MockUIComponentBase();
        child2.setId("id2");
        parent.getFacets().put("hoge", child2);

        StateManagerUtil.assertComponentNoDuplicateId(parent);

        assertTrue(true);
    }

    public void testAssertComponentNoDuplicateId_idDuplicate() throws Exception {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("id0");
        MockUIComponentBase child1 = new MockUIComponentBase();
        child1.setId("id1");
        parent.getChildren().add(child1);
        MockUIComponentBase child2 = new MockUIComponentBase();
        child2.setId("id0");
        parent.getFacets().put("hoge", child2);
        try {
            StateManagerUtil.assertComponentNoDuplicateId(parent);
            fail();
        } catch (IllegalStateException expected) {
            success();
        }
    }

    public void testAssertComponentNoDuplicateId_idDuplicateWithNamingContainer()
            throws Exception {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("id0");
        MockUIComponentBase child1 = new MockUIComponentBase();
        child1.setId("child1");
        parent.getChildren().add(child1);

        MockUIComponentBaseWithNamingContainer child2 = new MockUIComponentBaseWithNamingContainer();
        child2.setId("child2");
        MockUIComponentBase grandChild1 = new MockUIComponentBase();
        grandChild1.setId("grandchild1");
        child2.getChildren().add(grandChild1);
        MockUIComponentBase grandChild2 = new MockUIComponentBase();
        grandChild2.setId("grandchild1");
        grandChild1.getFacets().put("hoge", grandChild2);
        parent.getFacets().put("foo", child2);
        try {
            StateManagerUtil.assertComponentNoDuplicateId(parent);
            fail();
        } catch (IllegalStateException expected) {
            success();
        }
    }

}
