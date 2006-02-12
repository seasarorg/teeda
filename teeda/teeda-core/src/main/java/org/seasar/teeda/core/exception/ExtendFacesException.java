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
package org.seasar.teeda.core.exception;

import javax.faces.FacesException;

import org.seasar.teeda.core.util.MessageFormatterUtil;

/**
 * @author shot
 */
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
