package org.seasar.teeda.core.exception;



public class NoValueBindingContextException extends ExtendFacesException {

    private static final long serialVersionUID = 3256721801307371064L;

    public NoValueBindingContextException(String ref){
        super("ETDA0007", new Object[]{ref});
    }
}
