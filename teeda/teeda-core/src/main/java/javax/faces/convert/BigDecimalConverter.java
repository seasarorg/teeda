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

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ConvertUtils_;

public class BigDecimalConverter implements Converter {

	public static final String CONVERTER_ID = "javax.faces.BigDecimal";

	public BigDecimalConverter() {
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		ConvertUtils_.assertNotNull(context, "FacesContext");
		ConvertUtils_.assertNotNull(component, "UIComponent");

		if (value == null) {
			return null;
		}

		value = value.trim();		
		if(value.length() < 1){
			return null;
		}
		
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			Object[] args = ConvertUtils_.createExceptionMessageArgs(component, value); 
			throw ConvertUtils_.wrappedByConverterException(this, context, args, e);
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		ConvertUtils_.assertNotNull(context, "FacesContext");
		ConvertUtils_.assertNotNull(component, "UIComponent");

		if (value == null) {
			return "";
		}

		try {
			return (value instanceof String) ? 
					(String) value : ((BigDecimal) value).toString();
		} catch (Exception e) {
			throw ConvertUtils_.wrappedByConverterException(e);
		}
	}
}
