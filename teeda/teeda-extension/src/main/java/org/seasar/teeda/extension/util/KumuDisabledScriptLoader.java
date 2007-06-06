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
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;

/**
 * @author shot
 */
public class KumuDisabledScriptLoader implements DoubleSubmitProtectionLoader {

    public void loadScript(FacesContext context, THtmlCommandButton button) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("button", button);
        final String path = ResourceUtil.getResourcePath(
                "org.seasar.teeda.ajax.js.kumu", "js");
        VirtualResource.addJsResource(context, path);
        VirtualResource.addJsResource(context, ResourceUtil.getResourcePath(
                "org.seasar.teeda.ajax.js.event", "js"));
        VirtualResource.addJsResource(context, ResourceUtil.getResourcePath(
                "org.seasar.teeda.ajax.js.disabled", "js"));
        final String key = button.getClass().getName() + "." + button.getId();
        final StringBuffer buf = new StringBuffer(200);
        //buf.append("Kumu.dynamicLoad('disabled');\n");
        buf.append("DisabledConf = {\n");
        buf.append(" time : ").append(button.getTime()).append(",\n");
        final String submitFunction = FacesMessageUtil.getSummary(context,
                SUBMIT_MESSAGE_KEY, null);
        if (submitFunction != null) {
            buf.append(" submitMessage : ");
            buf.append(submitFunction).append("\n");
        }
        buf.append("}\n");
        VirtualResource.addInlineJsResource(context, key, buf.toString());
    }

}
