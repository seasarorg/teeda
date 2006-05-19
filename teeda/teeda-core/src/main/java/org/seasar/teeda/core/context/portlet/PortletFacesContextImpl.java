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
package org.seasar.teeda.core.context.portlet;

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
 * @author shot
 * 
 */
public class PortletFacesContextImpl extends FacesContext {

    //TODO impl these
    
    public PortletFacesContextImpl(ExternalContext externalContext) {
        
    }
    
    public Application getApplication() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getClientIdsWithMessages() {
        // TODO Auto-generated method stub
        return null;
    }

    public ExternalContext getExternalContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public Severity getMaximumSeverity() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getMessages() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getMessages(String clientId) {
        // TODO Auto-generated method stub
        return null;
    }

    public RenderKit getRenderKit() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean getRenderResponse() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean getResponseComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    public ResponseStream getResponseStream() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setResponseStream(ResponseStream responseStream) {
        // TODO Auto-generated method stub

    }

    public ResponseWriter getResponseWriter() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        // TODO Auto-generated method stub

    }

    public UIViewRoot getViewRoot() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setViewRoot(UIViewRoot root) {
        // TODO Auto-generated method stub

    }

    public void addMessage(String clientId, FacesMessage message) {
        // TODO Auto-generated method stub

    }

    public void release() {
        // TODO Auto-generated method stub

    }

    public void renderResponse() {
        // TODO Auto-generated method stub

    }

    public void responseComplete() {
        // TODO Auto-generated method stub

    }

}
