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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConvertUtils_ {

	public static void assertNotNull(Object obj){
		assertNotNull(obj, null);
	}
	
	public static void assertNotNull(Object obj, String message){
		if(obj == null){
			throw new NullPointerException(message);
		}
	}
	
	public static ConverterException wrappedByConverterException(){
		return new ConverterException();
	}
	
	public static ConverterException wrappedByConverterException(String message){
		return new ConverterException(message);
	}

	public static ConverterException wrappedByConverterException(Throwable t){
		return wrappedByConverterException(null, t);
	}

	public static ConverterException wrappedByConverterException(String message, Throwable t){
		return new ConverterException(message, t);		
	}

	public static ConverterException wrappedByConverterException(Converter converter, FacesContext context, Object[] args){
		return wrappedByConverterException(converter, context, args, null);
	}
	
	public static ConverterException wrappedByConverterException(Converter converter, FacesContext context, Object[] args, Throwable t){
		
		String conversionMessage = createConversionMessage(converter);
		FacesMessage facesMessage = FacesMessageUtils_.getMessage(context, conversionMessage, args);
		return new ConverterException(facesMessage, t);
	}
	
	public static String createConversionMessage(Converter converter){
		return converter.getClass().getName() + ".CONVERSION";  
	}

	public static Object[] createExceptionMessageArgs(UIComponent component, String value) {
        // TODO label attribute
		return new Object[]{component.getId(), value};
	}
}
