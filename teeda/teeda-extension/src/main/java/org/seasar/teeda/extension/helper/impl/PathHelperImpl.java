/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.helper.impl;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.exception.IllegalLayoutPathRuntimeException;
import org.seasar.teeda.extension.helper.PathHelper;

/**
 * @author higa
 * 
 */
public class PathHelperImpl implements PathHelper {

    private NamingConvention namingConvention;

    /**
     * @return Returns the namingConvention.
     */
    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    /**
     * @param namingConvention The namingConvention to set.
     */
    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

    public String fromPageClassToViewRootRelativePath(Class pageClass) {
        AssertionUtil.assertNotNull("pageClass", pageClass);
        String viewId = namingConvention.fromPageClassToPath(pageClass);
        String viewRootPath = namingConvention.adjustViewRootPath();
        return StringUtil.trimPrefix(viewId, viewRootPath);
    }

    public String fromViewRootRelativePathToViewId(String viewRootRelativePath) {
        AssertionUtil.assertNotNull("viewRootRelativePath",
                viewRootRelativePath);
        String viewRootPath = namingConvention.getViewRootPath();
        if (!viewRootRelativePath.startsWith("/")) {
            throw new IllegalLayoutPathRuntimeException(viewRootRelativePath,
                    viewRootPath);
        }
        if ("/".equals(viewRootPath)) {
            viewRootPath = "";
        }
        return viewRootPath + viewRootRelativePath;
    }
}
