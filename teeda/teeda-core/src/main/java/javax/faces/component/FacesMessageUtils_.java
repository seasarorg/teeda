package javax.faces.component;

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

	protected static void addErrorMessage(FacesContext context, UIComponent component, String messageId) {
		context.addMessage(
				component.getClientId(context),
				getMessage(
					context, context.getViewRoot().getLocale(),
					FacesMessage.SEVERITY_ERROR, messageId, null));
	}

	protected static void addErrorMessage(FacesContext context, UIComponent component, String messageId, Object[] args) {
		context.addMessage(
				component.getClientId(context),
				getMessage(context, context.getViewRoot().getLocale(),
						FacesMessage.SEVERITY_ERROR, messageId, args));
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
			Locale locale, Severity severity, String messageId, Object[] args) {

		ResourceBundle bundle = getResourceBundle(context, locale);

		String summary = getBundleString(bundle, messageId);

		String detail = getBundleString(bundle, messageId + DEFAULT_SUFFIX);

		if (args != null && args.length > 1) {

			if (summary != null) {
				summary = getFormattedMessage(summary, locale, args);
			}

			if (detail != null) {
				summary = getFormattedMessage(summary, locale, args);
			}
		}

		return new FacesMessage(severity, summary, detail);
	}

	private static ResourceBundle getResourceBundle(FacesContext context,
			Locale locale) {

		ResourceBundle bundle = null;
		String bundleName = context.getApplication().getMessageBundle();
		if (bundleName != null) {
			bundle = getBundle(context, locale, bundleName);
		}

		if (bundle == null || bundleName == null) {
			bundle = getBundle(context, locale, FacesMessage.FACES_MESSAGES);
		}

		return bundle;
	}

	private static ResourceBundle getBundle(FacesContext context,
			Locale locale, String bundleName) {

		ResourceBundle bundle = null;
		ClassLoader loader = getClassLoader(context);
		try {
			bundle = ResourceBundle.getBundle(bundleName, locale, loader);
		} catch (MissingResourceException e) {
			context.getExternalContext().log(
					"ResourceBundle " + bundleName + " could not be found.");
		}
		return bundle;
	}

	private static ClassLoader getClassLoader(Object obj) {

		ClassLoader loader = null;

		loader = Thread.currentThread().getContextClassLoader();

		if (loader == null) {
			loader = obj.getClass().getClassLoader();
		}

		if (loader == null) {
			loader = FacesMessageUtils_.class.getClassLoader();
		}
		return loader;
	}

	private static String getBundleString(ResourceBundle bundle, String key) {
		return (bundle != null) ? bundle.getString(key) : null;
	}

	private static String getFormattedMessage(String message, Locale locale,
			Object[] args) {
		return new MessageFormat(message, locale).format(args);
	}

}
