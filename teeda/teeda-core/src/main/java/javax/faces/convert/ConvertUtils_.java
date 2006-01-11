package javax.faces.convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

class ConvertUtils_ {

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
