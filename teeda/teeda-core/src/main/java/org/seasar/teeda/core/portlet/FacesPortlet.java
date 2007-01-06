/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
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
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.faces.FacesConfigBuilder;
import org.seasar.teeda.core.config.faces.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.webapp.WebappConfigBuilder;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shinsuke
 *
 */
public class FacesPortlet extends GenericPortlet {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(FacesPortlet.class);

    public static final String PORTLET_CONFIG = "javax.portlet.PortletConfig";

    public static final String VIEW_ID = FacesPortlet.class.getName()
            + ".VIEW_ID";

    public final String DEFAULT_PAGE = FacesPortlet.class.getName()
            + ".DEFAULT_PAGE";

    public final String DEFAULT_VIEW_PAGE = "view-page";

    public final String DEFAULT_EDIT_PAGE = "edit-page";

    public final String DEFAULT_HELP_PAGE = "help-page";

    protected static final String FACES_INIT_DONE = FacesPortlet.class
            .getName()
            + ".FACES_INIT_DONE";

    public static final String FACES_PORTLET_STATE_PREFIX = FacesPortlet.class
            .getName()
            + ".FACES_PORTLET_STATE_";

    public static final String INIT_PARAMETER = FacesPortlet.class.getName()
            + ".INIT";

    public static final String INIT_VIEW_PARAMETER = FacesPortlet.class
            .getName()
            + ".INIT_VIEW";

    public static final String INIT_EDIT_PARAMETER = FacesPortlet.class
            .getName()
            + ".INIT_EDIT";

    public static final String INIT_HELP_PARAMETER = FacesPortlet.class
            .getName()
            + ".INIT_HELP";

    private static final String PREVIOUS_PORTLET_MODE = FacesPortlet.class
            .getName()
            + ".PREVIOUS_PORTLET_MODE";

    private static final String CURRENT_PORTLET_MODE = FacesPortlet.class
            .getName()
            + ".CURRENT_PORTLET_MODE";

    private static final String EXCLUDED_ATTRIBUTE_LIST = FacesPortlet.class
            .getName()
            + ".EXCLUDED_ATTRIBUTE_LIST";

    private static final String REDEPLOYED_PORTLET = FacesPortlet.class
            .getName()
            + ".REDEPLOYED_PORTLET";

    public static final String REDIRECT_TO_PORTLET = FacesPortlet.class
            .getName()
            + ".REDIRECT_TO_PORTLET";

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
        defaultViewPage = getPortletConfig()
                .getInitParameter(DEFAULT_VIEW_PAGE);
        defaultEditPage = getPortletConfig()
                .getInitParameter(DEFAULT_EDIT_PAGE);
        defaultHelpPage = getPortletConfig()
                .getInitParameter(DEFAULT_HELP_PAGE);
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

            FacesConfig facesConfig = facesConfigBuilder.buildFacesConfigs();

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
                webappConfig = webAppConfigBuilder.build(is,
                        JsfConstants.WEB_XML_PATH);
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

    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        setContentType(request, response);
        setDefaultPage(request, defaultEditPage);
        renderFaces(request, response);
    }

    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        setContentType(request, response);
        setDefaultPage(request, defaultHelpPage);
        renderFaces(request, response);
    }

    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        setContentType(request, response);
        setDefaultPage(request, defaultViewPage);
        renderFaces(request, response);
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

    protected void storePortletConfig(PortletRequest request) {
        request.setAttribute(PORTLET_CONFIG, getPortletConfig());
    }

    protected void setDefaultPage(PortletRequest request, String page) {
        request.setAttribute(DEFAULT_PAGE, page);
    }

    protected void setCurrentPortletMode(PortletRequest request) {
        request.setAttribute(CURRENT_PORTLET_MODE, request.getPortletMode()
                .toString());
    }

    protected void renderFaces(RenderRequest request, RenderResponse response)
            throws PortletException, java.io.IOException {
        if (log.isDebugEnabled()) {
            log.debug("called renderFaces..");
        }

        storePortletConfig(request);
        setCurrentPortletMode(request);

        FacesContext facesContext = facesContextFactory.getFacesContext(
                getPortletContext(), request, response, lifecycle);

        restoreFacesState(facesContext);
        try {
            lifecycle.render(facesContext);
        } catch (Throwable e) {
            handleException(e);
        } finally {
            facesContext.release();
            request.getPortletSession().setAttribute(PREVIOUS_PORTLET_MODE,
                    request.getPortletMode().toString());
            request.getPortletSession().setAttribute(REDEPLOYED_PORTLET,
                    Boolean.FALSE);
        }

    }

    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("called processAction..");
        }

        // check session timeout
        if (checkTimeOut(request)) {
            return;
        }

        executeFaces(request, response);
    }

    protected void executeFaces(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {

        storePortletConfig(request);
        setCurrentPortletMode(request);

        // create excluded list
        ArrayList excludedNameList = new ArrayList();
        Enumeration enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            excludedNameList.add(enu.nextElement());
        }
        // reserved names
        excludedNameList.add(EXCLUDED_ATTRIBUTE_LIST);
        excludedNameList.add(JsfConstants.FACES_CONTEXT);
        request.setAttribute(EXCLUDED_ATTRIBUTE_LIST, excludedNameList);

        FacesContext facesContext = facesContextFactory.getFacesContext(
                getPortletContext(), request, response, lifecycle);

        try {
            lifecycle.execute(facesContext);
            if (!facesContext.getResponseComplete()) {
                // response.setRenderParameter(VIEW_ID, facesContext.getViewRoot().getViewId());
                request.setAttribute(VIEW_ID, facesContext.getViewRoot()
                        .getViewId());
                saveFacesState(facesContext);
            }

        } catch (Throwable e) {
            handleException(e);
        } finally {
            // release the FacesContext instance for this request
            facesContext.release();

        }

    }

    protected void handleException(Throwable e) throws PortletException,
            IOException {

        if (e instanceof IOException) {
            throw (IOException) e;
        } else if (e instanceof PortletException) {
            throw (PortletException) e;
        } else if (e.getMessage() != null) {
            throw new PortletException(e.getMessage(), e);
        }

        throw new PortletException(e);
    }

    protected boolean checkTimeOut(PortletRequest request) {
        return request.getPortletSession(false) == null;
    }

    protected boolean checkTimedOut(FacesContext context) {
        return context.getExternalContext().getSession(false) == null;
    }

    protected boolean checkSessionState(PortletRequest request) {
        return checkTimeOut(request)
                || (request.getPortletSession()
                        .getAttribute(REDEPLOYED_PORTLET) == null);
    }

    protected boolean checkSessionState(FacesContext context) {
        return checkTimedOut(context)
                || (context.getExternalContext().getSessionMap().get(
                        REDEPLOYED_PORTLET) == null);
    }

    protected void restoreFacesState(FacesContext facesContext) {

        PortletSession session = (PortletSession) facesContext
                .getExternalContext().getSession(true);

        String currentPortletMode = (String) facesContext.getExternalContext()
                .getRequestMap().get(CURRENT_PORTLET_MODE);
        String previousPortletMode = (String) session
                .getAttribute(PREVIOUS_PORTLET_MODE);

        FacesPortletState state = null;
        if (previousPortletMode != null
                && previousPortletMode.equals(currentPortletMode)) {
            state = (FacesPortletState) session
                    .getAttribute(FACES_PORTLET_STATE_PREFIX
                            + currentPortletMode);
        } else {
            facesContext.getExternalContext().getRequestMap().put(
                    VIEW_ID,
                    facesContext.getExternalContext().getRequestMap().get(
                            DEFAULT_PAGE));
            // remove all states in session
            for (Iterator i = facesContext.getExternalContext().getSessionMap()
                    .entrySet().iterator(); i.hasNext();) {
                Map.Entry e = (Map.Entry) i.next();
                if (e.getKey() instanceof String) {
                    String key = (String) e.getKey();
                    if (key.startsWith(FACES_PORTLET_STATE_PREFIX)) {
                        facesContext.getExternalContext().getSessionMap()
                                .remove(key);
                    }
                }
            }
        }
        if (state == null) {
            setViewId(facesContext);
            return;
        }

        // set viewId
        facesContext.getExternalContext().getRequestMap().put(VIEW_ID,
                state.getViewRoot().getViewId());

        // check redirect to portlet
        Object redirectToPortlet = state.getRequestMap().get(
                REDIRECT_TO_PORTLET);
        if (redirectToPortlet != null) {
            setViewId(facesContext);
            return;
        }

        // restore message information
        Iterator clientIds = state.getClientIds();
        while (clientIds.hasNext()) {
            String clientId = (String) clientIds.next();
            Iterator msgs = state.getMessages(clientId);
            while (msgs.hasNext()) {
                facesContext.addMessage(clientId, (FacesMessage) msgs.next());
            }
        }
        facesContext.setViewRoot(state.getViewRoot());

        // restore request attributes
        copyMap(state.getRequestMap(), facesContext.getExternalContext()
                .getRequestMap(), new ArrayList());

    }

    private void saveFacesState(FacesContext facesContext) {
        PortletSession session = (PortletSession) facesContext
                .getExternalContext().getSession(true);

        // save portlet mode
        String currentPortletMode = (String) facesContext.getExternalContext()
                .getRequestMap().get(CURRENT_PORTLET_MODE);
        session.setAttribute(PREVIOUS_PORTLET_MODE, currentPortletMode);

        Object redirectToPortlet = facesContext.getExternalContext()
                .getRequestMap().get(REDIRECT_TO_PORTLET);

        FacesPortletState state = new FacesPortletState();
        state.setViewRoot(facesContext.getViewRoot());

        if (redirectToPortlet == null) {
            // save message information from this FacesContext
            Iterator clientIds = facesContext.getClientIdsWithMessages();
            while (clientIds.hasNext()) {
                String clientId = (String) clientIds.next();
                Iterator msgs = facesContext.getMessages(clientId);
                while (msgs.hasNext()) {
                    state.addMessage(clientId, (FacesMessage) msgs.next());
                }
            }

            List excludedNameList = (List) facesContext.getExternalContext()
                    .getRequestMap().get(EXCLUDED_ATTRIBUTE_LIST);
            // save request attributes
            Map requestMap = new HashMap();
            copyMap(facesContext.getExternalContext().getRequestMap(),
                    requestMap, excludedNameList);
            state.setRequestMap(requestMap);
        } else {
            // save request attributes
            Map requestMap = new HashMap();
            requestMap.put(REDIRECT_TO_PORTLET, redirectToPortlet);
            state.setRequestMap(requestMap);
        }

        session.setAttribute(FACES_PORTLET_STATE_PREFIX + currentPortletMode,
                state);

    }

    private void copyMap(Map fromMap, Map toMap, List excludedNameList) {
        for (Iterator i = fromMap.entrySet().iterator(); i.hasNext();) {
            Map.Entry e = (Map.Entry) i.next();
            if (!toMap.containsKey((String) e.getKey())
                    && !excludedNameList.contains((String) e.getKey())) {
                toMap.put(e.getKey(), e.getValue());
            }
        }
    }

    private String getCurrentViewId(FacesContext facesContext) {
        Map requestMap = facesContext.getExternalContext().getRequestMap();
        Map requestParameterMap = facesContext.getExternalContext()
                .getRequestParameterMap();

        String viewId = (String) requestParameterMap.get(VIEW_ID);
        if (viewId != null && !checkSessionState(facesContext)) {
            return viewId;
        }
        viewId = (String) requestMap.get(VIEW_ID);
        if (viewId != null && !checkSessionState(facesContext)) {
            return viewId;
        }
        return (String) requestMap.get(DEFAULT_PAGE);
    }

    private void setViewId(FacesContext facesContext) {
        String viewId = getCurrentViewId(facesContext);
        facesContext.getApplication().getViewHandler().restoreView(
                facesContext, viewId);
        if (facesContext.getViewRoot() == null) {
            facesContext.setViewRoot(facesContext.getApplication()
                    .getViewHandler().createView(facesContext, viewId));
            facesContext.getExternalContext().getRequestMap().put(VIEW_ID,
                    viewId);
        } else {
            facesContext.getViewRoot().setViewId(viewId);
        }

        // context.getViewRoot().setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
    }

    private class FacesPortletState {

        private Map messages = new HashMap();

        private Map requestMap = null;

        public void addMessage(String clientId, FacesMessage message) {
            List list = (List) messages.get(clientId);
            if (list == null) {
                list = new ArrayList();
                messages.put(clientId, list);
            }
            list.add(message);
        }

        public Iterator getMessages(String clientId) {
            List list = (List) messages.get(clientId);
            if (list != null) {
                return (list.iterator());
            } else {
                return (Collections.EMPTY_LIST.iterator());
            }
        }

        public StringBuffer getMessagesBuffer(String clientId) {
            List list = (List) messages.get(clientId);
            StringBuffer buffer = new StringBuffer();
            if (list != null) {
                Iterator messages = list.iterator();
                FacesMessage message;
                while (messages.hasNext()) {
                    message = (FacesMessage) messages.next();
                    buffer.append(" ");
                    buffer.append(message.getDetail());
                }
            }
            return buffer;
        }

        // iterate over the client ids in this view
        public Iterator getClientIds() {
            return (messages.keySet().iterator());
        }

        // The UIViewRoot that is the root of our component tree
        private UIViewRoot viewRoot;

        public UIViewRoot getViewRoot() {
            return this.viewRoot;
        }

        public void setViewRoot(UIViewRoot viewRoot) {
            this.viewRoot = viewRoot;
        }

        /**
         * @return Returns the requestMap.
         */
        public Map getRequestMap() {
            return requestMap;
        }

        /**
         * @param requestMap The requestMap to set.
         */
        public void setRequestMap(Map requestMap) {
            this.requestMap = requestMap;
        }

    }

}
