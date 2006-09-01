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
package org.seasar.teeda.extension.util;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class HotdeployResourceBundle {

    //TODO impl this.
    private static Map cache = new HashMap();

    public static ResourceBundle getBundle(String baseName, Locale locale,
            ClassLoader classLoader) {
        return getBundleInternal(baseName, locale, classLoader);
    }

    private static ResourceBundle getBundleInternal(String baseName,
            Locale locale, ClassLoader classLoader) {
        ResourceBundleKey key = new ResourceBundleKey(baseName, locale);
        ResourceBundleDesc resourceBundleDesc = (ResourceBundleDesc) cache
                .get(key);
        if (resourceBundleDesc != null) {
            if (resourceBundleDesc.isModified()) {

            }
        }
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale,
                classLoader);
        return bundle;
    }

    public static class ResourceBundleKey {
        private String baseName;

        private Locale locale;

        public ResourceBundleKey(String baseName, Locale locale) {
            this.baseName = baseName;
            this.locale = locale;
        }

        public String getBaseName() {
            return baseName;
        }

        public Locale getLocale() {
            return locale;
        }

    }

    public static class ResourceBundleDesc {

        private String baseName;

        private Locale locale;

        private File file;

        private long lastModified;

        private static final String PROPERTIES_SUFFIX = ".properties";

        public ResourceBundleDesc(String baseName, Locale locale) {
            this.baseName = baseName;
            this.locale = locale;
            String propertiesName = StringUtil.replace(baseName, ".", "/")
                    + "_" + locale.toString() + PROPERTIES_SUFFIX;
            this.file = new File(propertiesName);
            if (!file.exists()) {
                propertiesName = StringUtil.replace(baseName, ".", "/")
                        + PROPERTIES_SUFFIX;
                this.file = new File(propertiesName);
            }
        }

        public String getBaseName() {
            return baseName;
        }

        public File getFile() {
            return file;
        }

        public boolean isModified() {
            if (file == null) {
                return false;
            }
            return file.lastModified() > lastModified;
        }

        public Locale getLocale() {
            return locale;
        }

    }
}
