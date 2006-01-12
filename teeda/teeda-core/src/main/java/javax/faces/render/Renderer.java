package javax.faces.render;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public abstract class Renderer {

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull("context", context);
        assertNotNull("component", component);
    }

    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        assertNotNull("context", context);
        assertNotNull("component", component);
    }

    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
        assertNotNull("context", context);
        assertNotNull("component", component);

        UIComponent child = null;
        for (Iterator itr = component.getChildren().iterator(); itr.hasNext();) {
            child = (UIComponent) itr.next();
            child.encodeBegin(context);
            if (child.getRendersChildren()) {
                child.encodeChildren(context);
            }
            child.encodeEnd(context);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        assertNotNull("context", context);
        assertNotNull("component", component);
    }

    public String convertClientId(FacesContext context, String clientId) {
        assertNotNull("context", context);
        assertNotNull("clientId", clientId);
        return clientId;
    }

    public boolean getRendersChildren() {
        return false;
    }

    public Object getConvertedValue(FacesContext context,
        UIComponent component, Object submittedValue) throws ConverterException {
        assertNotNull("context", context);
        assertNotNull("component", component);
        return submittedValue;
    }

    private static void assertNotNull(String message, Object actual) {
        if (actual == null) {
            throw new NullPointerException(message);
        }
    }
}
