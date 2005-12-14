package org.seasar.teeda.core.config.element;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface RendererElement extends JsfConfig {

    public void setComponentFamily(String family);
    
    public void setRendererType(String rendererType);
    
    public void setRendererClass(String rendererClass);
    
    public String getComponentFamily();
    
    public String getRendererType();
    
    public String getRendererClass();
    
}
