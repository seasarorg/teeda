package org.seasar.teeda.core.exception;

import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;

import org.seasar.teeda.core.util.MessageFormatterUtil;


public class ExtendEvaluationException extends EvaluationException {

    private static final long serialVersionUID = 3258407322669101874L;

    private static final String EVALUATION_EXCEPTION_ID = "ETDA0003";

    private String messageCode_ = EVALUATION_EXCEPTION_ID;

    private Object[] args_;

    private String simpleMessage_;

    private String message_;

    private MethodBinding methodBinding_;

    public ExtendEvaluationException(EvaluationException cause,
            MethodBinding mb) {
        super(cause);
        args_ = new Object[] { mb.getClass().getName(), mb.getExpressionString()};
        simpleMessage_ = 
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
