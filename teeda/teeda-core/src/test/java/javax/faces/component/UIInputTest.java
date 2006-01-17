package javax.faces.component;

import javax.faces.el.MethodBinding;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author shot
 * @author manhole
 */
public class UIInputTest extends UIOutputTest {

    public void testDefaultRendererType() throws Exception {
        UIInput input = new UIInput();
        assertEquals("javax.faces.Text", input.getRendererType());
    }

    public void testGetFamily() {
        UIInput input = new UIInput();
        assertEquals(UIInput.COMPONENT_FAMILY, input.getFamily());
    }

    public void testSetGetSubmittedValue() {
        UIInput input = createUIInput();
        input.setSubmittedValue("aaa");
        assertEquals("aaa", input.getSubmittedValue());
    }

    public void testSetValue() {
        UIInput input = createUIInput();
        input.setValue("bbb");
        assertEquals("bbb", input.getValue());
    }

    public void testSetGetLocalValueSet() {
        UIInput input = createUIInput();
        input.setLocalValueSet(true);
        assertEquals(true, input.isLocalValueSet());
    }

    public void testSetValueToIsLocalValueSet() throws Exception {
        UIInput input = createUIInput();
        assertEquals(false, input.isLocalValueSet());
        input.setValue("aaaa");
        assertEquals(true, input.isLocalValueSet());
    }

    public void testSetGetRequired() {
        UIInput input = createUIInput();
        input.setRequired(true);
        assertEquals(true, input.isRequired());
    }

    public void testSetGetRequired_ValueBinding() {
        UIInput input = createUIInput();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(new MockFacesContextImpl(), Boolean.TRUE);
        input.setValueBinding("required", vb);
        assertEquals(true, input.isRequired());
    }

    public void testSetGetValid() {
        UIInput input = createUIInput();
        input.setValid(true);
        assertEquals(true, input.isValid());
    }

    public void testSetGetValid_ValueBinding() {
        UIInput input = createUIInput();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(new MockFacesContextImpl(), Boolean.TRUE);
        input.setValueBinding("valid", vb);
        assertEquals(true, input.isValid());
    }

    public void testSetGetImmediate() {
        UIInput input = createUIInput();
        input.setImmediate(true);
        assertEquals(true, input.isImmediate());
    }

    public void testSetGetImmediate_ValueBinding() {
        UIInput input = createUIInput();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Boolean(true));
        input.setValueBinding("immediate", vb);
        assertEquals(true, input.isImmediate());
    }

    public void testSetGetValidator() {
        UIInput input = createUIInput();
        MethodBinding methodBinding = new MockMethodBinding();

        // ## Act & Assert ##
        input.setValidator(methodBinding);
        assertEquals(methodBinding, input.getValidator());
    }

    public void testSetGetValueChangeListener() throws Exception {
        // ## Arrange ##
        UIInput input = createUIInput();
        MethodBinding methodBinding = new MockMethodBinding();

        // ## Act & Assert ##
        input.setValueChangeListener(methodBinding);
        assertEquals(methodBinding, input.getValueChangeListener());
    }

    // TODO test: decode
    // TODO test: broadcast
    // TODO test: updateModel

    // TODO test: getConvertedValue
    // TODO test: validateValue
    // TODO test: compareValues
    // TODO test: addValidator
    // TODO test: getValidators
    // TODO test: removeValidator
    // TODO test: addValueChangeListener
    // TODO test: getValueChangeListeners
    // TODO test: removeValueChangeListener
    // TODO test: saveState
    // TODO test: restoreState

    private UIInput createUIInput() {
        return (UIInput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIInput();
    }

}
