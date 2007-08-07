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
package javax.faces.internal;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ViewHandlerUtil;

/**
 * @author shot
 */
public class LabelUtil {

    private static Logger logger = Logger.getLogger(LabelUtil.class);

    public static String getLabelValue(String defaultKey) {
        if (defaultKey == null) {
            return null;
        }
        int index = defaultKey.lastIndexOf('-');
        if (index > 0) {
            defaultKey = defaultKey.substring(0, index);
        }
        final FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            return null;
        }
        final UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot == null) {
            logger.log("WTDA0202", new Object[0]);
            return null;
        }
        String viewId = viewRoot.getViewId();
        if (viewId == null) {
            return null;
        }
        final NamingConvention nc = (NamingConvention) DIContainerUtil
                .getComponentNoException(NamingConvention.class);
        if (nc == null || !nc.isValidViewRootPath(viewId)) {
            return null;
        }
        String pageName = nc.fromPathToPageName(viewId);
        String key = getKey(nc, pageName, defaultKey);
        String propertiesName = getPropertiesName(nc, pageName);
        String defaultPropertiesName = getDefaultApplicationPropertiesName(nc,
                pageName);
        return getLabelValue(key, propertiesName, defaultKey,
                defaultPropertiesName);
    }

    private static String getKey(final NamingConvention nc,
            final String pageName, final String defaultKey) {
        final String labelKeySuffix = getLabelKeySuffix(nc, pageName);
        return new String(new StringBuffer(labelKeySuffix.length()
                + defaultKey.length() + 1).append(labelKeySuffix).append(
                InternalConstants.DOT).append(defaultKey));
    }

    public static String getLabelValue(String key, String propertiesName,
            String defaultKey, String defaultPropertiesName) {
        final Locale locale = ViewHandlerUtil.getLocale();
        String value = null;
        if (propertiesName != null) {
            MessageResourceBundle bundle = MessageResourceBundleChainFactory
                    .createChain(propertiesName, locale);
            value = (bundle != null) ? (String) bundle.get(key) : null;
            if (value == null && bundle != null) {
                value = (String) bundle.get(defaultKey);
            }
        }
        if (value == null) {
            if (defaultPropertiesName != null) {
                MessageResourceBundle bundle = MessageResourceBundleChainFactory
                        .createChain(defaultPropertiesName, locale);
                value = (bundle != null) ? (String) bundle.get(defaultKey)
                        : null;
            }
        }
        return value;
    }

    public static String getPropertiesName(NamingConvention nc, String pageName) {
        String packageName = NamingConventionUtil.getPackageName(nc, pageName);
        return (packageName != null) ? new String(new StringBuffer(packageName
                .length() + 5).append(packageName)
                .append(InternalConstants.DOT).append(InternalConstants.LABEL))
                : null;
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
        if (packageName == null) {
            return null;
        }
        if (packageName.lastIndexOf(subAppRoot) > 0) {
            final int len = packageName.lastIndexOf(subAppRoot)
                    + subAppRoot.length();
            final String s = packageName.substring(0, len);
            defaultPropertiesName = new String(new StringBuffer(s.length() + 5)
                    .append(s).append(InternalConstants.DOT).append(
                            InternalConstants.LABEL));
        }
        return defaultPropertiesName;
    }

}
