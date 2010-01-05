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
package org.seasar.teeda.extension.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.portals.bridges.portletfilter.PortletFilter;
import org.apache.portals.bridges.portletfilter.PortletFilterChain;
import org.apache.portals.bridges.portletfilter.PortletFilterConfig;
import org.seasar.framework.util.StringUtil;

/**
 * @author shinsuke
 * 
 */
public class MultipartFormDataFilter implements PortletFilter {
    public static final String DOFILTER_CALLED = "org.seasar.teeda.extension.portlet.MultipartFormDataFilter.doFilterCalled";

    public static final int DEFAULT_MAX_SIZE = 100 * 1024 * 1024;

    public static final int DEFAULT_MAX_FILE_SIZE = 100 * 1024 * 1024;

    public static final int DEFAULT_THREASHOLD_SIZE = 100 * 1024;

    protected int maxSize;

    protected int maxFileSize;

    protected int thresholdSize;

    protected String repositoryPath = null;

    protected String encoding = null;

    protected PortletConfig portletConfig;

    public void init(PortletFilterConfig filterConfig) throws PortletException {
        maxSize = getSizeParameter(filterConfig, "uploadMaxSize",
                DEFAULT_MAX_SIZE);
        maxFileSize = getSizeParameter(filterConfig, "uploadMaxFileSize",
                DEFAULT_MAX_FILE_SIZE);
        thresholdSize = getSizeParameter(filterConfig, "uploadThresholdSize",
                DEFAULT_THREASHOLD_SIZE);
        repositoryPath = filterConfig.getInitParameter("uploadRepositoryPath");
        encoding = filterConfig.getInitParameter("encoding");
        portletConfig = filterConfig.getPortletConfig();
    }

    public void processActionFilter(ActionRequest request,
            ActionResponse response, PortletFilterChain chain)
            throws PortletException, IOException {
        if (request.getAttribute(DOFILTER_CALLED) != null) {
            chain.processActionFilter(request, response);
            return;
        }
        request.setAttribute(DOFILTER_CALLED, Boolean.TRUE);

        if (!PortletFileUpload.isMultipartContent(request)) {
            chain.processActionFilter(request, response);
            return;
        }

        final ActionRequest multipartRequest = new MultipartFormDataActionRequestWrapper(
                request, maxSize, maxFileSize, thresholdSize, repositoryPath,
                encoding);
        chain.processActionFilter(multipartRequest, response);
    }

    public void renderFilter(RenderRequest request, RenderResponse response,
            PortletFilterChain chain) throws PortletException, IOException {
        chain.renderFilter(request, response);
    }

    public void destroy() {
    }

    protected int getSizeParameter(final PortletFilterConfig filterConfig,
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
