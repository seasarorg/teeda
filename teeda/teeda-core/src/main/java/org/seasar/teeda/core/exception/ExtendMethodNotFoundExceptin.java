/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.util.MessageFormatterUtil;

/**
 * @author Shinpei Ohtani
 */
public class ExtendMethodNotFoundExceptin extends MethodNotFoundException {

    private static final long serialVersionUID = 3257852073575265329L;

    private static final String METHOD_NOT_FOUND_EXCEPTION_ID = "ETDA0002";

    private String messageCode_ = METHOD_NOT_FOUND_EXCEPTION_ID;

    private Object[] args_;

    private String simpleMessage_;

    private String message_;

    private MethodBinding methodBinding_;

    public ExtendMethodNotFoundExceptin(MethodNotFoundException cause,
            MethodBinding mb) {
        super(cause);
        args_ = new Object[] { mb.getClass().getName(), mb.getExpressionString()};
        simpleMessage_ = 
            MessageFormatterUtil.getSimpleMessage(messageCode_, args_);
        MessageFormatterUtil.getSimpleMessage(messageCode_, args_);
        message_ = 
            MessageFormatterUtil.getFormattedMessage(messageCode_, simpleMessage_);
        methodBinding_ = mb;
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
