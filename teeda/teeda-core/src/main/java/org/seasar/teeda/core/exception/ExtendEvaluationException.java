/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;

import org.seasar.framework.message.MessageFormatter;

/**
 * @author shot
 * @author manhole
 */
public class ExtendEvaluationException extends EvaluationException {

    private static final long serialVersionUID = 3258407322669101874L;

    private static final String EVALUATION_EXCEPTION_ID = "ETDA0003";

    private String messageCode_;

    private Object[] args_;

    private String simpleMessage_;

    private String message_;

    private MethodBinding methodBinding_;

    public ExtendEvaluationException(EvaluationException cause, MethodBinding mb) {
        this(cause, mb.getClass().getName(), mb.getExpressionString());
        methodBinding_ = mb;
    }

    public ExtendEvaluationException(Throwable cause, String className,
            String expressionString) {
        this(cause, className, expressionString, EVALUATION_EXCEPTION_ID);
    }

    protected ExtendEvaluationException(Throwable cause, String className,
            String expressionString, String messageCode) {
        super(cause);
        args_ = new Object[] { className, expressionString };
        messageCode_ = messageCode;
        simpleMessage_ = MessageFormatter.getSimpleMessage(messageCode_, args_);
        message_ = MessageFormatter.getFormattedMessage(messageCode_,
                simpleMessage_);

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
