/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.portlet;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.faces.FacesConfigBuilder;
import org.seasar.teeda.core.config.faces.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.webapp.WebappConfigBuilder;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.context.portlet.PortletExternalContextImpl;
import org.seasar.teeda.core.context.portlet.PortletFacesContextImpl;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shinsuke
 * 
 */
public class FacesPortlet extends GenericPortlet {

    public static final String VIEW_ID = FacesPortlet.class.getName()
            + ".VIEW_ID";

    protected static final String CURRENT_FACES_CONTEXT = FacesPortlet.class
            .getName()
            + ".CURRENT_FACES_CONTEXT";

    public final String DEFULT_VIEW_PAGE = "view-page";

    public final String DEFULT_EDIT_PAGE = "edit-page";

    public final String DEFULT_HELP_PAGE = "help-page";

    protected static final String FACES_INIT_DONE = FacesPortlet.class
            .getName()
            + ".FACES_INIT_DONE";

    protected FacesContextFactory facesContextFactory;

    protected Lifecycle lifecycle;

    private String defaultViewPage = null;

    private String defaultEditPage = null;

    private String defaultHelpPage = null;

    public void init() throws PortletException {
        super.init();

        // Initialized Teeda
        initializeFaces(getPortletContext());

        // Load default pages
        defaultViewPage = getPortletConfig().getInitParameter(DEFULT_VIEW_PAGE);
        defaultEditPage = getPortletConfig().getInitParameter(DEFULT_EDIT_PAGE);
        defaultHelpPage = getPortletConfig().getInitParameter(DEFULT_HELP_PAGE);
        if (null == defaultViewPage) {
            // A Faces Portlet is required to have at least the default page
            throw new PortletException(
                    "Portlet "
                            + getPortletConfig().getPortletName()
                            + " is incorrectly configured. No default View page is defined.");
        }
        if (null == defaultEditPage) {
            defaultEditPage = defaultViewPage;
        }
        if (null == defaultHelpPage) {
            defaultHelpPage = defaultViewPage;
        }

        facesContextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

        // Javadoc says: Lifecycle instance is shared across multiple simultaneous requests, it must be
        // implemented in a thread-safe manner.  So we can acquire it here once:
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        lifecycle = lifecycleFactory.getLifecycle(FacesConfigOptions
                .getLifecycleId());
    }

    protected void initializeFaces(PortletContext servletContext) {
        Boolean b = (Boolean) servletContext.getAttribute(FACES_INIT_DONE);
        boolean isAlreadyInitialized = (b != null) ? b.booleanValue() : false;
        if (!isAlreadyInitialized) {
            initializeFacesConfigOptions(servletContext);
            FacesConfigBuilder facesConfigBuilder = (FacesConfigBuilder) DIContainerUtil
                    .getComponent(FacesConfigBuilder.class);

            FacesConfig facesConfig = facesConfigBuilder.createFacesConfigs();

            AssemblerAssembler assembler = (AssemblerAssembler) DIContainerUtil
                    .getComponent(AssemblerAssembler.class);

            assembler.assembleFactories(facesConfig);
            assembler.assembleApplication(facesConfig);
            assembler.assembleManagedBeans(facesConfig);
            assembler.assmbleNavigationRules(facesConfig);
            assembler.assembleLifecycle(facesConfig);
            assembler.assembleRenderKits(facesConfig);

            WebappConfigBuilder webAppConfigBuilder = (WebappConfigBuilder) DIContainerUtil
                    .getComponent(WebappConfigBuilder.class);

            InputStream is = null;
            WebappConfig webappConfig = null;
            try {
                is = servletContext
                        .getResourceAsStream(JsfConstants.WEB_XML_PATH);
                webappConfig = webAppConfigBuilder.build(is);
            } finally {
                InputStreamUtil.close(is);
            }

            servletContext.setAttribute(WebappConfig.class.getName(),
                    webappConfig);

            servletContext.setAttribute(FACES_INIT_DONE, Boolean.TRUE);
        }

    }

    protected void initializeFacesConfigOptions(PortletContext servletContext) {
        FacesConfigOptions.setConfigFiles(servletContext
                .getInitParameter(FacesServlet.CONFIG_FILES_ATTR));
        String savingMethod = servletContext
                .getInitParameter(StateManager.STATE_SAVING_METHOD_PARAM_NAME);
        if (savingMethod != null) {
            FacesConfigOptions
                    .setSavingStateInClient(StateManager.STATE_SAVING_METHOD_CLIENT
                            .equalsIgnoreCase(savingMethod));
        }
        String suffix = servletContext
                .getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);
        if (suffix != null) {
            FacesConfigOptions.setDefaultSuffix(suffix);
        } else {
            FacesConfigOptions.setDefaultSuffix(ViewHandler.DEFAULT_SUFFIX);
        }
        String lifecycleId = servletContext
                .getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
        if (lifecycleId != null) {
            FacesConfigOptions.setLifecycleId(lifecycleId);
        } else {
            FacesConfigOptions
                    .setLifecycleId(LifecycleFactory.DEFAULT_LIFECYCLE);
        }
    }

    public void destroy() {
        super.destroy();
        FactoryFinder.releaseFactories();
    }

    protected void setContentType(RenderRequest request, RenderResponse response) {

        if (response.getContentType() == null) {
            String portalPreferredContentType = request
                    .getResponseContentType();
            if (portalPreferredContentType != null) {
                response.setContentType(portalPreferredContentType);
            } else {
                response.setContentType("text/html");
            }
        }
    }

    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        setContentType(request, response);
        facesRender(request, response, defaultEditPage);
    }

    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        setContentType(request, response);
        facesRender(request, response, defaultHelpPage);
    }

    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        setContentType(request, response);
        facesRender(request, response, defaultViewPage);
    }

    protected void facesRender(RenderRequest request, RenderResponse response,
            String page) throws PortletException, java.io.IOException {

        String viewId = request.getParameter(VIEW_ID);
        if ((viewId == null) || sessionTimedOut(request)) {
            nonFacesRequest(request, response, page);
            return;
        }

        try {
            PortletFacesContextImpl facesContext = (PortletFacesContextImpl) request
                    .getPortletSession().getAttribute(CURRENT_FACES_CONTEXT);

            // TODO: not sure if this can happen.  Also double check this against spec section 2.1.3
            if (facesContext.getResponseComplete())
                return;

            facesContext.setExternalContext(new PortletExternalContextImpl(
                    getPortletContext(), request, response));
            lifecycle.render(facesContext);
        } catch (Throwable e) {
            handleExceptionFromLifecycle(e);
        }
    }

    protected void nonFacesRequest(RenderRequest request,
            RenderResponse response, String view) throws PortletException {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);
        Application application = appFactory.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        FacesContext facesContext = facesContextFactory.getFacesContext(
                getPortletContext(), request, response, lifecycle);
        UIViewRoot viewRoot = viewHandler.createView(facesContext, view);
        viewRoot.setViewId(view);
        facesContext.setViewRoot(viewRoot);
        lifecycle.render(facesContext);
    }

    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {

        // check session timeout
        if (sessionTimedOut(request))
            return;

        FacesContext facesContext = facesContextFactory.getFacesContext(
                getPortletContext(), request, response, lifecycle);

        try {
            lifecycle.execute(facesContext);

            if (!facesContext.getResponseComplete()) {
                response.setRenderParameter(VIEW_ID, facesContext.getViewRoot()
                        .getViewId());
            }

            request.getPortletSession().setAttribute(CURRENT_FACES_CONTEXT,
                    facesContext);
        } catch (Throwable e) {
            facesContext.release();
            handleExceptionFromLifecycle(e);
        }
    }

    protected void handleExceptionFromLifecycle(Throwable e)
            throws PortletException, IOException {

        if (e instanceof IOException) {
            throw (IOException) e;
        } else if (e instanceof PortletException) {
            throw (PortletException) e;
        } else if (e.getMessage() != null) {
            throw new PortletException(e.getMessage(), e);
        }

        throw new PortletException(e);
    }

    protected boolean sessionTimedOut(PortletRequest request) {
        return request.getPortletSession(false) == null;
    }
}
