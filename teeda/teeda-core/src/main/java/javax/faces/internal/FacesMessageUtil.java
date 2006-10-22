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
package javax.faces.internal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.message.MessageResourceBundleFactory;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;

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

    public static void addWarnMessage(FacesContext context,
            UIComponent component, String messageId) {
        addWarnMessage(context, component, messageId, null);
    }

    public static void addWarnMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_WARN);
    }

    public static void addInfoMessage(FacesContext context,
            UIComponent component, String messageId) {
        addInfoMessage(context, component, messageId, null);
    }

    public static void addInfoMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_INFO);
    }

    public static void addErrorMessage(FacesContext context,
            UIComponent component, String messageId) {
        addErrorMessage(context, component, messageId, null);
    }

    public static void addErrorMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_ERROR);
    }

    public static void addFatalMessage(FacesContext context,
            UIComponent component, String messageId) {
        addFatalMessage(context, component, messageId, null);
    }

    public static void addFatalMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args) {
        addTargetSeverityMessage(context, component, messageId, args,
                FacesMessage.SEVERITY_FATAL);
    }

    private static void addTargetSeverityMessage(FacesContext context,
            UIComponent component, String messageId, Object[] args,
            Severity severity) {
        String clientId = component.getClientId(context);
        Locale locale = ComponentUtil_.getLocale(context);
        FacesMessage message = getMessage(context, locale, severity, messageId,
                args);
        context.addMessage(clientId, message);
    }

    public static FacesMessage getMessage(FacesContext context,
            String messageId, Object[] args) {
        Locale locale = ComponentUtil_.getLocale(context);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return getMessage(context, locale, FacesMessage.SEVERITY_ERROR,
                messageId, args);
    }

    public static FacesMessage getMessage(FacesContext context, Locale locale,
            Severity severity, String messageId, Object[] args) {

        MessageResourceBundle bundle = getBundle(context, locale);
        String summary = bundle.get(messageId);
        String detail = bundle.get(messageId + DETAIL_SUFFIX);
        if (summary == null) {
            MessageResourceBundle defaultBundle = getDefaultBundle(context,
                    locale);
            summary = defaultBundle.get(messageId);
            if (summary != null) {
                detail = defaultBundle.get(messageId + DETAIL_SUFFIX);
            } else {
                detail = bundle.get(messageId + DETAIL_SUFFIX);
                if (detail == null) {
                    detail = defaultBundle.get(messageId + DETAIL_SUFFIX);
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

        return MessageResourceBundleFactory.getNullableBundle(bundleName,
                locale);
    }

    private static String getFormattedMessage(String message, Locale locale,
            Object[] args) {
        if (args == null || args.length == 0) {
            return message;
        }
        return new MessageFormat(message, locale).format(args);
    }

    public static void saveFacesMessagesToMap(FacesContext from, Map to) {
        if(to == null) {
            return;
        }
        final FacesMessage[] messages = FacesMessageUtil.getAllMessages(from);
        to.put(JsfConstants.ERROR_MESSAGE_PERSISTE_KEY, messages);
    }
    
    public static void restoreFacesMessagesFromMap(Map from, FacesContext to) {
        if(from == null) {
            return;
        }
        final FacesMessage[] messages = (FacesMessage[]) from
                .remove(JsfConstants.ERROR_MESSAGE_PERSISTE_KEY);
        if (messages == null) {
            return;
        }
        for (int i = 0; i < messages.length; i++) {
            to.addMessage(null, messages[i]);
        }
    }

}
