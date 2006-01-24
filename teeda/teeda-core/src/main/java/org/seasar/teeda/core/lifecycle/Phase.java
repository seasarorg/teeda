package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;

public interface Phase {

    public void execute(FacesContext context);
}
