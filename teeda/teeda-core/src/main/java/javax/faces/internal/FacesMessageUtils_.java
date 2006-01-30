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
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.ComponentUtils_;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import java.util.*;

/**
 * @author Shinpei Ohtani
 */
public class FacesMessageUtils_ {

	private static final String DETAIL_SUFFIX = "_detail";

	private FacesMessageUtils_() {
	}

	public static void addErrorMessage(FacesContext context, UIComponent component, String messageId) {
        String clientId = component.getClientId(context);
        Locale locale = ComponentUtils_.getLocale(context);
        FacesMessage message = getMessage(context, locale ,FacesMessage.SEVERITY_ERROR, messageId, null);
		context.addMessage(clientId, message);
	}

    public static void addErrorMessage(FacesContext context, UIComponent component, String messageId, Object[] args) {
        String clientId = component.getClientId(context); 
        Locale locale = ComponentUtils_.getLocale(context);
        FacesMessage message = getMessage(context, locale, FacesMessage.SEVERITY_ERROR, messageId, args);
		context.addMessage(clientId, message);
	}

	public static FacesMessage getMessage(FacesContext context, String messageId, Object[] args) {
		Locale locale = ComponentUtils_.getLocale(context);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return getMessage(context, locale, FacesMessage.SEVERITY_ERROR, messageId, args);
	}

	protected static FacesMessage getMessage(FacesContext context,
			Locale locale, Severity severity, String messageId, Object[] args) {

		ResourceBundle bundle = getResourceBundle(context, locale);
		String summary = getBundleString(bundle, messageId);
		String detail = getBundleString(bundle, messageId + DETAIL_SUFFIX);
		if (args != null && args.length > 1) {
			if (summary != null) {
				summary = getFormattedMessage(summary, locale, args);
			}
			if (detail != null) {
				detail = getFormattedMessage(detail, locale, args);
			}
		}
		return new FacesMessage(severity, summary, detail);
	}

	private static ResourceBundle getResourceBundle(FacesContext context, Locale locale) {
		ResourceBundle bundle = null;
		String appBundleName = context.getApplication().getMessageBundle();
		if (appBundleName != null) {
			bundle = getBundle(context, locale, appBundleName);
		}

		if (bundle == null) {
			bundle = getDefaultBundle(context, locale);
		}
		return bundle;
	}

    private static ResourceBundle getDefaultBundle(FacesContext context, Locale locale){
        return getBundle(context, locale, FacesMessage.FACES_MESSAGES);
    }
    
	private static ResourceBundle getBundle(FacesContext context,
			Locale locale, String bundleName) {

		ResourceBundle bundle = null;
		ClassLoader loader = ClassLoaderUtil_.getClassLoader(context);
		try {
			bundle = ResourceBundle.getBundle(bundleName, locale, loader);
		} catch (MissingResourceException e) {
			context.getExternalContext().log("ResourceBundle " + bundleName + " could not be found.");
		}
		return bundle;
	}

	private static String getBundleString(ResourceBundle bundle, String key) {
        if(bundle == null){
            return null;
        }
        try{
            return bundle.getString(key);
        }catch(MissingResourceException ignore){
            return null;
        }
	}

	private static String getFormattedMessage(
            String message, Locale locale, Object[] args) {
		return new MessageFormat(message, locale).format(args);
	}

}
