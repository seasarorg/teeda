/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;

/**
 * @author shot
 */
public class KumuDisabledScriptLoader implements DoubleSubmitProtectionLoader {

    protected static final Pattern pattern = Pattern.compile("Items:");

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

        final String key = getClass().getName();
        final Map resources = VirtualResource.getInlineJsResources(context);
        final JavaScriptProviderImpl provider;
        if (!resources.containsKey(key)) {
            final long time = button.getTime();
            final String submitFunction = FacesMessageUtil.getSummary(context,
                    SUBMIT_MESSAGE_KEY, null);
            provider = new JavaScriptProviderImpl(time, submitFunction);
        } else {
            provider = (JavaScriptProviderImpl) resources.get(key);
        }
        provider.addButton(button.getId());

        VirtualResource.addInlineJsResource(context, key, provider);
    }

    public static class JavaScriptProviderImpl implements JavaScriptProvider {

        protected long time;

        protected String submitFunction;

        protected List buttons = new ArrayList();

        public JavaScriptProviderImpl(long time, String submitFunction) {
            this.time = time;
            this.submitFunction = submitFunction;
        }

        public void addButton(final String id) {
            buttons.add(id);
        }

        public String getScript() {
            final FacesContext context = FacesContext.getCurrentInstance();
            final StringBuffer buf = new StringBuffer(200);
            buf.append("DisabledConf = {\n");
            buf.append(" includeButton : [");
            for (int i = 0; i < buttons.size(); ++i) {
                buf.append("'").append((String) buttons.get(i)).append("', ");
            }
            buf.setLength(buf.length() - 2);
            buf.append("],\n");
            buf.append(" time : ").append(time).append(",\n");
            if (submitFunction != null) {
                buf.append(" submitMessage : ");
                buf.append(submitFunction).append("\n");
            }
            buf.append("}\n");
            return new String(buf);
        }

    }

}
