package javax.faces.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;


public class ValidatorException extends FacesException {

	private FacesMessage facesMessage_ = null;
	
	public ValidatorException(FacesMessage facesMessage){
		this(facesMessage, null);
	}
	
	public ValidatorException(FacesMessage facesMessage, Throwable cause){
		super(facesMessage.getSummary(), cause);
		facesMessage_ = facesMessage;
	}
	
	public FacesMessage getFacesMessage(){
		return facesMessage_;
	}
	
}
