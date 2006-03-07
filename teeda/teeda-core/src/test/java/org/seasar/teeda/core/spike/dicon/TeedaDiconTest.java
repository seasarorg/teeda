package org.seasar.teeda.core.spike.dicon;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.teeda.core.application.ApplicationImpl;
import org.seasar.teeda.core.application.impl.TeedaStateManagerImpl;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.lifecycle.LifecycleImpl;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.render.html.HtmlRenderKitImpl;
import org.seasar.teeda.core.render.html.HtmlResponseStateManager;

public class TeedaDiconTest extends S2FrameworkTestCase {

    public void setUp() throws Exception {
    }

    public void testDicon() throws Exception {
        include("test.dicon");
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

        HtmlResponseStateManager responseStateManager = (HtmlResponseStateManager) renderKit
                .getResponseStateManager();
        assertNotNull(responseStateManager.getEncodeConverter());

        ManagedBeanFactory mbFactory = (ManagedBeanFactory) container
                .getComponent(ManagedBeanFactory.class);
        assertNotNull(mbFactory);
        assertNotNull(mbFactory.getManagedBeanScopeSaver());
        assertNotNull(mbFactory.getScopeManager());

        ValueBindingContext vbContext = (ValueBindingContext) container
                .getComponent("valueBindingContext");
        assertNotNull(vbContext.getELParser());
        assertNotNull(vbContext.getELParser().getExpressionProcessor());

        //        ConfigFilesFacesConfigurator configFilesConfigurator = (ConfigFilesFacesConfigurator) container.getComponent("configFilesConfigurator");
        //        assertNotNull(configFilesConfigurator);
        //        assertNotNull(configFilesConfigurator.getExternalContext());
    }

    public void tearDown() throws Exception {
    }
}
