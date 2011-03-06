/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package javax.faces.mock.servlet;

import java.util.Locale;

import javax.servlet.ServletRequest;

/**
 * @author shot
 */
public interface MockServletRequest extends ServletRequest {

    public String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";

    public void setContentLength(int contentLength);

    public void setContentType(String contentType);

    public void addParameter(String name, String value);

    public void addParameter(String name, String[] values);

    public void setParameter(String name, String value);

    public void setParameter(String name, String[] values);

    public void setProtocol(String protocol);

    public void setScheme(String scheme);

    public void setServerName(String serverName);

    public void setServerPort(int serverPort);

    public void setRemoteAddr(String remoteAddr);

    public void setRemoteHost(String remoteHost);

    public void setLocalAddr(String localAddr);

    public void setLocalName(String localName);

    public void setLocalPort(int localPort);

    public void setRemotePort(int remotePort);

    public void setLocale(Locale locale);
}