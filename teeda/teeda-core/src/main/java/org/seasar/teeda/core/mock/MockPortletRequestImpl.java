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
package org.seasar.teeda.core.mock;

import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;

/**
 * @author shot
 * 
 */
public class MockPortletRequestImpl implements MockPortletRequest {

    public boolean isWindowStateAllowed(WindowState arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isPortletModeAllowed(PortletMode arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public PortletMode getPortletMode() {
        // TODO Auto-generated method stub
        return null;
    }

    public WindowState getWindowState() {
        // TODO Auto-generated method stub
        return null;
    }

    public PortletPreferences getPreferences() {
        // TODO Auto-generated method stub
        return null;
    }

    public PortletSession getPortletSession() {
        // TODO Auto-generated method stub
        return null;
    }

    public PortletSession getPortletSession(boolean arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getProperties(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getPropertyNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public PortalContext getPortalContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAuthType() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getContextPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRemoteUser() {
        // TODO Auto-generated method stub
        return null;
    }

    public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isUserInRole(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public Object getAttribute(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getParameter(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getParameterNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getParameterValues(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getParameterMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isSecure() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setAttribute(String arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    public void removeAttribute(String arg0) {
        // TODO Auto-generated method stub

    }

    public String getRequestedSessionId() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        // TODO Auto-generated method stub
        return false;
    }

    public String getResponseContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getResponseContentTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getLocales() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getScheme() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getServerName() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getServerPort() {
        // TODO Auto-generated method stub
        return 0;
    }

}
