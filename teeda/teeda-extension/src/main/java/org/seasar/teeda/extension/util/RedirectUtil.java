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
package org.seasar.teeda.extension.util;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.html.RedirectDesc;
import org.seasar.teeda.extension.html.impl.RedirectDescImpl;

/**
 * @author koichik
 */
public class RedirectUtil {

    protected static final String REDIRECT_DESC_KEY = RedirectUtil.class
            .getName() +
            ".DESC";

    public static void setRedirectDesc(final String protocol) {
        setRedirectDesc(new RedirectDescImpl(protocol));
    }

    public static void setRedirectDesc(final String protocol, final int port) {
        setRedirectDesc(new RedirectDescImpl(protocol, port));
    }

    public static void setRedirectDesc(final RedirectDesc redirectDesc) {
        final Map map = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap();
        map.put(REDIRECT_DESC_KEY, redirectDesc);
    }

    public static RedirectDesc getRedirectDesc() {
        final Map map = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap();
        return (RedirectDesc) map.get(REDIRECT_DESC_KEY);
    }

}
