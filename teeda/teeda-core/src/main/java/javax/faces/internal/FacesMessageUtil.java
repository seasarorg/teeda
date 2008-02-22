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
package javax.faces.internal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.util.HTMLEncodeUtil;

/**
 * @author shot
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class FacesMessageUtil {

    private static final String DETAIL_SUFFIX = "_detail";

    private static final FacesMessage[] EMPTY_MESSAGES = new FacesMessage[0];

    private FacesMessageUtil() {
    }

    public static FacesMessage[] getWarnMessages() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage.Severity severity = FacesMessage.SEVERITY_WARN;
        return getTargetFacesMessages(context, severity);
    }

    public static FacesMessage[] getInfoMessages() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
        return getTargetFacesMessages(context, severity);
    }

    public static FacesMessage[] getErrorMessages() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage.Severity severity = FacesMessage.SEVERITY_ERROR;
        return getTargetFacesMessages(context, severity);
    }

    public static FacesMessage[] getFatalMessages() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage.Severity severity = FacesMessage.SEVERITY_FATAL;
        return getTargetFacesMessages(context, severity);
    }

    public static FacesMessage[] getTargetFacesMessages(Severity severity) {
        return getTargetFacesMessages(FacesContext.getCurrentInstance(),
                severity);
    }

    public static FacesMessage[] getTargetFacesMessages(FacesContext context,
            Severity severity) {
        return getTargetFacesMessages(context,
                new FacesMessage.Severity[] { severity });
    }

    public static FacesMessage[] getAllMessages() {
        final FacesContext context = FacesContext.getCurrentInstance();
        return getAllMessages(context);
    }

    public static FacesMessage[] getAllMessages(FacesContext context) {
        return getTargetFacesMessages(context, new FacesMessage.Severity[] {
                FacesMessage.SEVERITY_FATAL, FacesMessage.SEVERITY_ERROR,
                FacesMessage.SEVERITY_INFO, FacesMessage.SEVERITY_WARN });
    }

    public static FacesMessage[] getTargetFacesMessages(Severity[] severities) {
        return getTargetFacesMessages(FacesContext.getCurrentInstance(),
                severities);
    }

    public static FacesMessage[] getTargetFacesMessages(FacesContext context,
            Severity[] severities) {
        AssertionUtil.assertNotNull("context", context);
        if (severities == null || severities.length == 0) {
            return EMPTY_MESSAGES;
        }
        Iterator it = context.getMessages();
        if (!it.hasNext()) {
            return EMPTY_MESSAGES;
        }
        List list = new ArrayList();
        for (; it.hasNext();) {
            FacesMessage facesMessage = (FacesMessage) it.next();
            Severity severity = facesMessage.getSeverity();
            for (int i = 0; i < severities.length; i++) {
                if (!severity.equals(severities[i])) {
                    continue;
                }
                list.add(facesMessage);
            }
        }
        if (list.size() == 0) {
            return EMPTY_MESSAGES;
        }
        return (FacesMessage[]) list.toArray(new FacesMessage[list.size()]);
    }

    public static void addWarnMessage(String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addWarnComponentMessage(context, (UIComponent) null, messageId);
    }

    public static void addWarnMessage(String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addWarnComponentMessage(context, (UIComponent) null, messageId, args);
    }

    public static void addWarnComponentMessage(FacesContext context,
            UIComponent component, String messageId) {
        addWarnComponentMessage(context, component, messageId, null);
    }

    public static void addWarnComponentMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_WARN);
    }

    public static void addWarnComponentMessage(FacesContext context,
            String clientId, String messageId) {
        addWarnComponentMessage(context, clientId, messageId, null);
    }

    public static void addWarnComponentMessage(FacesContext context,
            String clientId, String messageId, Object[] args) {
        addTargetSeverityMessage(context, clientId, messageId, args,
                FacesMessage.SEVERITY_WARN);
    }

    public static void addInfoMessage(String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addInfoComponentMessage(context, (UIComponent) null, messageId);
    }

    public static void addInfoMessage(String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addInfoComponentMessage(context, (UIComponent) null, messageId, args);
    }

    public static void addInfoComponentMessage(FacesContext context,
            UIComponent component, String messageId) {
        addInfoComponentMessage(context, component, messageId, null);
    }

    public static void addInfoComponentMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_INFO);
    }

    public static void addInfoComponentMessage(FacesContext context,
            String clientId, String messageId) {
        addInfoComponentMessage(context, clientId, messageId, null);
    }

    public static void addInfoComponentMessage(FacesContext context,
            String clientId, String messageId, Object[] args) {
        addTargetSeverityMessage(context, clientId, messageId, args,
                FacesMessage.SEVERITY_INFO);
    }

    public static void addErrorMessage(String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addErrorComponentMessage(context, (UIComponent) null, messageId);
    }

    public static void addErrorMessage(String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addErrorComponentMessage(context, (UIComponent) null, messageId, args);
    }

    public static void addErrorComponentMessage(FacesContext context,
            UIComponent component, String messageId) {
        addErrorComponentMessage(context, component, messageId, null);
    }

    public static void addErrorComponentMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_ERROR);
    }

    public static void addErrorComponentMessage(FacesContext context,
            String clientId, String messageId) {
        addErrorComponentMessage(context, clientId, messageId, null);
    }

    public static void addErrorComponentMessage(FacesContext context,
            String clientId, String messageId, Object[] args) {
        addTargetSeverityMessage(context, clientId, messageId, args,
                FacesMessage.SEVERITY_ERROR);
    }

    public static void addFatalMessage(String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addFatalComponentMessage(context, (UIComponent) null, messageId);
    }

    public static void addFatalMessage(String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        addFatalComponentMessage(context, (UIComponent) null, messageId, args);
    }

    public static void addFatalComponentMessage(FacesContext context,
            UIComponent component, String messageId) {
        addFatalComponentMessage(context, component, messageId, null);
    }

    public static void addFatalComponentMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_FATAL);
    }

    public static void addFatalComponentMessage(FacesContext context,
            String clientId, String messageId) {
        addFatalComponentMessage(context, clientId, messageId, null);
    }

    public static void addFatalComponentMessage(FacesContext context,
            String clientId, String messageId, Object[] args) {
        addTargetSeverityMessage(context, clientId, messageId, args,
                FacesMessage.SEVERITY_FATAL);
    }

    private static void addTargetSeverityMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args,
            Severity severity) {
        final String clientId = (component != null) ? component
                .getClientId(context) : null;
        addTargetSeverityMessage(context, clientId, messageId, args, severity);
    }

    private static void addTargetSeverityMessage(FacesContext context,
            String clientId, String messageId, Object[] args, Severity severity) {
        final Locale locale = ComponentUtil_.getLocale(context);
        final FacesMessage message = getMessage(context, locale, severity,
                messageId, args);
        context.addMessage(clientId, message);
    }

    public static String getSummary(String messageId, Object[] args) {
        FacesMessage message = getMessage(FacesContext.getCurrentInstance(),
                messageId, args);
        return (message != null) ? message.getSummary() : null;
    }

    public static String getSummary(FacesContext context, String messageId,
            Object[] args) {
        FacesMessage message = getMessage(context, messageId, args);
        return (message != null) ? message.getSummary() : null;
    }

    public static String getDetail(String messageId, Object[] args) {
        FacesMessage message = getMessage(FacesContext.getCurrentInstance(),
                messageId, args);
        return (message != null) ? message.getDetail() : null;
    }

    public static String getDetail(FacesContext context, String messageId,
            Object[] args) {
        FacesMessage message = getMessage(context, messageId, args);
        return (message != null) ? message.getDetail() : null;
    }

    public static FacesMessage getMessage(FacesContext context,
            String messageId, Object[] args) {
        Locale locale = ComponentUtil_.getLocale(context);
        return getMessage(context, locale, FacesMessage.SEVERITY_ERROR,
                messageId, args);
    }

    public static FacesMessage getMessage(FacesContext context, Locale locale,
            Severity severity, String messageId, Object[] args) {

        MessageResourceBundle bundle = getBundle(context, locale);
        String summary = bundle.get(messageId);
        String detail = bundle.get(messageId + DETAIL_SUFFIX);
        if (summary == null && detail == null) {
            MessageResourceBundle defaultBundle = getDefaultBundle(context,
                    locale);
            summary = defaultBundle.get(messageId);
            detail = defaultBundle.get(messageId + DETAIL_SUFFIX);
        }
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                final Object o = args[i];
                if (o != null && (o instanceof String)) {
                    args[i] = HTMLEncodeUtil.encodeAll(o.toString());
                }
            }
        }
        if (summary != null) {
            summary = getFormattedMessage(summary, locale, args);
        }
        if (detail != null) {
            detail = getFormattedMessage(detail, locale, args);
        }
        return new FacesMessage(severity, summary, detail);
    }

    private static MessageResourceBundle getBundle(FacesContext context,
            Locale locale) {
        MessageResourceBundle bundle = null;
        String appBundleName = context.getApplication().getMessageBundle();
        if (appBundleName != null) {
            bundle = getBundle(context, locale, appBundleName);
        }

        if (bundle == null) {
            bundle = getDefaultBundle(context, locale);
        }
        return bundle;
    }

    private static MessageResourceBundle getDefaultBundle(FacesContext context,
            Locale locale) {
        return getBundle(context, locale, FacesMessage.FACES_MESSAGES);
    }

    private static MessageResourceBundle getBundle(FacesContext context,
            Locale locale, String bundleName) {
        return MessageResourceBundleChainFactory
                .createChain(bundleName, locale);
    }

    private static String getFormattedMessage(String message, Locale locale,
            Object[] args) {
        if (args == null || args.length == 0) {
            return message;
        }
        return new MessageFormat(message, locale).format(args);
    }

    public static boolean hasClientId(FacesContext context,
            UIComponent component) {
        AssertionUtil.assertNotNull("context", context);
        final String clientId = component.getClientId(context);
        for (Iterator itr = context.getClientIdsWithMessages(); itr.hasNext();) {
            String cId = (String) itr.next();
            if (clientId.equals(cId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasMessagesByClientId(FacesContext context,
            UIComponent component) {
        AssertionUtil.assertNotNull("context", context);
        final String clientId = component.getClientId(context);
        return hasMessagesByClientId(context, clientId);
    }

    public static boolean hasMessagesByClientId(FacesContext context,
            String clientId) {
        final Iterator itr = context.getMessages(clientId);
        return itr.hasNext();
    }

    public static boolean hasMessages() {
        return hasMessages(FacesContext.getCurrentInstance());
    }

    public static boolean hasMessages(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        final Iterator itr = context.getMessages();
        return itr.hasNext();
    }

    public static boolean hasErrorOrFatalMessage() {
        return hasErrorOrFatalMessage(FacesContext.getCurrentInstance());
    }

    public static boolean hasErrorOrFatalMessage(final FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        final FacesMessage[] allMessages = getAllMessages(context);
        for (int i = 0; i < allMessages.length; i++) {
            final FacesMessage fm = allMessages[i];
            final Severity severity = fm.getSeverity();
            if (severity.equals(FacesMessage.SEVERITY_ERROR) ||
                    severity.equals(FacesMessage.SEVERITY_FATAL)) {
                return true;
            }
        }
        return false;
    }
}