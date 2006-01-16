package javax.faces.component;

import javax.faces.convert.Converter;

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.convert.NullConverter;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author shot
 * @author manhole
 */
public class UIOutputTest extends UIComponentBaseTest {

    public void testDefaultRendererType() throws Exception {
        UIOutput output = createUIOutput();
        assertEquals("javax.faces.Text", output.getRendererType());
    }

    public void testGetFamily() {
        UIOutput output = new UIOutput();
        assertEquals(UIOutput.COMPONENT_FAMILY, output.getFamily());
    }

    public void testSetGetConverter() {
        UIOutput output = createUIOutput();
        Converter converter = new NullConverter();
        output.setConverter(converter);
        assertSame(converter, output.getConverter());
    }

    public void testSetGetConverter_ValueBinding() {
        UIOutput output = createUIOutput();
        Converter converter = new NullConverter();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), converter);
        output.setValueBinding("converter", vb);
        assertSame(converter, output.getConverter());
    }

    public final void testSetValueGetLocalValue() {
        UIOutput output = createUIOutput();
        output.setValue("aaa");
        assertEquals("aaa", output.getLocalValue());
    }

    public void testSetGetValue() {
        UIOutput output = createUIOutput();
        output.setValue("aaa");
        assertEquals("aaa", output.getValue());
    }

    public void testSetGetValue_ValueBinding() {
        UIOutput output = createUIOutput();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bbb");
        output.setValueBinding("value", vb);
        assertEquals("bbb", output.getValue());
    }

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        UIOutput output1 = createUIOutput();
        output1.setConverter(new MockConverter());
        output1.setValue("foo value");
        MockFacesContext context = getFacesContext();
        Object state = output1.saveState(context);

        UIOutput output2 = createUIOutput();
        output2.restoreState(context, state);

        ObjectAssert.assertInstanceOf(MockConverter.class, output2
                .getConverter());
        assertEquals(output1.getValue(), output2.getValue());
    }

    private UIOutput createUIOutput() {
        return (UIOutput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIOutput();
    }

}
