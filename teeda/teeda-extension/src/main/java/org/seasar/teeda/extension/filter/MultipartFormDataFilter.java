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
package org.seasar.teeda.extension.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.framework.util.StringUtil;

/**
 * @author koichik
 */
public class MultipartFormDataFilter implements Filter {

    public static final String DOFILTER_CALLED = "org.seasar.teeda.extension.filter.MultipartFormDataFilter.doFilterCalled";

    public static final int DEFAULT_MAX_SIZE = 100 * 1024 * 1024;

    public static final int DEFAULT_THREASHOLD_SIZe = 1 * 1024 * 1024;

    protected int maxSize;

    protected int thresholdSize;

    protected String repositoryPath = null;

    protected ServletContext servletContext;

    public void init(final FilterConfig filterConfig) throws ServletException {
        maxSize = getSizeParameter(filterConfig, "uploadMaxFileSize",
                DEFAULT_MAX_SIZE);
        thresholdSize = getSizeParameter(filterConfig, "uploadThresholdSize",
                DEFAULT_THREASHOLD_SIZe);
        repositoryPath = filterConfig.getInitParameter("uploadRepositoryPath");
        servletContext = filterConfig.getServletContext();
    }

    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (request.getAttribute(DOFILTER_CALLED) != null) {
            chain.doFilter(request, response);
            return;
        }
        request.setAttribute(DOFILTER_CALLED, "true");

        if (!(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!ServletFileUpload.isMultipartContent(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        final HttpServletRequest multipartRequest = new MultpartFormDataRequestWrapper(
                httpRequest, maxSize, thresholdSize, repositoryPath);
        chain.doFilter(multipartRequest, response);
    }

    public void destroy() {
    }

    protected int getSizeParameter(final FilterConfig filterConfig,
            final String parameterName, final int defaultValue) {
        String param = filterConfig.getInitParameter(parameterName);
        if (StringUtil.isEmpty(param)) {
            return defaultValue;
        }

        param = param.toLowerCase();
        int factor = 1;
        String number = param;
        if (param.endsWith("g")) {
            factor = 1024 * 1024 * 1024;
            number = param.substring(0, param.length() - 1);
        } else if (param.endsWith("m")) {
            factor = 1024 * 1024;
            number = param.substring(0, param.length() - 1);
        } else if (param.endsWith("k")) {
            factor = 1024;
            number = param.substring(0, param.length() - 1);
        }
        return Integer.parseInt(number) * factor;
    }

}
