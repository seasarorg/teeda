package org.seasar.teeda.core.mock;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class MockFacesContext extends FacesContext {

    public abstract void setExternalContext(ExternalContext context);

    public abstract void setApplication(Application application);

}
