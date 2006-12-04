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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.xerces.parsers.SAXParser;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.SAXRuntimeException;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author higa
 * @author manhole
 * @author shot
 */
public class HtmlParserImpl implements HtmlParser {

    private Map dtdPaths = new HashMap();

    private String encoding = JsfConstants.DEFAULT_ENCODING;

    public HtmlNode parse(InputStream is, String viewId) {
        try {
            SAXParser parser = new TeedaSAXParser();
            HtmlNodeHandler handler = createHtmlNodeHandler();
            InputStreamReader reader = InputStreamReaderUtil.create(is,
                    getEncoding());
            parser.setContentHandler(handler);
            parser.setEntityResolver(handler);
            InputSource inputSource = new InputSource(reader);
            inputSource.setPublicId(viewId);
            parser.parse(inputSource);
            return handler.getRoot();
        } catch (SAXException e) {
            throw new SAXRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    protected HtmlNodeHandler createHtmlNodeHandler() {
        HtmlNodeHandler htmlNodeHandler = (HtmlNodeHandler) DIContainerUtil
                .getComponent(HtmlNodeHandler.class);
        for (Iterator itr = dtdPaths.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String publicId = (String) entry.getKey();
            String dtdPath = (String) entry.getValue();
            htmlNodeHandler.registerDtdPath(publicId, dtdPath);
        }
        return htmlNodeHandler;
    }

    public void registerDtdPath(String publicId, String dtdPath) {
        dtdPaths.put(publicId, dtdPath);
    }

    public void clearDtdPath() {
        dtdPaths.clear();
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
