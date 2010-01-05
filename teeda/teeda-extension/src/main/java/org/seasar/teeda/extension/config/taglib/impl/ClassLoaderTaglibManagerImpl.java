/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.config.taglib.impl;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author higa
 *  
 */
public class ClassLoaderTaglibManagerImpl extends AbstractTaglibManager {

    public void init() {
        scanJars(getClass().getClassLoader());
    }

    public void scanJars(ClassLoader classLoader) {
        while (classLoader != null) {
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) classLoader).getURLs();
                for (int i = 0; i < urls.length; ++i) {
                    JarURLConnection conn = openJarURLConnection(urls[i]);
                    if (conn != null) {
                        scanJar(conn);
                    }
                }
            }
            classLoader = classLoader.getParent();
        }
    }
}