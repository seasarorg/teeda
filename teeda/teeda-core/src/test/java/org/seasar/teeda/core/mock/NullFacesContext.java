/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

/**
 * @author manhole
 */
public class NullFacesContext extends FacesContext {

    public Application getApplication() {
        return null;
    }

    public Iterator getClientIdsWithMessages() {
        return null;
    }

    public ExternalContext getExternalContext() {
        return null;
    }

    public Severity getMaximumSeverity() {
        return null;
    }

    public Iterator getMessages() {
        return null;
    }

    public Iterator getMessages(String clientId) {
        return null;
    }

    public RenderKit getRenderKit() {
        return null;
    }

    public boolean getRenderResponse() {
        return false;
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
        return null;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
    }

    public UIViewRoot getViewRoot() {
        return null;
    }

    public void setViewRoot(UIViewRoot root) {
    }

    public void addMessage(String clientId, FacesMessage message) {
    }

    public void release() {
    }

    public void renderResponse() {
    }

    public void responseComplete() {
    }

}
