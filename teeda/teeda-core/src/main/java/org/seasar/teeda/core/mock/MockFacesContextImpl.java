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
package org.seasar.teeda.core.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

/**
 * @author shot
 * @author manhole
 */
public class MockFacesContextImpl extends MockFacesContext {

    private UIViewRoot root_ = null;

    private ExternalContext context_;

    private Application application_;

    private Map messages_ = new HashMap();

    private ResponseWriter responseWriter_;

    private boolean renderResponse_;

    public MockFacesContextImpl() {
        setCurrentInstance(this);
    }

    public MockFacesContextImpl(ExternalContext context, Application application) {
        context_ = context;
        application_ = application;
        setCurrentInstance(this);
    }

    public Application getApplication() {
        return application_;
    }

    public Iterator getClientIdsWithMessages() {
        return null;
    }

    public ExternalContext getExternalContext() {
        return context_;
    }

    public Severity getMaximumSeverity() {
        return null;
    }

    public Iterator getMessages() {
        return messages_.values().iterator();
    }

    public Iterator getMessages(String clientId) {
        Object o = messages_.get(clientId);
        List list = new ArrayList();
        list.add(o);
        return list.iterator();
    }

    public RenderKit getRenderKit() {
        return null;
    }

    public boolean getRenderResponse() {
        return renderResponse_;
    }

    public boolean getResponseComplete() {
        return false;
    }

    public ResponseStream getResponseStream() {
        return null;
    }

    public void setResponseStream(ResponseStream responseStream) {
    }

    public ResponseWriter getResponseWriter() {
        return responseWriter_;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        responseWriter_ = responseWriter;
    }

    public UIViewRoot getViewRoot() {
        return root_;
    }

    public void setViewRoot(UIViewRoot root) {
        root_ = root;
    }

    public void addMessage(String clientId, FacesMessage message) {
        messages_.put(clientId, message);
    }

    public void release() {
    }

    public void renderResponse() {
        renderResponse_ = true;
    }

    public void responseComplete() {
    }

    public void setExternalContext(ExternalContext context) {
        context_ = context;
    }

    public void setApplication(Application application) {
        application_ = application;
    }

}
