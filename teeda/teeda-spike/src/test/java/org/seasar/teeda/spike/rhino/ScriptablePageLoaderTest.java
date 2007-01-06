/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.spike.rhino;

import java.net.URL;

import org.seasar.teeda.spike.rhino.ScriptablePageLoader;

import junit.framework.TestCase;

public class ScriptablePageLoaderTest extends TestCase {

    public void testLoad() {
        ScriptablePageLoader loader = new ScriptablePageLoader();
        String htmlPath = "org/seasar/teeda/spike/rhino/ScriptPageTest.html";
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        URL url = classLoader.getResource(htmlPath);
        htmlPath = url.getFile();
        try {
            loader.load(htmlPath);
        } catch (Throwable t) {
            fail();
        }
    }

}
