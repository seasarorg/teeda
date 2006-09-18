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
package org.seasar.teeda.unit.web;

import java.io.IOException;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebWindow;

/**
 * @author manhole
 */
public class MyWebClient extends WebClient {

    private WebRequestSettings webRequestSettings_;

    public Page getPage(final WebWindow webWindow,
        final WebRequestSettings parameters) throws IOException,
        FailingHttpStatusCodeException {
        webRequestSettings_ = parameters;
        return super.getPage(webWindow, parameters);
    }

    public WebRequestSettings getWebRequestSettings() {
        return webRequestSettings_;
    }

    public KeyValuePair getRequestParameter(final String key) {
        for (final Iterator it = webRequestSettings_.getRequestParameters()
            .iterator(); it.hasNext();) {
            final KeyValuePair pair = (KeyValuePair) it.next();
            if (key.equals(pair.getKey())) {
                return pair;
            }
        }
        return null;
    }
}