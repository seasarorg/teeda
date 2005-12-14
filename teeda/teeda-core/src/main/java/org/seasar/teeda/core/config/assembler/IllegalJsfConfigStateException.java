package org.seasar.teeda.core.config.assembler;

import org.seasar.teeda.core.exception.ExtendFacesException;


public class IllegalJsfConfigStateException extends ExtendFacesException {

    private static final long serialVersionUID = 3258417231091347508L;

    public IllegalJsfConfigStateException(Object[] args){
        super("ETDA0006", args);
    }
}
