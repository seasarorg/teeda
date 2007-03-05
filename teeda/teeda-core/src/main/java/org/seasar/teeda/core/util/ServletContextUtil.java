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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContext;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.URLUtil;

/**
 * @author higa
 */
public class ServletContextUtil {

    private ServletContextUtil() {
    }

    public static URL getResource(ServletContext servletContext, String path) {
        try {
            return servletContext.getResource(path);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static InputStream getResourceAsStream(
            ServletContext servletContext, String path) {
        URL url = getResource(servletContext, path);
        if (url == null) {
            return null;
        }
        return URLUtil.openStream(url);
    }
}
