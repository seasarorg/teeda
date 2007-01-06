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

import java.util.Map;
import java.util.Random;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author higa
 *
 */
public class WindowIdUtil {

    public static final String NEWWINDOW = "newwindow";

    public static final String TEEDA_WID = "TEEDA_WID";

    private static final String BLANK = "_blank";

    private static final String base = String.valueOf(System
            .currentTimeMillis());

    private static Random random = new Random();

    private static Long sequence = new Long(0);

    private static String currentWindowId;

    protected WindowIdUtil() {
    }

    public static String setupWindowId(final ExternalContext externalContext)
            throws FacesException {
        String wid = null;
        if (needNewWindow(externalContext.getRequestParameterMap())) {
            wid = createWindowId();
            Object response = externalContext.getResponse();
            //TODO PortletSupport
            if (response instanceof HttpServletResponse) {
                HttpServletResponse res = (HttpServletResponse) response;
                Cookie cookie = new Cookie(WindowIdUtil.TEEDA_WID, wid);
                res.addCookie(cookie);
            }
        } else {
            Map cookieMap = externalContext.getRequestCookieMap();
            if (cookieMap != null) {
                Cookie cookie = (Cookie) cookieMap.get(WindowIdUtil.TEEDA_WID);
                if (cookie != null) {
                    wid = cookie.getValue();
                }
            }
        }
        setWindowId(externalContext, wid);
        return wid;
    }

    public static String createWindowId() {
        synchronized (random) {
            sequence = new Long(sequence.longValue() + 1);
            currentWindowId = base + sequence + random.nextLong();
            return currentWindowId;
        }

    }

    public static void setWindowId(final ExternalContext externalContext,
            String wid) throws FacesException {
        Map requestMap = externalContext.getRequestMap();
        requestMap.put(WindowIdUtil.TEEDA_WID, wid);
    }

    public static String getWindowId(final ExternalContext externalContext)
            throws FacesException {
        Map requestMap = externalContext.getRequestMap();
        return (String) requestMap.get(WindowIdUtil.TEEDA_WID);
    }

    public static String getCurrentWindowId() {
        synchronized (random) {
            return currentWindowId;
        }
    }

    public static boolean isNewWindowTarget(String target) {
        if (StringUtil.isEmpty(target)) {
            return false;
        }
        return BLANK.equals(target) || target.charAt(0) != '_';
    }

    public static boolean needNewWindow(final Map parameterMap) {
        final String newwindow = (String) parameterMap.get(NEWWINDOW);
        return JsfConstants.TRUE.equals(newwindow);
    }
}