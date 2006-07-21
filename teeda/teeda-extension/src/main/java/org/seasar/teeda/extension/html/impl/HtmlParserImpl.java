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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.util.SAXParserUtil;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;
import org.xml.sax.InputSource;

/**
 * @author higa
 * @author manhole
 */
public class HtmlParserImpl implements HtmlParser {

    private Map dtdPaths = new HashMap();

    private List elementNodeTagName = new ArrayList();

    public HtmlNode parse(InputStream is) {
        SAXParser parser = SAXParserFactoryUtil.newSAXParser();
        HtmlNodeHandler handler = createHtmlNodeHandler();
        SAXParserUtil.parse(parser, new InputSource(is), handler);
        return handler.getRoot();
    }

    protected HtmlNodeHandler createHtmlNodeHandler() {
        HtmlNodeHandler htmlNodeHandler = new HtmlNodeHandler();
        for (Iterator itr = dtdPaths.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String publicId = (String) entry.getKey();
            String dtdPath = (String) entry.getValue();
            htmlNodeHandler.registerDtdPath(publicId, dtdPath);
        }
        for (final Iterator it = elementNodeTagName.iterator(); it.hasNext();) {
            final String tagName = (String) it.next();
            htmlNodeHandler.addElementNodeTagName(tagName);
        }
        return htmlNodeHandler;
    }

    public void registerDtdPath(String publicId, String dtdPath) {
        dtdPaths.put(publicId, dtdPath);
    }

    public void clearDtdPath() {
        dtdPaths.clear();
    }

    public void addElementNodeTagName(String tagName) {
        elementNodeTagName.add(tagName);
    }

}
