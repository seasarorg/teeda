package javax.faces.convert;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;


public class ConverterException extends FacesException {

	private FacesMessage facesMessage_ = null;
	public ConverterException() {
		super();
	}
	
	public ConverterException(String message) {
		super(message);
	}

	public ConverterException(Throwable cause) {
		super(cause);
	}

	public ConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConverterException(FacesMessage facesMessage){
		super(facesMessage.getDetail());
		facesMessage_ = facesMessage;		
	}
	
	public ConverterException(FacesMessage facesMessage, Throwable cause){
		super(facesMessage.getDetail(), cause);
		facesMessage_ = facesMessage;
	}
	
	public FacesMessage getFacesMessage(){
		return facesMessage_;
	}

}
