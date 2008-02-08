/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.FileInputStreamUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.util.FileSystemTraversal;

/**
 * @author manhole
 */
public class FileSystemTaglibManagerImpl extends AbstractTaglibManager {

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void init() {
        init("/WEB-INF/");
    }

    public void init(String path) {
        try {
            URL rootPath = servletContext.getResource(path);
            File root = ResourceUtil.getFile(rootPath);
            init0(root);
        } catch (MalformedURLException e) {
            throw new IORuntimeException(e);
        }
    }

    private void init0(File root) {
        FileSystemTraversal.Handler handler = new FileSystemTraversal.Handler() {
            public void acceptFile(File file) {
                final String fileName = file.getName();
                if (fileName.endsWith(".tld")) {
                    FileInputStream is = null;
                    try {
                        is = FileInputStreamUtil.create(file);
                        scanTld(is, fileName);
                    } finally {
                        if (is != null) {
                            InputStreamUtil.close(is);
                        }
                    }
                }
            }

            public void acceptDirectory(File file) {
            }
        };
        FileSystemTraversal.traverse(root, handler);
    }

}
