/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.context.portlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.FactoryFinderUtil;
import javax.faces.render.RenderKit;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.context.Releaseable;
import org.seasar.teeda.core.util.ApplicationUtil;

/**
 * @author shot
 * @author shinsuke
 *
 */
public class PortletFacesContextImpl extends FacesContext {

    private ExternalContext externalContext;

    private Application application;

    private boolean released = false;

    private boolean renderResponse = false;

    private boolean responseComplete = false;

    private ResponseWriter responseWriter;

    private UIViewRoot root;

    private List messages;

    private List messageClientIds;

    private FacesMessage.Severity maxSeverity;

    private ResponseStream responseStream;

    public PortletFacesContextImpl(ExternalContext externalContext) {
        this.externalContext = externalContext;
        this.application = ApplicationUtil.getApplicationFromFactory();
        FacesContext.setCurrentInstance(this);
    }

    public Application getApplication() {
        assertNotReleased();
        return application;
    }

    public Iterator getClientIdsWithMessages() {
        assertNotReleased();
        if (messages == null) {
            return Collections.EMPTY_LIST.iterator();
        }
        return messageClientIds.iterator();
    }

    public ExternalContext getExternalContext() {
        assertNotReleased();
        return externalContext;
    }

    public Severity getMaximumSeverity() {
        assertNotReleased();
        return maxSeverity;
    }

    public Iterator getMessages() {
        assertNotReleased();
        return (messages != null) ? messages.iterator()
                : Collections.EMPTY_LIST.iterator();
    }

    public Iterator getMessages(String clientId) {
        assertNotReleased();
        if (messages == null) {
            return Collections.EMPTY_LIST.iterator();
        }
        if (messages.size() != messageClientIds.size()) {
            throw new IllegalStateException();
        }
        List list = new ArrayList();
        for (int i = 0; i < messages.size(); i++) {
            String savedClientId = (String) messageClientIds.get(i);
            if (stringEquals(clientId, savedClientId)) {
                list.add(messages.get(i));
            }
        }
        return list.iterator();
    }

    private boolean stringEquals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    public RenderKit getRenderKit() {
        assertNotReleased();
        UIViewRoot root = getViewRoot();
        if (root == null) {
            return null;
        }
        String renderKitId = root.getRenderKitId();
        if (renderKitId == null) {
            return null;
        }
        return FactoryFinderUtil.getRenderKitFactory().getRenderKit(this,
                renderKitId);
    }

    public boolean getRenderResponse() {
        assertNotReleased();
        return renderResponse;
    }

    public boolean getResponseComplete() {
        assertNotReleased();
        return responseComplete;
    }

    public ResponseStream getResponseStream() {
        assertNotReleased();
        return responseStream;
    }

    public void setResponseStream(ResponseStream responseStream) {
        assertNotReleased();
        AssertionUtil.assertNotNull("responseStream", responseStream);
        this.responseStream = responseStream;
    }

    public ResponseWriter getResponseWriter() {
        assertNotReleased();
        return responseWriter;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        assertNotReleased();
        AssertionUtil.assertNotNull("responseWriter", responseWriter);
        this.responseWriter = responseWriter;
    }

    public UIViewRoot getViewRoot() {
        assertNotReleased();
        return root;
    }

    public void setViewRoot(UIViewRoot root) {
        assertNotReleased();
        AssertionUtil.assertNotNull("root", root);
        this.root = root;
    }

    public void addMessage(String clientId, FacesMessage message) {
        assertNotReleased();
        AssertionUtil.assertNotNull("message", message);
        if (messages == null) {
            messages = new ArrayList();
            messageClientIds = new ArrayList();
        }
        messages.add(message);
        messageClientIds.add(clientId);
        FacesMessage.Severity severity = message.getSeverity();
        setSeverity(severity);
    }

    public void release() {
        released = true;
        application = null;
        if (externalContext instanceof Releaseable) {
            ((Releaseable) externalContext).release();
        }
        externalContext = null;
        FacesContext.setCurrentInstance(null);
    }

    public void renderResponse() {
        assertNotReleased();
        renderResponse = true;
    }

    public void responseComplete() {
        assertNotReleased();
        responseComplete = true;
    }

    private void setSeverity(FacesMessage.Severity severity) {
        if (severity != null) {
            if (maxSeverity == null || severity.compareTo(maxSeverity) > 0) {
                maxSeverity = severity;
            }
        }
    }

    private void assertNotReleased() {
        if (released) {
            throw new IllegalStateException("already released");
        }
    }

    public void setExternalContext(ExternalContext externalContext) {
        assertNotReleased();
        this.externalContext = externalContext;
        //TODO this code is needed Portlet render method. needs to review FacesContext handling..
        FacesContext.setCurrentInstance(this);
    }

}
