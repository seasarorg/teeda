package org.seasar.teeda.core.exception;

import javax.faces.FacesException;

import org.seasar.teeda.core.util.MessageFormatterUtil;

public class ExtendFacesException extends FacesException {

    private static final long serialVersionUID = 3257003246236087604L;

    private static final String DEFAULT_FACES_EXCEPTION_ID = "ETDA0001";

    private String messageCode_;
    private Object[] args_;
    private String simpleMessage_;
    private String message_;

    public ExtendFacesException(){
        this(DEFAULT_FACES_EXCEPTION_ID, null, null);
    }

    public ExtendFacesException(String messageCode){
        this(messageCode, null, null);
    }

    public ExtendFacesException(String messageCode, Object[] args){
        this(messageCode, args, null);
    }

    public ExtendFacesException(String messageCode, Object[] args, Throwable cause){
        super(cause);
        messageCode_ = messageCode;
        args_ = args;
        simpleMessage_ = MessageFormatterUtil.getSimpleMessage(messageCode_, args_);
        message_ = MessageFormatterUtil.getFormattedMessage(messageCode_, simpleMessage_);
    }
    
    public final String getMessageCode() {
        return messageCode_;
    }

    public final Object[] getArgs() {
        return args_;
    }

    public final String getMessage() {
        return message_;
    }
    
    protected void setMessage(String message) {
        message_ = message;
    }
    
    public final String getSimpleMessage() {
        return simpleMessage_;
    }

    public Throwable getCause() {
        return super.getCause();
    }

}
