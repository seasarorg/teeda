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

import java.util.Enumeration;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.teeda.core.context.AbstractExternalContextMap;

/**
 * @author manhole
 */
public class MockServletRequestParameterMap extends AbstractExternalContextMap {

    private final MockHttpServletRequest request_;

    public MockServletRequestParameterMap(final MockHttpServletRequest request) {
        request_ = request;
    }

    protected Object getAttribute(String key) {
        return request_.getParameter(key);
    }

    protected Enumeration getAttributeNames() {
        return request_.getParameterNames();
    }

    protected void setAttribute(String key, Object value) {
        request_.setParameter(key, (String) value);
    }

    protected void removeAttribute(String key) {
        request_.setParameter(key, (String) null);
    }

}
