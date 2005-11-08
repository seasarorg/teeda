package javax.faces.event;

public interface ActionListener extends FacesListener {

	void processAction(ActionEvent actionEvent) throws AbortProcessingException;
}
