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
package org.seasar.teeda.core.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author manhole
 * @author shot
 */
public class MessageBean {

    public FacesMessage[] getMessagesBySeverity(FacesMessage.Severity severity) {
        FacesContext context = getFacesContext();
        List list = new ArrayList();
        for (Iterator it = context.getMessages(); it.hasNext();) {
            FacesMessage message = (FacesMessage) it.next();
            if (message.getSeverity().equals(severity)) {
                list.add(message);
            }
        }
        return (FacesMessage[]) list.toArray(new FacesMessage[list.size()]);
    }

    public boolean isEmpty() {
        FacesContext context = getFacesContext();
        Iterator it = context.getMessages();
        return !it.hasNext();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
