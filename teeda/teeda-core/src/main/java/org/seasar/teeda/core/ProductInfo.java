/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author shot
 * @author manhole
 */
public class ProductInfo {

    private static final String POM_PROPERTIES_NAME = "META-INF/maven/org.seasar.teeda/teeda-core/pom.properties";

    private static final String UNKNOWN_VERSION = "unknown";

    public static String getProductName() {
        return "Teeda";
    }

    public static String getVersion() {
        try {
            final URL url = Thread.currentThread().getContextClassLoader()
                    .getResource(POM_PROPERTIES_NAME);
            if (url == null) {
                return UNKNOWN_VERSION;
            }
            final InputStream is = url.openStream();
            try {
                Properties props = new Properties();
                props.load(is);
                return props.getProperty("version");
            } finally {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UNKNOWN_VERSION;
        }
    }

    /*
     * MANIFEST.MFのmain-classに指定しておき、jarを実行するとバージョン番号が
     * コンソールへ出力されるようにする。
     */
    public static void main(final String[] args) {
        System.out.println("teeda.version: " + getVersion());
    }

}
