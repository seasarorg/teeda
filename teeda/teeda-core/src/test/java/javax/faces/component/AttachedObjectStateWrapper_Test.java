package javax.faces.component;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockFacesContextImpl;

public class AttachedObjectStateWrapper_Test extends TestCase {

    public void testRestoreSerializable() {
        FacesContext context = new MockFacesContextImpl();
        SerializableBean target = new SerializableBean();
        AttachedObjectStateWrapper_ wrapper = new AttachedObjectStateWrapper_(
                context, target);
        assertEquals(target, wrapper.restore(context));
    }

    public void testRestoreStateHolder() {
        FacesContext context = new MockFacesContextImpl();
        StateHolderBean target = new StateHolderBean();
        target.setStr("a");
        AttachedObjectStateWrapper_ wrapper = new AttachedObjectStateWrapper_(
                context, target);
        Object o = wrapper.restore(context);
        assertTrue(o instanceof StateHolderBean);
        assertEquals("a", ((StateHolderBean) o).getStr());
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
}
