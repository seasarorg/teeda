package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface RenderKitElement extends JsfConfig {

    public void setRenderKitId(String renderKitId);
    
    public void setRenderKitClass(String renderKitClass);
    
    public String getRenderKitId();
    
    public String getRenderKitClass();
    
    public void addRendererElement(RendererElement renderer);
    
    public List getRendererElements();
}
