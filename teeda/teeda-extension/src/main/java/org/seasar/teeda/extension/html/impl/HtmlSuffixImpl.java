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
package org.seasar.teeda.extension.html.impl;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.html.HtmlSuffix;

/**
 * @author higa
 * 
 */
public class HtmlSuffixImpl implements HtmlSuffix {

    private static final String KEY = HtmlSuffixImpl.class.getName();

    private char separator = '_';

    /**
     * @return Returns the separator.
     */
    public char getSeparator() {
        return separator;
    }

    /**
     * @param separator The separator to set.
     */
    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setupSuffix(FacesContext context) {
        String viewId = context.getViewRoot().getViewId();
        int pos = viewId.lastIndexOf('.');
        if (pos < 0) {
            return;
        }
        int pos2 = viewId.lastIndexOf('/', pos);
        if (pos2 < 0) {
            return;
        }
        String suffix = null;
        for (int i = pos; i >= pos2; i--) {
            char c = viewId.charAt(i);
            if (c == separator) {
                suffix = viewId.substring(i, pos);
                break;
            }
        }
        if (suffix != null) {
            Map sessionMap = context.getExternalContext().getSessionMap();
            sessionMap.put(KEY, suffix);
        }
    }

    public String normalizePath(String path) {
        AssertionUtil.assertNotNull("path", path);
        String extension = null;
        for (int i = path.length() - 1; i >= 0; i--) {
            char c = path.charAt(i);
            if (c == '.') {
                extension = path.substring(i);
                continue;
            }
            if (c == separator) {
                return path.substring(0, i) + extension;
            }
            if (c == '/') {
                return path;
            }
        }
        return path;
    }

    public String getSuffix(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        Map sessionMap = context.getExternalContext().getSessionMap();
        return (String) sessionMap.get(KEY);
    }

}
