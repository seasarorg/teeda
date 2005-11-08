package javax.faces.mock;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class MockSingleConstructorActionListener implements ActionListener {

	private ActionEvent event_ = null;

    private ActionListener originalListener_;
    
    public MockSingleConstructorActionListener(ActionListener listener){
        originalListener_ = listener;
    }
    
	public void processAction(ActionEvent event)
			throws AbortProcessingException {
        originalListener_.processAction(event);
        event_ = event;
	}

	public ActionEvent getEvent() {
		return event_;
	}
}
