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
package javax.faces.context;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKit;

/**
 * @author shot
 */
public abstract class FacesContext {

    private static ThreadLocal threadLocal = new ThreadLocal();

    public abstract javax.faces.application.Application getApplication();

    public abstract Iterator getClientIdsWithMessages();

    public abstract ExternalContext getExternalContext();

    public abstract FacesMessage.Severity getMaximumSeverity();

    public abstract Iterator getMessages();

    public abstract Iterator getMessages(String clientId);

    public abstract RenderKit getRenderKit();

    public abstract boolean getRenderResponse();

    public abstract boolean getResponseComplete();

    public abstract ResponseStream getResponseStream();

    public abstract void setResponseStream(ResponseStream responseStream);

    public abstract ResponseWriter getResponseWriter();

    public abstract void setResponseWriter(
            javax.faces.context.ResponseWriter responseWriter);

    public abstract UIViewRoot getViewRoot();

    public abstract void setViewRoot(UIViewRoot root);

    public abstract void addMessage(String clientId,
            javax.faces.application.FacesMessage message);

    public abstract void release();

    public abstract void renderResponse();

    public abstract void responseComplete();

    public static FacesContext getCurrentInstance() {
        return (FacesContext) threadLocal.get();
    }

    protected static void setCurrentInstance(
            javax.faces.context.FacesContext context) {
        threadLocal.set(context);
    }

}
