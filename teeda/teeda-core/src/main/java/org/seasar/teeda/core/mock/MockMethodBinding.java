package org.seasar.teeda.core.mock;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

public class MockMethodBinding extends MethodBinding {

    private String expression_;

    private boolean invokeCalled_;

    private Object[] invokeParams_;

    public MockMethodBinding() {
    }

    public MockMethodBinding(String expression) {
        expression_ = expression;
    }

    public Object invoke(FacesContext context, Object[] params)
            throws EvaluationException, MethodNotFoundException {
        invokeCalled_ = true;
        invokeParams_ = params;
        return null;
    }

    public Class getType(FacesContext context) throws MethodNotFoundException {
        return null;
    }

    public void setExpressionString(String expression) {
        expression_ = expression;
    }

    public String getExpressionString() {
        return expression_;
    }

    public boolean isInvokeCalled() {
        return invokeCalled_;
    }

    public Object[] getInvokeParams() {
        return invokeParams_;
    }

}
