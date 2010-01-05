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
package org.seasar.teeda.core.portlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

/**
 * @author shinsuke
 * 
 */
public class FacesPortletState implements Serializable {

    private static final long serialVersionUID = -2877915312956741979L;

    private Map messages = new HashMap();

    private Map requestMap = null;

    private String viewId = null;

    private Object state = null;

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

    /**
     * @return Returns the viewId.
     */
    public String getViewId() {
        return viewId;
    }

    /**
     * @param viewId The viewId to set.
     */
    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    /**
     * @return Returns the state.
     */
    public Object getState() {
        return state;
    }

    /**
     * @param state The state to set.
     */
    public void setState(Object state) {
        this.state = state;
    }

}
