/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.EnumerationIterator;
import org.seasar.framework.util.MethodUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 * @author manhole
 */
public class ServletExternalContextUtil {

    private static Logger logger = Logger
            .getLogger(ServletExternalContextUtil.class);

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String CHARSET_HEADER = "charset=";

    private static final int CHARSET_HEADER_LENGTH = CHARSET_HEADER.length();

    private ServletExternalContextUtil() {
    }

    public static void setCharacterEncoding(ServletRequest request) {
        Method characterEncodingMethod = getCharacterEncodingMethodFromRequest();
        if (characterEncodingMethod == null) {
            return;
        }
        if (!isHttpServletRequest(request)) {
            // No getHeader(), no getSession() if it is not HttpServletRequest.
            // So, do nothing.
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String contentType = httpServletRequest.getHeader(CONTENT_TYPE);
        String encoding = getEncodingFromContentType(contentType);
        if (encoding == null) {
            encoding = getEncodingFromSession(httpServletRequest);
        }
        if (encoding == null) {
            return;
        }
        MethodUtil.invoke(characterEncodingMethod, httpServletRequest,
                new Object[] { encoding });
    }

    public static boolean setCharacterEncoding(ServletResponse response) {
        Method characterEncodingMethod = getCharacterEncodingMethodFromResponse();
        if (characterEncodingMethod == null
                && (!isHttpServletResponse(response))) {
            return false;
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String contentType = httpServletResponse.getContentType();
        String encoding = getEncodingFromContentType(contentType);
        if (encoding == null) {
            return false;
        }
        MethodUtil.invoke(characterEncodingMethod, httpServletResponse,
                new Object[] { encoding });
        return true;
    }

    public static boolean isHttpServletRequest(ServletRequest request) {
        return (request != null) && (request instanceof HttpServletRequest);
    }

    public static boolean isHttpServletResponse(ServletResponse response) {
        return (response != null) && (response instanceof HttpServletResponse);
    }

    public static String getEncodingFromContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        String encoding = null;
        int found = contentType.indexOf(CHARSET_HEADER);
        if (found == 0) {
            encoding = contentType.substring(CHARSET_HEADER_LENGTH);
        } else if (found >= 1) {
            char charBefore = contentType.charAt(found - 1);
            if (charBefore == ';' || Character.isWhitespace(charBefore)) {
                encoding = contentType.substring(found + CHARSET_HEADER_LENGTH);
            }
        }
        return encoding;
    }

    public static String getEncodingFromSession(
            HttpServletRequest servletRequest) {
        String encoding = null;
        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            encoding = (String) session
                    .getAttribute(ViewHandler.CHARACTER_ENCODING_KEY);
        }
        return encoding;
    }

    public static void dispatch(String path, ServletRequest request,
            ServletResponse response) throws IOException {
        AssertionUtil.assertNotNull("path is null.", path);
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            if (e.getMessage() != null) {
                throw new FacesException(e.getMessage(), e);
            }
            throw new FacesException(e);
        }
    }

    public static Iterator getLocales(Enumeration locales) {
        return new EnumerationIterator(locales);
    }

    public static Iterator getRequestParameterNames(Enumeration paramNames) {
        return new EnumerationIterator(paramNames);
    }

    public static void redirect(String url, ServletResponse response)
            throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(url);
        FacesContext.getCurrentInstance().responseComplete();
    }

    private static Method getCharacterEncodingMethodFromRequest() {
        try {
            Class clazz = ServletRequest.class;
            return clazz.getMethod("setCharacterEncoding",
                    new Class[] { String.class });
        } catch (Exception e) {
            logger.log(e);
            return null;
        }
    }

    private static Method getCharacterEncodingMethodFromResponse() {
        try {
            Class clazz = ServletResponse.class;
            return clazz.getMethod("setCharacterEncoding",
                    new Class[] { String.class });
        } catch (Exception e) {
            logger.log(e);
            return null;
        }
    }

    public static HttpServletRequest getRequest(ExternalContext externalContext) {
        return (HttpServletRequest) externalContext.getRequest();
    }

    public static HttpServletResponse getResponse(
            ExternalContext externalContext) {
        return (HttpServletResponse) externalContext.getResponse();
    }

    public static boolean isGetRedirect(ExternalContext externalContext) {
        return getRequest(externalContext).getMethod().equals("GET");
    }

    public static boolean isPost(ExternalContext externalContext) {
        return getRequest(externalContext).getMethod().equals("POST");
    }

    public static void storeErrorInfoToAttribute(final ServletRequest request,
            final Throwable exception) {
        AssertionUtil.assertNotNull("request", request);
        AssertionUtil.assertNotNull("exception", exception);
        request.setAttribute(JsfConstants.ERROR_EXCEPTION, exception);
        request.setAttribute(JsfConstants.ERROR_EXCEPTION_TYPE, exception
                .getClass());
        request
                .setAttribute(JsfConstants.ERROR_MESSAGE, exception
                        .getMessage());

    }
}
