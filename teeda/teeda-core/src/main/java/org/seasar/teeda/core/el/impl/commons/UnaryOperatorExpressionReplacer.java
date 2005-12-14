package org.seasar.teeda.core.el.impl.commons;

import org.apache.commons.el.Expression;
import org.apache.commons.el.UnaryOperatorExpression;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;


public class UnaryOperatorExpressionReplacer implements Replacer {

    private ExpressionProcessor processor_;
    public UnaryOperatorExpressionReplacer(ExpressionProcessor processor){
        processor_ = processor;
    }

    public void replace(Object expression) {
        Expression next = ((UnaryOperatorExpression)expression).getExpression();
        processor_.processExpression(next, next.getClass());
    }

}
