package javax.faces.component;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

class RenderKitUtil_ {

    private RenderKitUtil_() {
    }

    public static RenderKit getRenderKit(FacesContext context) {
        RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        String renderKitId = context.getViewRoot().getRenderKitId();
        RenderKit renderKit = renderKitFactory.getRenderKit(context,
                renderKitId);
        return renderKit;
    }

}
