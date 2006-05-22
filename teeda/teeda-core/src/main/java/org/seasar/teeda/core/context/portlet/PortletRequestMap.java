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

import org.seasar.teeda.core.context.AbstractExternalContextMap;

/**
 * PortletRequestMap is RequestMap implementation for Portlet environment.
 * 
 * @author shinsuke
 * 
 */
public class PortletRequestMap extends AbstractExternalContextMap {
    private final PortletRequest portletRequest_;

    PortletRequestMap(PortletRequest portletRequest) {
        portletRequest_ = portletRequest;
    }

    protected Object getAttribute(String key) {
        return portletRequest_.getAttribute(key);
    }

    protected void setAttribute(String key, Object value) {
        portletRequest_.setAttribute(key, value);
    }

    protected void removeAttribute(String key) {
        portletRequest_.removeAttribute(key);
    }

    protected Enumeration getAttributeNames() {
        return portletRequest_.getAttributeNames();
    }

    public void putAll(Map t) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }
}
