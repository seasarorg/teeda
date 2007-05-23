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
package org.seasar.teeda.core.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import javax.faces.internal.FactoryFinderUtil;
import javax.faces.render.RenderKit;
import javax.servlet.ServletResponse;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;

/**
 * @author shot
 * @author manhole
 */
public class MockFacesContextImpl extends MockFacesContext {

    private UIViewRoot viewRoot = null;

    private MockExternalContext externalContext;

    private Application application;

    private Map messages = new LinkedHashMap();

    private ResponseWriter responseWriter;

    private boolean renderResponse;

    private boolean responseComplete;

    public MockFacesContextImpl() {
        setCurrentInstance(this);
    }

    public MockFacesContextImpl(final MockExternalContext externalContext) {
        this.externalContext = externalContext;
        application = FactoryFinderUtil.getApplicationFactory()
                .getApplication();
        setCurrentInstance(this);
    }

    public MockFacesContextImpl(final MockExternalContext context,
            final Application application) {
        externalContext = context;
        this.application = application;
        setCurrentInstance(this);
    }

    public Application getApplication() {
        if (application == null) {
            application = new MockApplicationImpl();
        }
        return application;
    }

    public Iterator getClientIdsWithMessages() {
        return messages.keySet().iterator();
    }

    public ExternalContext getExternalContext() {
        return getMockExternalContext();
    }

    public Severity getMaximumSeverity() {
        return null;
    }

    public void removeMessages() {
        messages = new LinkedHashMap();
    }

    public Iterator getMessages() {
        final List all = new ArrayList();
        for (final Iterator it = messages.values().iterator(); it.hasNext();) {
            final List messages = (List) it.next();
            all.addAll(messages);
        }
        return all.iterator();
    }

    public Iterator getMessages(final String clientId) {
        return getMessagesList(clientId).iterator();
    }

    public RenderKit getRenderKit() {
        return null;
    }

    public boolean getRenderResponse() {
        return renderResponse;
    }

    public boolean getResponseComplete() {
        return responseComplete;
    }

    public ResponseStream getResponseStream() {
        return null;
    }

    public void setResponseStream(final ResponseStream responseStream) {
    }

    public ResponseWriter getResponseWriter() {
        if (responseWriter == null) {
            final HtmlResponseWriter responseWriter = new HtmlResponseWriter();
            final ServletResponse response = getMockExternalContext()
                    .getMockHttpServletResponse();
            try {
                responseWriter.setWriter(response.getWriter());
                this.responseWriter = responseWriter;
            } catch (final IOException e) {
                throw new IORuntimeException(e);
            }
        }
        return responseWriter;
    }

    public void setResponseWriter(final ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }

    public UIViewRoot getViewRoot() {
        if (viewRoot == null) {
            viewRoot = new UIViewRoot();
            viewRoot.setLocale(Locale.getDefault());
        }
        return viewRoot;
    }

    public void setViewRoot(final UIViewRoot root) {
        viewRoot = root;
    }

    public void addMessage(final String clientId, final FacesMessage message) {
        final List l = getMessagesList(clientId);
        l.add(message);
    }

    private List getMessagesList(final String clientId) {
        List l = (List) messages.get(clientId);
        if (l == null) {
            l = new ArrayList();
            messages.put(clientId, l);
        }
        return l;
    }

    public void release() {
        setCurrentInstance(null);
    }

    public void renderResponse() {
        renderResponse = true;
    }

    public void responseComplete() {
        responseComplete = true;
    }

    public void setExternalContext(final ExternalContext context) {
        setMockExternalContext((MockExternalContext) context);
    }

    public void setMockExternalContext(final MockExternalContext context) {
        externalContext = context;
    }

    public void setApplication(final Application application) {
        this.application = application;
    }

    public MockExternalContext getMockExternalContext() {
        if (externalContext == null) {
            externalContext = new MockExternalContextImpl();
        }
        return externalContext;
    }

}
