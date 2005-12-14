package org.seasar.teeda.core.exception;




public class IllegalClassTypeException extends ExtendFacesException {

    private static final long serialVersionUID = 3257007670136156983L;

    public IllegalClassTypeException(Class expected, Class actual){
        super("ETDA0004", new Object[]{expected.getName(), actual.getName()}, null);
    }

}
