package org.seasar.teeda.core.config.element.impl;

import org.seasar.teeda.core.config.element.RendererElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class RendererElementImpl implements RendererElement {

    private String family_;
    private String rendererType_;
    private String rendererClass_;
    public RendererElementImpl(){
    }
    
    public void setComponentFamily(String family) {
        family_ = family;
    }

    public void setRendererType(String rendererType) {
        rendererType_ = rendererType;
    }

    public void setRendererClass(String rendererClass) {
        rendererClass_ = rendererClass;
    }

    public String getComponentFamily() {
        return family_;
    }

    public String getRendererType() {
        return rendererType_;
    }

    public String getRendererClass() {
        return rendererClass_;
    }

}
