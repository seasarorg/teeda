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

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.html.PageAutoNaming;

/**
 * @author higa
 * 
 */
public class DefaultPageAutoNaming implements PageAutoNaming {

    private String htmlRootPath = "/html";

    private String htmlExtension = ".html";

    private String pageSuffix = "Page";

    public String getHtmlRootPath() {
        return htmlRootPath;
    }

    public void setHtmlRootPath(String htmlRootPath) {
        this.htmlRootPath = htmlRootPath;
    }

    public String getHtmlExtension() {
        return htmlExtension;
    }

    public void setHtmlExtension(String htmlExtension) {
        this.htmlExtension = htmlExtension;
    }

    public String getPageSuffix() {
        return pageSuffix;
    }

    public void setPageSuffix(String pageSuffix) {
        this.pageSuffix = pageSuffix;
    }

    public String convertToPageName(String htmlPath) {
        if (!htmlPath.startsWith(htmlRootPath)
                || !htmlPath.endsWith(htmlExtension)) {
            throw new IllegalArgumentException(htmlPath);
        }
        String pageName = htmlPath.substring(htmlRootPath.length() + 1,
                htmlPath.length() - htmlExtension.length())
                + pageSuffix;
        return pageName.replace('/', '_');
    }

    public String convertToHtmlPath(String pageName) {
        String[] names = StringUtil.split(pageName, "_");
        String fileName = names[names.length - 1];
        if (!fileName.endsWith(pageSuffix)) {
            throw new IllegalArgumentException(pageName);
        }
        fileName = fileName.substring(0, fileName.length()
                - pageSuffix.length());
        String prefix = "/";
        for (int i = 0; i < names.length - 1; ++i) {
            prefix = prefix + names[i] + "/";
        }
        return htmlRootPath + prefix + fileName + htmlExtension;
    }
}