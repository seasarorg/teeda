package javax.faces.event;

import javax.faces.component.UIComponent;

public class ActionEvent extends FacesEvent {

    private static final long serialVersionUID = 1L;

    public ActionEvent(UIComponent component) {
        super(component);
        if (component == null) {
            throw new IllegalArgumentException("ActionEvent");
        }
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof ActionListener);
    }

    public void processListener(FacesListener listener) {
        ((ActionListener) listener).processAction(this);
    }

}
