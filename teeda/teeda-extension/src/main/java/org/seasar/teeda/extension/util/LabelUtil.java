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

import javax.faces.internal.FacesConfigOptions;

import org.seasar.framework.convention.NamingConvention;

/**
 * @author shot
 */
public class LabelUtil {

    public static final String LABEL = "label";

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
        if (packageName.lastIndexOf(subAppRoot) > 0) {
            defaultPropertiesName = packageName.substring(0, packageName
                    .lastIndexOf(subAppRoot)
                    + subAppRoot.length())
                    + "." + LABEL;
        }
        return defaultPropertiesName;
    }

}
