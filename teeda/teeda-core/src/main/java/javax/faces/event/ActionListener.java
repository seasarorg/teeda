package javax.faces.event;

/**
 * @author shot
 */
public interface ActionListener extends FacesListener {

    public void processAction(ActionEvent actionEvent)
            throws AbortProcessingException;

}
