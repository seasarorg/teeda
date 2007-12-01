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

import java.io.IOException;
import java.net.URL;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.util.DefaultRedirectUrlResolverImpl;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.RedirectDesc;

/**
 * @author shot
 */
public class ExtensionRedirectUrlResolverImpl extends
        DefaultRedirectUrlResolverImpl {

    protected boolean addUniqueKeyParameter = true;

    protected String uniqueKeyParameterName = ExtensionConstants.REDIRECT_UNIQUE_KEY_PARAMETER;

    public void setAddUniqueKeyParameter(final boolean addUniqueKeyParameter) {
        this.addUniqueKeyParameter = addUniqueKeyParameter;
    }

    public void setUniqueKeyParameterName(final String uniqueKeyParameterName) {
        this.uniqueKeyParameterName = uniqueKeyParameterName;
    }

    public String resolveUrl(final String contextPath,
            final FacesContext context, final HttpServletRequest request,
            final HttpServletResponse response) throws FacesException {
        AssertionUtil.assertNotNull("contextPath", contextPath);
        final String redirectUrl;
        final RedirectDesc redirectDesc = RedirectUtil.getRedirectDesc();
        if (redirectDesc != null) {
            redirectUrl = buildRedirectUrl(contextPath, request, redirectDesc);
        } else {
            redirectUrl = super.resolveUrl(contextPath, context, request,
                    response);
        }

        if (!addUniqueKeyParameter) {
            return redirectUrl;
        }
        return redirectUrl + "?" + uniqueKeyParameterName + "=" +
                Long.toHexString(System.currentTimeMillis());
    }

    protected String buildRedirectUrl(final String contextPath,
            final HttpServletRequest request, final RedirectDesc redirectDesc) {
        try {
            final URL currentUrl = new URL(new String(request.getRequestURL()));
            final URL redirectUrl = new URL(redirectDesc.getProtocol(),
                    currentUrl.getHost(), redirectDesc.getPort(), contextPath);
            return redirectUrl.toExternalForm();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
