package javax.faces.event;

public interface ValueChangeListener extends FacesListener {

    public void processValueChange(ValueChangeEvent event)
        throws AbortProcessingException;

}