package org.seasar.teeda.core.mock;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;


public class MockRenderKitFactory extends RenderKitFactory {

    public RenderKit getRenderKit(FacesContext context, String renderKitId) {
        return null;
    }

    public Iterator getRenderKitIds() {
        return null;
    }

    public void addRenderKit(String renderKitID, RenderKit renderKit) {
    }

}
