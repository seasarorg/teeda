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
package org.seasar.teeda.extension.html.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.html.HtmlPathCache;

/**
 * @author higa
 * 
 */
public class HtmlPathCacheImpl implements HtmlPathCache {

    private Map paths = new HashMap();

    public String getName(String path) {
        int pos = path.lastIndexOf('/');
        int pos2 = path.lastIndexOf('.');
        if (pos < 0 || pos2 < 0 || pos2 < pos) {
            throw new IllegalArgumentException("path:" + path);
        }
        return path.substring(pos + 1, pos2);
    }

    public String getPath(String name) {
        return (String) paths.get(name);
    }

    public void setPath(String name, String path) {
        paths.put(name, path);
    }

}