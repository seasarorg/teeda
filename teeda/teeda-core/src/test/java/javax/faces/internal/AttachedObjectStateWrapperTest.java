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
package javax.faces.internal;

import java.io.Serializable;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockFacesContextImpl;

public class AttachedObjectStateWrapperTest extends TestCase {

    public void testRestoreSerializable() {
        FacesContext context = new MockFacesContextImpl();
        SerializableBean target = new SerializableBean();
        AttachedObjectStateWrapper wrapper = new AttachedObjectStateWrapper(
                context, target);
        assertEquals(target, wrapper.restore(context));
    }

    public void testRestoreStateHolder() {
        FacesContext context = new MockFacesContextImpl();
        StateHolderBean target = new StateHolderBean();
        target.setStr("a");
        AttachedObjectStateWrapper wrapper = new AttachedObjectStateWrapper(
                context, target);
        Object o = wrapper.restore(context);
        assertTrue(o instanceof StateHolderBean);
        assertEquals("a", ((StateHolderBean) o).getStr());
    }

    public void testRestoreStateHolder_restoredStateIsNull() throws Exception {
        FacesContext context = new MockFacesContextImpl();
        StateHolderBean2 target = new StateHolderBean2();
        AttachedObjectStateWrapper wrapper = new AttachedObjectStateWrapper(
                context, target);
        Object o = wrapper.restore(context);
        assertNotNull(o);
        assertTrue(o instanceof StateHolderBean2);
    }

    private static class SerializableBean implements Serializable {

        private static final long serialVersionUID = 3257005453899544628L;

        public SerializableBean() {
        }
    }

    private static class StateHolderBean implements StateHolder {

        private boolean transientValue_ = false;

        private String str_ = null;

        public StateHolderBean() {
        }

        public boolean isTransient() {
            return transientValue_;
        }

        public void setTransient(boolean transientValue) {
            transientValue_ = transientValue;
        }

        public Object saveState(FacesContext context) {
            Object[] values = new Object[1];
            values[0] = str_;
            return values;
        }

        public void restoreState(FacesContext context, Object state) {
            Object[] values = (Object[]) state;
            str_ = (String) values[0];
        }

        public void setStr(String str) {
            str_ = str;
        }

        public String getStr() {
            return str_;
        }
    }

    private static class StateHolderBean2 implements StateHolder {

        public StateHolderBean2() {
        }

        public boolean isTransient() {
            return false;
        }

        public void setTransient(boolean transientValue) {
        }

        public Object saveState(FacesContext context) {
            return null;
        }

        public void restoreState(FacesContext context, Object state) {
        }

    }

}
