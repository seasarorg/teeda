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
package org.seasar.teeda.core.unit;

import org.seasar.teeda.core.render.html.support.UrlString;

/**
 * @author manhole
 */
public class UrlDiff {

    private Boolean identical;

    private String url1;

    private String url2;

    public UrlDiff(final String url1, final String url2) {
        if (url1 == null) {
            throw new NullPointerException("url1");
        }
        if (url2 == null) {
            throw new NullPointerException("url2");
        }
        this.url1 = url1;
        this.url2 = url2;
    }

    public boolean isIdentical() {
        if (identical == null) {
            identical = Boolean.valueOf(compare());
        }
        return identical.booleanValue();
    }

    protected boolean compare() {
        if (url1.equals(url2)) {
            return true;
        }
        final UrlString urlString1 = new UrlString();
        urlString1.parse(url1);
        final UrlString urlString2 = new UrlString();
        urlString2.parse(url2);
        return urlString1.isIdentical(urlString2);
    }

}
