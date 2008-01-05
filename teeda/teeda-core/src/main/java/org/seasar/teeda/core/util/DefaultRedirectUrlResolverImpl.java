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
package org.seasar.teeda.core.util;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesConfigOptions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.resolver.RedirectUrlResolver;

/**
 * @author shot
 */
public class DefaultRedirectUrlResolverImpl implements RedirectUrlResolver {

    public String resolveUrl(final String contextPath,
            final FacesContext context, final HttpServletRequest request,
            final HttpServletResponse response) throws FacesException {
        AssertionUtil.assertNotNull("contextPath", contextPath);
        final String redirectUrl = FacesConfigOptions.getRedirectUrl();
        if (StringUtil.isEmpty(redirectUrl)) {
            return contextPath;
        }
        if (redirectUrl.endsWith("/") && contextPath.startsWith("/")) {
            return redirectUrl + contextPath.substring(1);
        } else {
            return redirectUrl + contextPath;
        }
    }

}
