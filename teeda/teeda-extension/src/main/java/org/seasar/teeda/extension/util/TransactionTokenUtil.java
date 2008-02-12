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

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.MessageDigestUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author higa
 * @author shot
 */
public class TransactionTokenUtil {

    public static String TOKEN = null;

    public static String SAVED_TOKEN_SUFFIX = ".SAVED";

    public static final String DEFAULT_TOKEN = TransactionTokenUtil.class
            .getName() +
            ".TOKEN";

    private static final String CURRENT_TOKEN = TransactionTokenUtil.class
            .getName() +
            ".CURRENT_TOKEN";

    public static final String PREVIOUS_TOKEN = TransactionTokenUtil.class
            .getName() +
            ".PREVIOUS_TOKEN";

    private static final String DO_ONCE = "doOnce";

    private static long previous;

    protected TransactionTokenUtil() {
    }

    public static final boolean isDoOnce(String id) {
        if (id == null) {
            return false;
        }
        return id.startsWith(DO_ONCE);
    }

    public static synchronized String getToken(Map requestMap) {
        initTokenIfNeed();
        return (String) requestMap.get(TOKEN);
    }

    public static synchronized void renderTokenIfNeed(FacesContext context,
            UIComponent component) throws IOException {
        initTokenIfNeed();
        ExternalContext extCtx = context.getExternalContext();
        Map requestMap = extCtx.getRequestMap();
        String token = (String) requestMap.get(TOKEN);
        if (!StringUtil.isEmpty(token)) {
            return;
        }
        token = (String) requestMap.remove(TOKEN + SAVED_TOKEN_SUFFIX);
        if (StringUtil.isEmpty(token)) {
            token = generate(context);
        }
        requestMap.put(TOKEN, token);
        RendererUtil.renderHidden(component, context.getResponseWriter(),
                TOKEN, token);
    }

    public static synchronized void resetToken(final FacesContext context) {
        if (TOKEN == null) {
            return;
        }
        final ExternalContext extCtx = context.getExternalContext();
        final Map requestMap = extCtx.getRequestMap();
        String token = (String) requestMap.remove(TOKEN);
        if (!StringUtil.isEmpty(token)) {
            requestMap.put(TOKEN + SAVED_TOKEN_SUFFIX, token);
        }
    }

    protected static String generate(FacesContext context) {
        ExternalContext extCtx = context.getExternalContext();
        Object session = extCtx.getSession(true);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(session.getClass());
        PropertyDesc pd = beanDesc.getPropertyDesc("id");
        String id = null;
        if (pd.isReadable()) {
            id = (String) pd.getValue(session);
        }
        String token = generate(id);
        setCurrentToken(context, token);
        return token;

    }

    protected static String generate(String sessionId) {
        byte[] idBytes = sessionId.getBytes();
        long current = System.currentTimeMillis();
        if (current <= previous) {
            current = previous + 1;
        }
        previous = current;
        byte[] now = Long.toString(current).getBytes();
        MessageDigest md = MessageDigestUtil.getInstance("MD5");
        md.update(idBytes);
        md.update(now);
        return StringUtil.toHex(md.digest());
    }

    protected static String getCurrentToken(final FacesContext context) {
        final Map subAppScope = SubApplicationScope.getContext(context);
        if (subAppScope == null) {
            return null;
        }
        return (String) subAppScope.get(CURRENT_TOKEN);
    }

    protected static void setCurrentToken(final FacesContext context,
            String currentToken) {
        final Map subAppScope = SubApplicationScope.getOrCreateContext(context);
        subAppScope.put(CURRENT_TOKEN, currentToken);
    }

    protected static void removeCurrentTokens(final FacesContext context) {
        final Map subAppScope = SubApplicationScope.getContext(context);
        if (subAppScope == null) {
            return;
        }
        subAppScope.remove(CURRENT_TOKEN);
    }

    protected static String getPreviousToken(final FacesContext context) {
        final Map subAppScope = SubApplicationScope.getContext(context);
        if (subAppScope == null) {
            return null;
        }
        return (String) subAppScope.get(PREVIOUS_TOKEN);
    }

    protected static void setPreviousToken(final FacesContext context,
            String previousToken) {
        final Map subAppScope = SubApplicationScope.getOrCreateContext(context);
        subAppScope.put(PREVIOUS_TOKEN, previousToken);
    }

    protected static void removePreviousToken(final FacesContext context) {
        final Map subAppScope = SubApplicationScope.getContext(context);
        if (subAppScope == null) {
            return;
        }
        subAppScope.remove(PREVIOUS_TOKEN);
    }

    public static synchronized boolean verify(FacesContext context) {
        initTokenIfNeed();
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        String token = getToken(requestMap);
        if (StringUtil.isEmpty(token)) {
            return false;
        }
        return verify(context, token);
    }

    public static synchronized boolean verify(FacesContext context, String token) {
        final String currentToken = getCurrentToken(context);
        if (StringUtil.isEmpty(currentToken)) {
            return false;
        }
        if (currentToken.equals(token)) {
            removeCurrentTokens(context);
            setPreviousToken(context, token);
            return true;
        }
        return false;
    }

    public static boolean isPrevious(final FacesContext context) {
        final Map paramMap = context.getExternalContext()
                .getRequestParameterMap();
        final String token = (String) paramMap.get(TOKEN);
        final String previous = getPreviousToken(context);
        if (StringUtil.isEmpty(previous)) {
            return false;
        }
        return previous.equals(token);
    }

    private static void initTokenIfNeed() {
        if (TOKEN == null) {
            final String customizedTokenKey = FacesMessageUtil.getSummary(
                    FacesContext.getCurrentInstance(), DEFAULT_TOKEN, null);
            TOKEN = (customizedTokenKey != null) ? customizedTokenKey
                    : DEFAULT_TOKEN;
        }
    }

    public static void resetContext(final FacesContext context) {
        removeCurrentTokens(context);
        removePreviousToken(context);
    }

}
