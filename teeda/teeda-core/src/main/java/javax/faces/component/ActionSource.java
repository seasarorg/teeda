package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.ActionListener;

public interface ActionSource {

	public MethodBinding getAction();

	public void setAction(MethodBinding action);

	public MethodBinding getActionListener();

	public void setActionListener(MethodBinding actionListener);

	public boolean isImmediate();

	public void setImmediate(boolean immediate);

	public void addActionListener(ActionListener listener);

	public ActionListener[] getActionListeners();

	public void removeActionListener(ActionListener listener);
}
