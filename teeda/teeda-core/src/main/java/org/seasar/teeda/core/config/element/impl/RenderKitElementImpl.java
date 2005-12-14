package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.RendererElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class RenderKitElementImpl implements RenderKitElement {

    private String renderKitId_;

    private String renderKitClass_;

    private List renderers_ = new ArrayList();

    public RenderKitElementImpl() {
    }

    public void setRenderKitId(String renderKitId) {
        renderKitId_ = renderKitId;
    }

    public void setRenderKitClass(String renderKitClass) {
        renderKitClass_ = renderKitClass;
    }

    public String getRenderKitId() {
        return renderKitId_;
    }

    public String getRenderKitClass() {
        return renderKitClass_;
    }

    public void addRendererElement(RendererElement renderer) {
        renderers_.add(renderer);
    }

    public List getRendererElements() {
        return renderers_;
    }

}
