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

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.seasar.framework.util.EmptyEnumeration;
import org.seasar.teeda.core.context.AbstractExternalContextMap;

/**
 * PortletSessionMap is SessionMap implementation for Portlet environment.
 * 
 * @author shinsuke
 * 
 */
public class PortletSessionMap extends AbstractExternalContextMap {

    private static final Enumeration EMPTY_ENUMERATION = new EmptyEnumeration();

    private final PortletRequest portletRequest_;

    PortletSessionMap(PortletRequest portletRequest) {
        portletRequest_ = portletRequest;
    }

    protected Object getAttribute(String key) {
        PortletSession portletSession = getSession();
        return (portletSession == null) ? null : portletSession.getAttribute(
                key.toString(), PortletSession.PORTLET_SCOPE);
    }

    protected void setAttribute(String key, Object value) {
        portletRequest_.getPortletSession(true).setAttribute(key, value,
                PortletSession.PORTLET_SCOPE);
    }

    protected void removeAttribute(String key) {
        PortletSession portletSession = getSession();
        if (portletSession != null) {
            portletSession.removeAttribute(key, PortletSession.PORTLET_SCOPE);
        }
    }

    protected Enumeration getAttributeNames() {
        PortletSession portletSession = getSession();
        return (portletSession == null) ? EMPTY_ENUMERATION : portletSession
                .getAttributeNames(PortletSession.PORTLET_SCOPE);
    }

    private PortletSession getSession() {
        return portletRequest_.getPortletSession(false);
    }

    public void putAll(Map t) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }
}