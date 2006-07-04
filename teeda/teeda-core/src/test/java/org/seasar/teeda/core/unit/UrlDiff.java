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
package org.seasar.teeda.core.unit;

import org.seasar.teeda.core.render.html.support.UrlString;

/**
 * @author manhole
 */
public class UrlDiff {

    private boolean isIdentical_;

    private String url1_;

    private String url2_;

    public UrlDiff(String url1, String url2) {
        if (url1 == null) {
            throw new NullPointerException("url1");
        }
        if (url2 == null) {
            throw new NullPointerException("url2");
        }
        url1_ = url1;
        url2_ = url2;
    }

    public boolean isIdentical() {
        compare();
        return isIdentical_;
    }

    protected void compare() {
        if (url1_.equals(url2_)) {
            isIdentical_ = true;
            return;
        }
        UrlString urlString1 = new UrlString();
        urlString1.parse(url1_);
        UrlString urlString2 = new UrlString();
        urlString2.parse(url2_);
        isIdentical_ = urlString1.isIdentical(urlString2);
    }

}
