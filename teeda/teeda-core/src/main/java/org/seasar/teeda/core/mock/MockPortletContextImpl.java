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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletRequestDispatcher;

/**
 * @author shot
 * 
 */
public class MockPortletContextImpl implements MockPortletContext {

    public String getServerInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public PortletRequestDispatcher getRequestDispatcher(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public PortletRequestDispatcher getNamedDispatcher(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public InputStream getResourceAsStream(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getMajorVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMinorVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getMimeType(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRealPath(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Set getResourcePaths(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public URL getResource(String arg0) throws MalformedURLException {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getAttribute(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getInitParameter(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getInitParameterNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public void log(String arg0) {
        // TODO Auto-generated method stub

    }

    public void log(String arg0, Throwable arg1) {
        // TODO Auto-generated method stub

    }

    public void removeAttribute(String arg0) {
        // TODO Auto-generated method stub

    }

    public void setAttribute(String arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    public String getPortletContextName() {
        // TODO Auto-generated method stub
        return null;
    }

}
