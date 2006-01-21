package org.seasar.teeda.core.mock;

import java.util.*;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;


public class MockRenderKitFactory extends RenderKitFactory {

    private Map renderKits_ = new HashMap();
    public MockRenderKitFactory(){
    }
    
    public RenderKit getRenderKit(FacesContext context, String renderKitId) {
        return (RenderKit)renderKits_.get(renderKitId);
    }

    public Iterator getRenderKitIds() {
        return renderKits_.keySet().iterator();
    }

    public void addRenderKit(String renderKitId, RenderKit renderKit) {
        renderKits_.put(renderKitId, renderKit);
    }

}
