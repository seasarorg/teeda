package org.seasar.teeda.core.spike.dicon;

import javax.faces.component.UIInput;
import javax.faces.render.Renderer;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.teeda.core.application.ApplicationImpl;
import org.seasar.teeda.core.application.impl.TeedaStateManagerImpl;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.lifecycle.LifecycleImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.render.html.HtmlRenderKitImpl;
import org.seasar.teeda.core.render.html.HtmlResponseStateManager;
import org.seasar.teeda.core.resource.ValidatorResourceImpl;

public class TeedaDiconTest extends S2FrameworkTestCase {

    public void testDicon() throws Exception {
        include("teeda.dicon");
        S2Container container = getContainer();
        LifecycleImpl lifecycle = (LifecycleImpl) container
                .getComponent(LifecycleImpl.class);
        assertNotNull(lifecycle.getApplyRequestValuesPhase());
        assertNotNull(lifecycle.getInvokeApplicationPhase());
        assertNotNull(lifecycle.getProcessValidationPhase());
        assertNotNull(lifecycle.getRenderResponsePhase());
        assertNotNull(lifecycle.getRestoreViewPhase());
        assertNotNull(lifecycle.getUpdateModelValuesPhase());

        ApplicationImpl app = (ApplicationImpl) container
                .getComponent(ApplicationImpl.class);
        assertNotNull(app.getActionListener());
        assertNotNull(app.getNavigationHandler());
        assertNotNull(app.getPropertyResolver());
        assertNotNull(app.getStateManager());
        assertNotNull(app.getVariableResolver());
        assertNotNull(app.getViewHandler());
        assertNotNull(app.getValueBindingContext());
        assertNotNull(app.getMethodBindingContext());
        
        TeedaStateManagerImpl stateManager = (TeedaStateManagerImpl) app
                .getStateManager();
        assertNotNull(stateManager.getTreeStructureManager());

        HtmlRenderKitImpl renderKit = (HtmlRenderKitImpl) container
                .getComponent(HtmlRenderKitImpl.class);
        assertNotNull(renderKit.getResponseStateManager());

        Renderer renderer = renderKit.getRenderer("javax.faces.Input",
                "javax.faces.Text");
        assertTrue(renderer instanceof HtmlInputTextRenderer);
        HtmlInputTextRenderer inputTextRenderer = (HtmlInputTextRenderer) renderer;
        assertNotNull(inputTextRenderer.getComponentIdLookupStrategy());

        HtmlResponseStateManager responseStateManager = (HtmlResponseStateManager) renderKit
                .getResponseStateManager();
        assertNotNull(responseStateManager.getEncodeConverter());

        ManagedBeanFactory mbFactory = (ManagedBeanFactory) container
                .getComponent(ManagedBeanFactory.class);
        assertNotNull(mbFactory);
        assertNotNull(mbFactory.getManagedBeanScopeSaver());
        assertNotNull(mbFactory.getScopeManager());

        ValueBindingContext vbContext = (ValueBindingContext) container
                .getComponent("teeda.valueBindingContext");
        assertNotNull(vbContext.getELParser());
        assertNotNull(vbContext.getELParser().getExpressionProcessor());
    }

}
