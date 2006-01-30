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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ConvertUtils_;

/**
 * TODO test & refactor(because of having lots of responsibility)
 */
public class DateTimeConverter implements Converter, StateHolder {

	public static final String CONVERTER_ID = "javax.faces.DateTime";

	protected static final String STYLE_DEFAULT = "default";

	protected static final String STYLE_MEDIUM = "medium";

	protected static final String STYLE_SHORT = "short";

	protected static final String STYLE_LONG = "long";

	protected static final String STYLE_FULL = "full";

	protected static final String TYPE_DATE = "date";

	protected static final String TYPE_TIME = "time";

	protected static final String TYPE_BOTH = "both";

	private String dateStyle_ = STYLE_DEFAULT;

	private boolean transientValue_ = false;

	private Locale locale_ = null;

	private String pattern_ = null;

	private String timeStyle_ = STYLE_DEFAULT;

	private static final TimeZone DEFAULT_TIME_ZONE = TimeZone
			.getTimeZone("GMT");

	private TimeZone timeZone_ = DEFAULT_TIME_ZONE;

	private String type_ = TYPE_DATE;

	public DateTimeConverter() {
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		ConvertUtils_.assertNotNull(context, "FacesContext");
		ConvertUtils_.assertNotNull(component, "UIComponent");

		if (value == null) {
			return null;
		}
		value = value.trim();
		if (value.length() < 1) {
			return null;
		}
		Locale locale = getLocale();
		DateFormat parser = getDateFormat(value, locale);
		if (parser == null) {
			parser = getDateFormat0(value, locale);
		}
		parser.setLenient(false);
		TimeZone timeZone = getTimeZone();
		if (timeZone != null) {
			parser.setTimeZone(timeZone);
		}
		try {
			return parser.parse(value);
		} catch (ParseException e) {
			Object[] args = ConvertUtils_.createExceptionMessageArgs(component,
					value);
			throw ConvertUtils_.wrappedByConverterException(this, context,
					args, e);
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		ConvertUtils_.assertNotNull(context, "FacesContext");
		ConvertUtils_.assertNotNull(component, "UIComponent");

		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return (String) value;
		}

		DateFormat formatter = getDateFormat(getLocale());
		TimeZone timeZone = getTimeZone();
		if (timeZone != null) {
			formatter.setTimeZone(timeZone);
		}
		try {
			return formatter.format(value);
		} catch (Exception e) {
			throw ConvertUtils_.wrappedByConverterException(e);
		}
	}

	public boolean isTransient() {
		return transientValue_;
	}

	public void setTransient(boolean transientValue) {
		transientValue_ = transientValue;
	}

	public Object saveState(FacesContext context) {
		Object[] values = new Object[6];
		values[0] = dateStyle_;
		values[1] = locale_;
		values[2] = pattern_;
		values[3] = timeStyle_;
		values[4] = timeZone_;
		values[5] = type_;
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[]) state;
		dateStyle_ = (String) values[0];
		locale_ = (Locale) values[1];
		pattern_ = (String) values[2];
		timeStyle_ = (String) values[3];
		timeZone_ = (TimeZone) values[4];
		type_ = (String) values[5];
	}

	public String getDateStyle() {
		return dateStyle_;
	}

	public void setDateStyle(String dateStyle) {
		dateStyle_ = dateStyle;
	}

	public Locale getLocale() {
		if (locale_ == null) {
			locale_ = getLocale(FacesContext.getCurrentInstance());
		}
		return locale_;
	}

	public void setLocale(Locale locale) {
		locale_ = locale;
	}

	public String getPattern() {
		return pattern_;
	}

	public void setPattern(String pattern) {
		pattern_ = pattern;
	}

	public String getTimeStyle() {
		return timeStyle_;
	}

	public void setTimeStyle(String timeStyle) {
		timeStyle_ = timeStyle;
	}

	public TimeZone getTimeZone() {
		return timeZone_;
	}

	public void setTimeZone(TimeZone timeZone) {
		timeZone_ = timeZone;
	}

	public String getType() {
		return type_;
	}

	public void setType(String type) {
		type_ = type;
	}

	private Locale getLocale(FacesContext context) {
		return context.getViewRoot().getLocale();
	}

	protected DateFormat getDateFormat(Locale locale) {
		String pattern = getPattern();
		if (pattern != null) {
			return new SimpleDateFormat(pattern, locale);
		}

		if (isDefaultStyle()) {
			pattern = getPattern(locale);
			if (pattern.indexOf("yyyy") < 0) {
				pattern = replace(pattern, "yy", "yyyy");
			}
			return new SimpleDateFormat(pattern);
		}
		return getDateFormatForType();
	}

	protected DateFormat getDateFormat(String value, Locale locale) {
		String pattern = getPattern();
		if (pattern != null) {
			return new SimpleDateFormat(pattern, locale);
		}

		if (isDefaultStyle()) {
			return getDateFormat0(value, locale);
		}
		return getDateFormatForType();
	}

	protected boolean isDefaultStyle() {
		return STYLE_DEFAULT.equalsIgnoreCase(getDateStyle())
				&& STYLE_DEFAULT.equalsIgnoreCase(getTimeStyle());
	}

	private static DateFormat getDateFormat0(String s, Locale locale) {
		String pattern = getPattern(locale);
		String shortPattern = removeDelimiter(pattern);
		String[] delimitors = findDelimiter(s);

		String array[];
		if (delimitors == null) {
			if (s.length() == shortPattern.length())
				return new SimpleDateFormat(shortPattern);
			if (s.length() == shortPattern.length() + 2)
				return new SimpleDateFormat(replace(shortPattern, "yy", "yyyy"));
			else
				return new SimpleDateFormat();
		}

		array = split(s, delimitors);
		for (int i = 0; i < array.length; i++) {
			if (array[i].length() != 4)
				continue;
			pattern = replace(pattern, "yy", "yyyy");
			break;
		}

		return new SimpleDateFormat(pattern);
	}

	private static String getPattern(Locale locale) {
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance(3,
				locale);
		String pattern = df.toPattern();
		int index = pattern.indexOf(' ');
		if (index > 0)
			pattern = pattern.replaceAll(" ", "");
		if (pattern.indexOf("MM") < 0)
			pattern = replace(pattern, "M", "MM");
		if (pattern.indexOf("dd") < 0)
			pattern = replace(pattern, "d", "dd");
		return pattern;
	}

	private static String[] findDelimiter(String value) {
		List list = new LinkedList();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (!Character.isDigit(c)) {
				list.add(Character.toString(c));
			}
		}
		return (list.size() != 0) ? (String[]) list.toArray(new String[list
				.size()]) : null;
	}

	private static String removeDelimiter(String pattern) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c == 'y' || c == 'M' || c == 'd')
				buf.append(c);
		}

		return buf.toString();
	}

	protected static final String replace(String text, String fromText,
			String toText) {
		if (text == null || fromText == null || toText == null)
			return null;
		StringBuffer buf = new StringBuffer(100);
		int pos = 0;
		int pos2 = 0;
		for (;;) {
			pos = text.indexOf(fromText, pos2);
			if (pos == 0) {
				buf.append(toText);
				pos2 = fromText.length();
			} else if (pos > 0) {
				buf.append(text.substring(pos2, pos));
				buf.append(toText);
				pos2 = pos + fromText.length();
			} else {
				buf.append(text.substring(pos2));
				break;
			}
		}
		return buf.toString();
	}

	protected static String[] split(String str, String[] delimeters) {
		if (str == null)
			return new String[0];

		List list = new ArrayList();
		for (int i = 0; i < delimeters.length; i++) {
			for (StringTokenizer st = new StringTokenizer(str, delimeters[i]); st
					.hasMoreElements(); list.add(st.nextElement()))
				;
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	protected DateFormat getDateFormatForType() {
		String type = getType();
		if (type.equals(TYPE_DATE)) {
			return DateFormat.getDateInstance(calcStyle(getDateStyle()),
					getLocale());
		} else if (type.equals(TYPE_TIME)) {
			return DateFormat.getTimeInstance(calcStyle(getTimeStyle()),
					getLocale());
		} else if (type.equals(TYPE_BOTH)) {
			return DateFormat.getDateTimeInstance(calcStyle(getDateStyle()),
					calcStyle(getTimeStyle()), getLocale());
		} else {
			return null;
		}
	}

	protected int calcStyle(String name) {
		if (name.equals(STYLE_DEFAULT)) {
			return DateFormat.DEFAULT;
		}
		if (name.equals(STYLE_MEDIUM)) {
			return DateFormat.MEDIUM;
		}
		if (name.equals(STYLE_SHORT)) {
			return DateFormat.SHORT;
		}
		if (name.equals(STYLE_LONG)) {
			return DateFormat.LONG;
		}
		if (name.equals(STYLE_FULL)) {
			return DateFormat.FULL;
		}
		return DateFormat.DEFAULT;
	}

}
