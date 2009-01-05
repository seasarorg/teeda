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
package org.seasar.teeda.extension.util;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class GridUtil {

    private static final int WINDOWS_XP_DEFAULT = 16;

    private static final int WINDOWS_2000_DEFAULT = 17;

    public static int getScrollBarWidthByOS(FacesContext context) {
        final ExternalContext externalContext = context.getExternalContext();
        Map requestHeaderMap = externalContext.getRequestHeaderMap();
        String useragent = (String) requestHeaderMap.get("user-agent");
        if (useragent == null) {
            return WINDOWS_XP_DEFAULT;
        }
        if (useragent.indexOf("Windows NT 5.0") >= 0
                || useragent.indexOf("Windows 2000") >= 0) {
            return WINDOWS_2000_DEFAULT;
        } else if (useragent.indexOf("Windows NT 5.1") >= 0
                || useragent.indexOf("Windows XP") >= 0) {
            return WINDOWS_XP_DEFAULT;
        } else {
            //TODO another OS
        }
        return WINDOWS_XP_DEFAULT;
    }
}
