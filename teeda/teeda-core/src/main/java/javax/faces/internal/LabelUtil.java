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
package javax.faces.internal;

import java.util.Locale;

import javax.faces.context.FacesContext;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.message.MessageResourceBundleFactory;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ViewHandlerUtil;

/**
 * @author shot
 */
public class LabelUtil {

    public static final String LABEL = "label";

    public static String getLabelValue(String defaultKey) {
        if (defaultKey == null) {
            return null;
        }
        int index = defaultKey.lastIndexOf('-');
        if (index > 0) {
            defaultKey = defaultKey.substring(0, index);
        }
        String viewId = FacesContext.getCurrentInstance().getViewRoot()
                .getViewId();
        if (viewId == null) {
            return null;
        }
        NamingConvention nc = (NamingConvention) DIContainerUtil
                .getComponent(NamingConvention.class);
        if (!nc.isValidViewRootPath(viewId)) {
            return null;
        }
        String pageName = nc.fromPathToPageName(viewId);
        String key = getLabelKeySuffix(nc, pageName) + "." + defaultKey;
        String propertiesName = getPropertiesName(nc, pageName);
        String defaultPropertiesName = getDefaultApplicationPropertiesName(nc,
                pageName);
        return getLabelValue(key, propertiesName, defaultKey,
                defaultPropertiesName);
    }

    public static String getLabelValue(String key, String propertiesName,
            String defaultKey, String defaultPropertiesName) {
        final Locale locale = ViewHandlerUtil.getLocale();
        String value = null;
        if (propertiesName != null) {
            MessageResourceBundle bundle = MessageResourceBundleFactory
                    .getNullableBundle(propertiesName, locale);
            value = (bundle != null) ? (String) bundle.get(key) : null;
            if (value == null && bundle != null) {
                value = (String) bundle.get(defaultKey);
            }
        }
        if (value == null) {
            if (defaultPropertiesName != null) {
                MessageResourceBundle bundle = MessageResourceBundleFactory
                        .getNullableBundle(defaultPropertiesName, locale);
                value = (bundle != null) ? (String) bundle.get(defaultKey)
                        : null;
            }
        }
        return value;
    }

    public static String getPropertiesName(NamingConvention nc, String pageName) {
        String packageName = NamingConventionUtil.getPackageName(nc, pageName);
        return (packageName != null) ? packageName + "." + LABEL : null;
    }

    public static String getLabelKeySuffix(NamingConvention nc, String pageName) {
        String path = nc.fromPageNameToPath(pageName);
        String defaultSuffix = FacesConfigOptions.getDefaultSuffix();
        if (path.endsWith(defaultSuffix)) {
            path = path.substring(0, path.lastIndexOf(defaultSuffix));
        }
        int lastIndex = path.lastIndexOf('/');
        if (lastIndex > 0) {
            path = path.substring(lastIndex + 1);
        }
        return path;
    }

    public static String getDefaultApplicationPropertiesName(
            NamingConvention nc, String pageName) {
        String subAppRoot = nc.getSubApplicationRootPackageName();
        String defaultPropertiesName = null;
        String packageName = NamingConventionUtil.getPackageName(nc, pageName);
        if(packageName == null) {
            return null;
        }
        if (packageName.lastIndexOf(subAppRoot) > 0) {
            defaultPropertiesName = packageName.substring(0, packageName
                    .lastIndexOf(subAppRoot)
                    + subAppRoot.length())
                    + "." + LABEL;
        }
        return defaultPropertiesName;
    }

}
