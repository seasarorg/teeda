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
package org.seasar.teeda.core.mock;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.Cookie;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;

/**
 * @author shot
 */
public abstract class MockExternalContext extends ExternalContext {

    public abstract void addRequestCookieMap(Cookie cookie);

    public abstract void setRequestCookieMap(Map map);

    public abstract void addRequestParameterMap(String key, String value);

    public abstract void setRequestParameterMap(Map map);

    public abstract void setMockServletContext(
            MockServletContext mockServletContext);

    public abstract MockServletContext getMockServletContext();

    public abstract void setMockHttpServletRequest(
            MockHttpServletRequest mockHttpServletRequest);

    public abstract MockHttpServletRequest getMockHttpServletRequest();

    public abstract void setMockHttpServletResponse(
            MockHttpServletResponse mockHttpServletResponse);

    public abstract MockHttpServletResponse getMockHttpServletResponse();

    public abstract void setRequestPathInfo(String pathInfo);
}
