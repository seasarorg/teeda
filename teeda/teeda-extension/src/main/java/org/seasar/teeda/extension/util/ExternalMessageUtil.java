/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.util;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;

/**
 * @author koichik
 */
public class ExternalMessageUtil {

    public static final String REQUEST_ATTRIBUTE_MESSAGES = ExternalMessageUtil.class
            .getName() +
            ".Messages";

    public static final Object[] EMPTY_PARAMS = new Object[0];

    public static void addMessage(final ServletRequest request,
            final String message) {
        addMessage(request, message, EMPTY_PARAMS);
    }

    public static void addMessage(final ServletRequest request,
            final String message, final Object[] params) {
        final Map messages = getOrCreateMessages(request);
        messages.put(message, params);
    }

    public static void addMessage(final PortletRequest request,
            final String message) {
        addMessage(request, message, EMPTY_PARAMS);
    }

    public static void addMessage(final PortletRequest request,
            final String message, final Object[] params) {
        final Map messages = getOrCreateMessages(request);
        messages.put(message, params);
    }

    public static Map getMessages(final FacesContext context) {
        final Map request = context.getExternalContext().getRequestMap();
        return (Map) request.get(REQUEST_ATTRIBUTE_MESSAGES);
    }

    protected static Map getOrCreateMessages(final ServletRequest request) {
        Map messages = (Map) request.getAttribute(REQUEST_ATTRIBUTE_MESSAGES);
        if (messages == null) {
            messages = new LinkedHashMap();
            request.setAttribute(REQUEST_ATTRIBUTE_MESSAGES, messages);
        }
        return messages;
    }

    protected static Map getOrCreateMessages(final PortletRequest request) {
        Map messages = (Map) request.getAttribute(REQUEST_ATTRIBUTE_MESSAGES);
        if (messages == null) {
            messages = new LinkedHashMap();
            request.setAttribute(REQUEST_ATTRIBUTE_MESSAGES, messages);
        }
        return messages;
    }

}
