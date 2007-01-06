/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

import org.seasar.framework.message.MessageFormatter;

/**
 * @author shot
 */
public class ExtendMethodNotFoundExceptin extends MethodNotFoundException {

    private static final long serialVersionUID = 3257852073575265329L;

    private static final String METHOD_NOT_FOUND_EXCEPTION_ID = "ETDA0002";

    private String messageCode_;

    private Object[] args_;

    private String simpleMessage_;

    private String message_;

    private MethodBinding methodBinding_;

    public ExtendMethodNotFoundExceptin(MethodNotFoundException cause,
            MethodBinding mb) {
        this(cause, mb.getClass().getName(), mb.getExpressionString());
        methodBinding_ = mb;
    }

    public ExtendMethodNotFoundExceptin(Exception cause, String className,
            String expressionString) {
        this(cause, className, expressionString, METHOD_NOT_FOUND_EXCEPTION_ID);
    }

    protected ExtendMethodNotFoundExceptin(Exception cause, String className,
            String expressionString, String messageCode) {
        super(cause);
        args_ = new Object[] { className, expressionString };
        messageCode_ = messageCode;
        simpleMessage_ = MessageFormatter.getSimpleMessage(messageCode_, args_);
        message_ = MessageFormatter.getFormattedMessage(messageCode_, simpleMessage_);

    }

    public MethodBinding getMethodBinding() {
        return methodBinding_;
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

    public final String getSimpleMessage() {
        return simpleMessage_;
    }

}
