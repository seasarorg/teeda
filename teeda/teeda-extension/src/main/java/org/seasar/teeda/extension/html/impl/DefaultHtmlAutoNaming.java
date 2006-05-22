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

import org.seasar.teeda.extension.html.HtmlAutoNaming;

/**
 * @author higa
 * 
 */
public class DefaultHtmlAutoNaming implements HtmlAutoNaming {

    private String htmlRootPath = "/html";

    private String htmlExtension = ".html";

    private String pageSuffix = "Page";

    private String actionSuffix = "Action";

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

    public String getActionSuffix() {
        return actionSuffix;
    }

    public void setActionSuffix(String actionSuffix) {
        this.actionSuffix = actionSuffix;
    }

    public String convertToPageName(String htmlPath) {
        return convertToComponentName(htmlPath, pageSuffix);
    }

    public String convertToActionName(String htmlPath) {
        return convertToComponentName(htmlPath, actionSuffix);
    }

    protected String convertToComponentName(String htmlPath, String nameSuffix) {
        if (!htmlPath.startsWith(htmlRootPath)
                || !htmlPath.endsWith(htmlExtension)) {
            throw new IllegalArgumentException(htmlPath);
        }
        String componentName = htmlPath.substring(htmlRootPath.length() + 1,
                htmlPath.length() - htmlExtension.length())
                + nameSuffix;
        return componentName.replace('/', '_');
    }
}