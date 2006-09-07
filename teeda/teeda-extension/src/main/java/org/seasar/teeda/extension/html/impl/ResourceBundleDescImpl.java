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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.PropertiesUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.html.ResourceBundleDesc;
import org.seasar.teeda.extension.util.MessageResourceBundle;

/**
 * @author shot
 */
public class ResourceBundleDescImpl implements ResourceBundleDesc {

    private String baseName;

    private Locale locale;

    private File file;

    private long lastModified;

    private String propertiesName;

    private String defaultPropertiesName;
    
    private MessageResourceBundle bundle;
    
    private static final String PROPERTIES_SUFFIX = ".properties";

    public ResourceBundleDescImpl(String baseName, Locale locale) {
        AssertionUtil.assertNotNull("baseName", baseName);
        AssertionUtil.assertNotNull("locale", locale);
        this.baseName = baseName;
        this.locale = locale;
        this.file = createFile();
        this.lastModified = file.lastModified();
    }

    public boolean isModified(Locale locale) {
        if (file == null || locale == null) {
            return false;
        }
        return (file.lastModified() > lastModified)
                || !(this.locale.equals(locale));
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    protected File createFile() {
        final String basePropName = StringUtil.replace(baseName, ".", "/");
        this.defaultPropertiesName = basePropName + PROPERTIES_SUFFIX;
        String propertiesName = basePropName + "_" + locale.toString()
                + PROPERTIES_SUFFIX;
        File file = ResourceUtil.getResourceAsFile(propertiesName);
        if (!file.exists()) {
            propertiesName = this.defaultPropertiesName;
            file = ResourceUtil.getResourceAsFile(propertiesName);
        }
        this.propertiesName = propertiesName;
        return file;
    }

    public MessageResourceBundle getBundle() {
        if (bundle != null) {
            return bundle;
        }
        Properties prop = getProperties(propertiesName);
        Properties parentProp = getProperties(defaultPropertiesName);
        this.bundle = new MessageResourceBundle(prop, parentProp);
        return this.bundle;
    }

    protected Properties getProperties(String propName) {
        if (StringUtil.isEmpty(propName)) {
            return null;
        }
        InputStream is = ResourceUtil.getResourceAsStreamNoException(propName);
        if(is == null) {
            return null;
        }
        BufferedInputStream bs = new BufferedInputStream(is);
        try {
            Properties properties = new Properties();
            PropertiesUtil.load(properties, bs);
            return properties;
        } finally {
            InputStreamUtil.close(bs);
        }
    }
}
