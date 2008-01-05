/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class UICommandUtil {

    private UICommandUtil() {
    }

    public static void setSubmittedCommand(FacesContext context,
            UICommand command) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("command", command);
        String id = command.getId();
        if (id != null) {
            Map requestMap = context.getExternalContext().getRequestMap();
            requestMap.put(JsfConstants.SUBMITTED_COMMAND, id);
        }
    }

    public static String getSubmittedCommand(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        return (String) context.getExternalContext().getRequestMap().get(
                JsfConstants.SUBMITTED_COMMAND);
    }
}
