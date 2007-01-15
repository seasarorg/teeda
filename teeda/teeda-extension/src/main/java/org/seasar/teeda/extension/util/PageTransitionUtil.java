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
package org.seasar.teeda.extension.util;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;

/**
 * @author shot
 */
public class PageTransitionUtil {

    private PageTransitionUtil() {
    }

    public static String getNextPageTransition(Class fromPageOrActionClass,
            Class toPageClass, NamingConvention nc) {
        if (fromPageOrActionClass == null || toPageClass == null) {
            return null;
        }
        final String to = toPageClass.getName();
        final String fromPackage = ClassUtil
                .getPackageName(fromPageOrActionClass);
        final String toPackage = ClassUtil.getPackageName(toPageClass);
        final String componentName = nc.fromClassNameToComponentName(to);
        final String path = nc.fromPageNameToPath(componentName);
        if (fromPackage.equals(toPackage)) {
            final int pos1 = path.lastIndexOf('/');
            final int pos2 = path.lastIndexOf('.');
            String s = path.substring(0, pos2);
            return s.substring(pos1 + 1);
        } else {
            String viewRootPath = nc.getViewRootPath();
            if (!viewRootPath.endsWith("/")) {
                viewRootPath = viewRootPath + "/";
            }
            final int pos1 = path.lastIndexOf(viewRootPath);
            final int pos2 = path.lastIndexOf('.');
            String s = path.substring(0, pos2);
            if (pos1 == 0) {
                s = s.substring(viewRootPath.length());
            }
            return s.replaceAll("/", "_");
        }
    }
}
