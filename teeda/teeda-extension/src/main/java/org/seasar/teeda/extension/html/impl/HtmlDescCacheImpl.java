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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.seasar.framework.util.FileInputStreamUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.HtmlDescCache;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;

/**
 * @author higa
 * 
 */
public class HtmlDescCacheImpl implements HtmlDescCache {

    private Map htmlDescs = new HashMap();

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public synchronized HtmlDesc getHtmlDesc(String viewId) {
        return (HtmlDesc) htmlDescs.get(viewId);
    }

    public synchronized HtmlDesc createHtmlDesc(String viewId) {
        HtmlDesc htmlDesc = null;
        String realPath = servletContext.getRealPath(viewId);
        if (realPath != null) {
            File file = new File(realPath);
            if (file.exists()) {
                htmlDesc = createHtmlDescFromRealPath(file);
            }
        }
        if (htmlDesc == null) {
            htmlDesc = createHtmlDescFromResource(viewId);
        }
        htmlDescs.put(viewId, htmlDesc);
        return htmlDesc;
    }

    protected HtmlDesc createHtmlDescFromRealPath(File file) {
        HtmlNode htmlNode = null;
        HtmlParser parser = new HtmlParserImpl();
        InputStream is = new BufferedInputStream(FileInputStreamUtil
                .create(file));
        try {
            htmlNode = parser.parse(is);
        } finally {
            InputStreamUtil.close(is);
        }
        return new HtmlDescImpl(htmlNode, file);
    }

    protected HtmlDesc createHtmlDescFromResource(String viewId) {
        HtmlNode htmlNode = null;
        HtmlParser parser = new HtmlParserImpl();
        InputStream is = servletContext.getResourceAsStream(viewId);
        if (is == null) {
            throw new IllegalArgumentException(viewId);
        }
        try {
            htmlNode = parser.parse(is);
        } finally {
            InputStreamUtil.close(is);
        }
        return new HtmlDescImpl(htmlNode);
    }
}