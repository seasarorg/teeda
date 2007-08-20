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

import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author higa
 *
 */
public abstract class TargetCommandUtil {

    protected TargetCommandUtil() {
    }

    public static final boolean isTargetCommand(FacesContext context,
            String[] forValues) {
        if (forValues == null || forValues.length == 0) {
            return true;
        }
        String command = getCommand(context);
        if (command == null) {
            return true;
        }
        for (int i = 0; i < forValues.length; ++i) {
            String t = forValues[i].trim();
            if (StringUtil.isEmpty(t)) {
                continue;
            }
            if (command.equals(t)) {
                return true;
            }
        }
        return false;
    }

    public static final String getCommand(FacesContext context) {
        return (String) context.getExternalContext().getRequestMap().get(
                JsfConstants.SUBMITTED_COMMAND);
    }
}
