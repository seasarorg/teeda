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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.html.ResourceBundleDesc;
import org.seasar.teeda.extension.html.impl.ResourceBundleDescImpl;

/**
 * @author shot
 */
public class HotdeployResourceBundle {

    //TODO testing
    private static Map cache = new HashMap();

    public static MessageResourceBundle getBundle(String baseName,
            Locale locale, ClassLoader classLoader) {
        return getBundleInternal(baseName, locale, classLoader);
    }

    private static MessageResourceBundle getBundleInternal(String baseName,
            Locale locale, ClassLoader classLoader) {
        AssertionUtil.assertNotNull("baseName", baseName);
        AssertionUtil.assertNotNull("locale", locale);
        AssertionUtil.assertNotNull("classLoader", classLoader);
        final ResourceBundleDesc resourceBundleDesc = (ResourceBundleDesc) cache
                .get(baseName);
        Properties prop = null;
        if (resourceBundleDesc == null || resourceBundleDesc.isModified(locale)) {
            ResourceBundleDesc rbd = new ResourceBundleDescImpl(baseName,
                    locale);
            prop = rbd.getProperties(classLoader);
            cache.put(baseName, rbd);
        } else {
            prop = resourceBundleDesc.getProperties(classLoader);
        }
        return new MessageResourceBundle(prop);
    }

    public static void clear() {
        cache.clear();
    }

}
