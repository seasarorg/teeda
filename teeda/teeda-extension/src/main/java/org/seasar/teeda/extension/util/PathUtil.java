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
package org.seasar.teeda.extension.util;

import java.util.Iterator;
import java.util.LinkedList;

import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;

/**
 * @author koichik
 * @since 1.0.12
 */
public abstract class PathUtil {

    public static String toAbsolutePath(final FacesContext context,
            final String path, final String basePath) {
        if (path.charAt(0) == '/' || path.indexOf(':') != -1) {
            return path;
        }
        if (StringUtil.isEmpty(basePath)) {
            return path;
        }
        final String contextRoot = context.getExternalContext()
                .getRequestContextPath();
        final String absolutePath = contextRoot + basePath + "/../" + path;
        return normalizePath(absolutePath);
    }

    protected static String normalizePath(final String absolutePath) {
        final LinkedList list = new LinkedList();
        final String[] parts = absolutePath.substring(1).split("/");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            if ("..".equals(part)) {
                list.removeLast();
            } else if (!".".equals(part)) {
                list.addLast(part);
            }
        }
        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return "/" + list.getFirst();
        }
        final StringBuffer buf = new StringBuffer(absolutePath.length());
        for (final Iterator it = list.iterator(); it.hasNext();) {
            buf.append('/').append(it.next());
        }
        return new String(buf);
    }

}
