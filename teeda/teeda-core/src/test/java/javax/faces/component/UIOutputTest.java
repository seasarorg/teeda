package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

public class UIOutputTest extends UIComponentBaseTest {

    public void testGetFamily() {
        UIOutput output = new UIOutput();
        assertEquals(UIOutput.COMPONENT_FAMILY, output.getFamily());
    }

    public void testSetConverter() {
        UIOutput output = createUIOutput();
        Converter converter = new Converter() {

            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return null;
            }

            public String getAsString(FacesContext context,
                    UIComponent component, Object value)
                    throws ConverterException {
                return null;
            }

            public String toString() {
                return "foo converter";
            }
        };

        output.setConverter(converter);
        assertEquals(converter.toString(), output.getConverter().toString());
    }

    public void testGetConverter() {
        UIOutput output = createUIOutput();
        Converter converter = new Converter() {

            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return null;
            }

            public String getAsString(FacesContext context,
                    UIComponent component, Object value)
                    throws ConverterException {
                return null;
            }

            public String toString() {
                return "some converter";
            }
        };
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), converter);
        output.setValueBinding("converter", vb);
        assertEquals("some converter", output.getConverter().toString());
    }

    public void testGetLocalValue() {
        UIOutput output = new UIOutput();
        output.setValue("aaa");
        assertEquals("aaa", output.getLocalValue());
    }

    public void testSetValue() {
        UIOutput output = createUIOutput();
        output.setValue("aaa");
        assertEquals("aaa", output.getValue());
    }

    public void testGetValue() {
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

        assertEquals(true, output2.getConverter() instanceof MockConverter);
        assertEquals(output1.getValue(), output2.getValue());
    }

    private UIOutput createUIOutput() {
        return (UIOutput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIOutput();
    }

}
