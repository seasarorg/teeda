package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.ValueChangeEvent;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author shot
 * @author manhole
 */
public class UIInputTest extends UIOutputTest {

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

    // NOT_TODO test: decode -> renderer

    public void testBroadcast_PassToListener() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        final Object[][] methodParams = { null };
        UIInput input = createUIInput();
        MethodBinding valueChangeMethod = new MockMethodBinding() {
            public Object invoke(FacesContext context, Object[] params)
                    throws EvaluationException, MethodNotFoundException {
                calls[0] = true;
                methodParams[0] = params;
                return null;
            }
        };
        input.setValueChangeListener(valueChangeMethod);
        ValueChangeEvent event = new ValueChangeEvent(
                new MockUIComponentBase(), "1", "2");

        // ## Act ##
        input.broadcast(event);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(1, methodParams[0].length);
        assertSame(event, methodParams[0][0]);
    }

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
