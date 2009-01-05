/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render.html.support;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.IllegalRendererKeyException;

/**
 * @author shot
 *
 */
public class HtmlRenderKitKeyGenerateUtil {

    private HtmlRenderKitKeyGenerateUtil() {
    }

    public static String getGeneratedKey(String family, String renderType) {
        if (!StringUtil.isEmpty(family) && !StringUtil.isEmpty(renderType)) {
            return new String(new StringBuffer(family.length()
                    + renderType.length() + 1).append(family).append(".")
                    .append(renderType));
        } else {
            throw new IllegalRendererKeyException(family, renderType);
        }
    }

}
