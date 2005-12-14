package org.seasar.teeda.core.el.impl.commons;

import org.apache.commons.el.ConditionalExpression;
import org.apache.commons.el.Expression;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;


public class ConditionalExpressionReplacer implements Replacer {

    private ExpressionProcessor processor_;
    private ConditionalExpression expression_;
    public ConditionalExpressionReplacer(ExpressionProcessor processor){
        processor_ = processor;
    }

    public void replace(Object expression) {
        expression_ = (ConditionalExpression)expression;
        Expression trueExpression = expression_.getTrueBranch();
        Expression falseExpression = expression_.getFalseBranch();
        processor_.processExpression(trueExpression, trueExpression.getClass());
        processor_.processExpression(falseExpression, falseExpression.getClass());
    }

}
