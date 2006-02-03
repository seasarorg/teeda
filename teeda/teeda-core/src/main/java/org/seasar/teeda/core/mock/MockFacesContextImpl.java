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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.servlet.ServletResponse;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.util.FactoryFinderUtil;

/**
 * @author shot
 * @author manhole
 */
public class MockFacesContextImpl extends MockFacesContext {

    private UIViewRoot viewRoot_ = null;

    private MockExternalContext externalContext_;

    private Application application_;

    private Map messages_ = new HashMap();

    private ResponseWriter responseWriter_;

    private boolean renderResponse_;

    public MockFacesContextImpl() {
        setCurrentInstance(this);
    }

    public MockFacesContextImpl(MockExternalContext externalContext) {
        externalContext_ = externalContext;
        application_ = FactoryFinderUtil.getApplicationFactory()
                .getApplication();
        setCurrentInstance(this);
    }

    public MockFacesContextImpl(MockExternalContext context,
            Application application) {
        externalContext_ = context;
        application_ = application;
        setCurrentInstance(this);
    }

    public Application getApplication() {
        if (application_ == null) {
            application_ = new MockApplication();
        }
        return application_;
    }

    public Iterator getClientIdsWithMessages() {
        return null;
    }

    public ExternalContext getExternalContext() {
        if (externalContext_ == null) {
            externalContext_ = new MockExternalContextImpl();
        }
        return externalContext_;
    }

    public Severity getMaximumSeverity() {
        return null;
    }

    public Iterator getMessages() {
        List all = new ArrayList();
        for (Iterator it = messages_.values().iterator(); it.hasNext();) {
            List messages = (List) it.next();
            all.addAll(messages);
        }
        return all.iterator();
    }

    public Iterator getMessages(String clientId) {
        return getMessagesList(clientId).iterator();
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
        if (responseWriter_ == null) {
            HtmlResponseWriter responseWriter = new HtmlResponseWriter();
            ServletResponse response = (ServletResponse) getExternalContext()
                    .getResponse();
            try {
                responseWriter.setWriter(response.getWriter());
                responseWriter_ = responseWriter;
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
        }
        return responseWriter_;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        responseWriter_ = responseWriter;
    }

    public UIViewRoot getViewRoot() {
        if (viewRoot_ == null) {
            viewRoot_ = new UIViewRoot();
            viewRoot_.setLocale(Locale.getDefault());
        }
        return viewRoot_;
    }

    public void setViewRoot(UIViewRoot root) {
        viewRoot_ = root;
    }

    public void addMessage(String clientId, FacesMessage message) {
        List l = getMessagesList(clientId);
        l.add(message);
    }

    private List getMessagesList(String clientId) {
        List l = (List) messages_.get(clientId);
        if (l == null) {
            l = new ArrayList();
            messages_.put(clientId, l);
        }
        return l;
    }

    public void release() {
        setCurrentInstance(null);
    }

    public void renderResponse() {
        renderResponse_ = true;
    }

    public void responseComplete() {
    }

    public void setExternalContext(ExternalContext context) {
        setMockExternalContext((MockExternalContext) context);
    }

    public void setMockExternalContext(MockExternalContext context) {
        externalContext_ = context;
    }

    public void setApplication(Application application) {
        application_ = application;
    }

    public MockExternalContext getMockExternalContext() {
        return externalContext_;
    }

}
