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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author manhole
 * @author shot
 */
public class MessageBean {

    private static final FacesMessage.Severity[] INFO = new FacesMessage.Severity[] {
            FacesMessage.SEVERITY_INFO, FacesMessage.SEVERITY_WARN,
            FacesMessage.SEVERITY_ERROR, FacesMessage.SEVERITY_FATAL };

    private static final FacesMessage.Severity[] WARN = new FacesMessage.Severity[] {
            FacesMessage.SEVERITY_WARN, FacesMessage.SEVERITY_ERROR,
            FacesMessage.SEVERITY_FATAL };

    private static final FacesMessage.Severity[] ERROR = new FacesMessage.Severity[] {
            FacesMessage.SEVERITY_ERROR, FacesMessage.SEVERITY_FATAL };

    private static final FacesMessage.Severity[] FATAL = new FacesMessage.Severity[] { FacesMessage.SEVERITY_FATAL };

    private static Map map = new HashMap();
    static {
        map.put(FacesMessage.SEVERITY_INFO.toString(), INFO);
        map.put(FacesMessage.SEVERITY_WARN.toString(), WARN);
        map.put(FacesMessage.SEVERITY_ERROR.toString(), ERROR);
        map.put(FacesMessage.SEVERITY_FATAL.toString(), FATAL);
    }

    public FacesMessage[] getMessagesBySeverity(FacesMessage.Severity severity) {
        FacesContext context = getFacesContext();
        List list = new ArrayList();
        String type = severity.toString();
        FacesMessage.Severity[] severities = (FacesMessage.Severity[]) map.get(type);
        for (Iterator it = context.getMessages(); it.hasNext();) {
            FacesMessage message = (FacesMessage) it.next();
            for(int i = 0; i < severities.length; i++) {
                if (message.getSeverity().equals(severities[i])) {
                    list.add(message);
                }
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
