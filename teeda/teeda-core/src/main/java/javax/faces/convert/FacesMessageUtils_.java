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
package javax.faces.convert;

import java.text.MessageFormat;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * TODO test
 */
class FacesMessageUtils_ {
	
	private static final String DEFAULT_SUFFIX = "_detail";
	
	private FacesMessageUtils_() {
	}

	protected static FacesMessage getMessage(FacesContext context,
			String messageId, Object[] args) {

		Locale locale = context.getViewRoot().getLocale();
		if (locale == null) {
			locale = Locale.getDefault();
		}
		
		return getMessage(context, locale, FacesMessage.SEVERITY_ERROR,
				messageId, args);

	}

	protected static FacesMessage getMessage(FacesContext context,
			Locale locale, Severity severity, String messageId,
			Object[] args) {
		
		ResourceBundle bundle = getResourceBundle(context, locale);
		
		String summary = getBundleString(bundle, messageId);
		
		String detail = getBundleString(bundle, messageId + DEFAULT_SUFFIX);
		
		if(args != null && args.length > 1){
			
			if(summary != null){
				summary = getFormattedMessage(summary, locale, args);
			}
			
			if(detail != null){
				summary = getFormattedMessage(summary, locale, args);
			}
		}
		
		return new FacesMessage(severity, summary, detail);
	}

	private static ResourceBundle getResourceBundle(FacesContext context, Locale locale){
		
		ResourceBundle bundle = null;
		String bundleName = context.getApplication().getMessageBundle();
		if(bundleName != null){
			bundle = getBundle(context, locale, bundleName);
		}

		if(bundle == null || bundleName == null){
			bundle = getBundle(context, locale, FacesMessage.FACES_MESSAGES);
		}

		return bundle;
	}
	
	private static ResourceBundle getBundle(FacesContext context, Locale locale, String bundleName){
		
		ResourceBundle bundle = null;
		ClassLoader loader = getClassLoader(context);
		try{
			bundle = ResourceBundle.getBundle(bundleName, locale, loader);
		}catch(MissingResourceException e){
			context.getExternalContext().log("ResourceBundle " + bundleName + " could not be found.");
		}
		return bundle;
	}
	
	private static ClassLoader getClassLoader(Object obj){
		
		ClassLoader loader = null;
		
		loader = Thread.currentThread().getContextClassLoader();
		
		if(loader == null){
			loader = obj.getClass().getClassLoader();
		}
		
		if(loader == null){
			loader = FacesMessageUtils_.class.getClassLoader();
		}
		return loader;
	}
	
	private static String getBundleString(ResourceBundle bundle, String key){
		return (bundle != null) ? bundle.getString(key) : null;
	}
	
	private static String getFormattedMessage(String message, Locale locale, Object[] args){
		return new MessageFormat(message, locale).format(args);
	}

}
