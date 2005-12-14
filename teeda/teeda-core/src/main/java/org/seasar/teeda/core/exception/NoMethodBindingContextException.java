package org.seasar.teeda.core.exception;


public class NoMethodBindingContextException extends ExtendFacesException {

    private static final long serialVersionUID = 3258126942841745713L;

    public NoMethodBindingContextException(String expression, Class[] params){
        super("ETDA0008", new Object[]{expression, params});
    }
}
