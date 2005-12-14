package org.seasar.teeda.core.config.element;

import javax.faces.application.ViewHandler;


public interface ViewHandlerElement extends JsfConfig {

    public void setViewHandler(ViewHandler handler);
    
    public ViewHandler getViewHandler();
}
