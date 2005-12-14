package org.seasar.teeda.core.el.impl.commons;

import org.apache.commons.el.BinaryOperatorExpression;
import org.apache.commons.el.Expression;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;


public class BinaryOperatorExpressionReplacer implements Replacer{

    private ExpressionProcessor processor_;
    public BinaryOperatorExpressionReplacer(ExpressionProcessor processor){
        processor_ = processor;
    }

    public void replace(Object expression) {
        Expression next = ((BinaryOperatorExpression)expression).getExpression();
        processor_.processExpression(next, next.getClass());
    }
    
}
