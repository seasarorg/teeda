package javax.faces.application;

import javax.faces.context.FacesContext;

public abstract class NavigationHandler {

	public abstract void handleNavigation(FacesContext context,
			String fromAction, String outCome);
}
