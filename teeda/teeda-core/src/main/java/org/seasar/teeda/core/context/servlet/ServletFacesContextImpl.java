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
package org.seasar.teeda.core.context.servlet;

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
import javax.faces.render.RenderKit;

import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.FactoryFinderUtil;

/**
 * @author shot
 */
public class ServletFacesContextImpl extends FacesContext {

    private ExternalContext externalContext_;

    private Application application_;

    private boolean released_ = false;

    private boolean renderResponse_ = false;

    private boolean responseComplete_ = false;

    private ResponseWriter responseWriter_;

    private UIViewRoot root_;

    private List messages_;

    private List messageClientIds_;

    private FacesMessage.Severity maxSeverity_;

    private ResponseStream responseStream_;

    public ServletFacesContextImpl(ExternalContext externalContext) {
        externalContext_ = externalContext;
        application_ = ApplicationUtil.getApplicationFromFactory();
        FacesContext.setCurrentInstance(this);
    }

    public Application getApplication() {
        assertNotReleased();
        return application_;
    }

    public Iterator getClientIdsWithMessages() {
        assertNotReleased();
        if (messages_ == null) {
            return Collections.EMPTY_LIST.iterator();
        }
        return messageClientIds_.iterator();
    }

    public ExternalContext getExternalContext() {
        assertNotReleased();
        return externalContext_;
    }

    public Severity getMaximumSeverity() {
        assertNotReleased();
        return maxSeverity_;
    }

    public Iterator getMessages() {
        assertNotReleased();
        return (messages_ != null) ? messages_.iterator()
                : Collections.EMPTY_LIST.iterator();
    }

    public Iterator getMessages(String clientId) {
        assertNotReleased();
        if (messages_ == null) {
            return Collections.EMPTY_LIST.iterator();
        }
        if (messages_.size() != messageClientIds_.size()) {
            throw new IllegalStateException();
        }
        List list = new ArrayList();
        for (int i = 0; i < messages_.size(); i++) {
            String savedClientId = (String) messageClientIds_.get(i);
            if (stringEquals(clientId, savedClientId)) {
                list.add(messages_.get(i));
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
        return renderResponse_;
    }

    public boolean getResponseComplete() {
        assertNotReleased();
        return responseComplete_;
    }

    public ResponseStream getResponseStream() {
        assertNotReleased();
        return responseStream_;
    }

    public void setResponseStream(ResponseStream responseStream) {
        assertNotReleased();
        assertNotNull("responseStream", responseStream);
        responseStream_ = responseStream;
    }

    public ResponseWriter getResponseWriter() {
        assertNotReleased();
        return responseWriter_;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        assertNotReleased();
        assertNotNull("responseWriter", responseWriter);
        responseWriter_ = responseWriter;
    }

    public UIViewRoot getViewRoot() {
        assertNotReleased();
        return root_;
    }

    public void setViewRoot(UIViewRoot root) {
        assertNotReleased();
        assertNotNull("root", root);
        root_ = root;
    }

    public void addMessage(String clientId, FacesMessage message) {
        assertNotReleased();
        assertNotNull("message", message);
        if (messages_ == null) {
            messages_ = new ArrayList();
            messageClientIds_ = new ArrayList();
        }
        messages_.add(message);
        messageClientIds_.add(clientId);
        FacesMessage.Severity severity = message.getSeverity();
        setSeverity(severity);
    }

    public void release() {
        released_ = true;
        application_ = null;
        externalContext_ = null;
        FacesContext.setCurrentInstance(null);
    }

    public void renderResponse() {
        assertNotReleased();
        renderResponse_ = true;
    }

    public void responseComplete() {
        assertNotReleased();
        responseComplete_ = true;
    }

    private void setSeverity(FacesMessage.Severity severity) {
        if (severity != null) {
            if (maxSeverity_ == null || severity.compareTo(maxSeverity_) > 0) {
                maxSeverity_ = severity;
            }
        }
    }

    private void assertNotReleased() {
        if (released_) {
            throw new IllegalStateException("already released");
        }
    }

    private void assertNotNull(String message, Object o) {
        if (o == null) {
            throw new NullPointerException(message);
        }
    }
}
