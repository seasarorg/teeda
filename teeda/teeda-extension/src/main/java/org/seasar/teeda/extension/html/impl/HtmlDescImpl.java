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

import java.io.File;

import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.HtmlNode;

public class HtmlDescImpl implements HtmlDesc {

    private HtmlNode htmlNode;

    private File file;

    private long lastModified;

    public HtmlDescImpl(HtmlNode htmlNode) {
        this(htmlNode, null);
    }

    public HtmlDescImpl(HtmlNode htmlNode, File file) {
        this.htmlNode = htmlNode;
        if (file != null) {
            this.file = file;
            lastModified = file.lastModified();
        }
    }

    public HtmlNode getHtmlNode() {
        return htmlNode;
    }

    public boolean isModified() {
        if (file == null) {
            return false;
        }
        return file.lastModified() > lastModified;
    }

}
