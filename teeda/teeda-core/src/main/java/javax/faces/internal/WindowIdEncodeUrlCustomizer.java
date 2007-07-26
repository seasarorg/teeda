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

import javax.faces.context.ExternalContext;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class WindowIdEncodeUrlCustomizer implements EncodeUrlCustomizer {

    private static final long serialVersionUID = 1L;

    public String encodeActionUrl(ExternalContext externalContext, String url) {
        return encodeResourceUrl(externalContext, url);
    }

    public String encodeResourceUrl(final ExternalContext externalContext,
            final String url) {
        AssertionUtil.assertNotNull("url is null.", url);
        AssertionUtil
                .assertNotNull("externalContext is null.", externalContext);
        final String wid = WindowIdUtil.getWindowId(externalContext);
        final StringBuffer buf = new StringBuffer(url.length()
                + WindowIdUtil.WID.length() + 2);
        buf.append(url);
        if (!StringUtil.isEmpty(wid)) {
            if (url.lastIndexOf("?") >= 0) {
                buf.append("&");
            } else {
                buf.append("?");
            }
            buf.append(WindowIdUtil.WID);
            buf.append("=");
            buf.append(wid);
        }
        return buf.toString();
    }

    //do nothing
    public String encodeNamespace(String name) {
        return name;
    }

}
