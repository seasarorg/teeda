package javax.faces.mock;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class MockActionListener implements ActionListener {

	private ActionEvent event_ = null;

	public void processAction(ActionEvent event)
			throws AbortProcessingException {
		event_ = event;
	}

	public ActionEvent getEvent() {
		return event_;
	}
}
